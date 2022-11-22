package co.empathy.academy.demo_search.util;

import co.empathy.academy.demo_search.model.FullAka;
import org.elasticsearch.search.aggregations.metrics.InternalHDRPercentiles;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TSVReader<T> implements Iterable<T> {
    private final List<String> listNames;
    private final Class<T> clazz;

    private BufferedReader reader;
    private final List<String> headers;

    public TSVReader(
            File file,
            Class<T> clazz,
            String... listNames
    ) throws IOException {
        this(file, clazz, Arrays.stream(listNames).toList());
    }
    public TSVReader(
            File file,
            Class<T> clazz,
            List<String> listNames
    ) throws IOException {
        this.clazz = clazz;
        this.listNames = listNames;

        reader = new BufferedReader(
                new FileReader(file)
        );

        headers = extractLine();
    }

    private List<String> extractLine() throws IOException {
        String line = reader.readLine();
        if(line == null) return null;
        return Arrays.stream(
                    line.split("\t")
                )
                .toList();
    }

    private T convertLine(List<String> line)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException
    {
        headers.forEach(System.out::println);
        line.forEach(System.out::println);

        //Lotta reflection and such
        T result = clazz.getDeclaredConstructor().newInstance();
        for(int i = 0; i < line.size(); i++) {
            String name = headers.get(i);
            Object value = line.get(i);

            //Get the field
            Optional<Field> op = Arrays.stream(clazz.getDeclaredFields())
                    .filter(f ->
                            f.getName().toLowerCase()
                                    .equals(name.toLowerCase())
                    ).findFirst();

            if(op.isEmpty()) continue;
            Field field = op.get();

            //Convert value to the type requested
            //TODO: fix this, make it more generic
            if(listNames.contains(name))
                value = Arrays.stream(((String) value).split(",")).toList();
            else try {
                value = field
                        .getType()
                        .getDeclaredConstructor(String.class)
                        .newInstance(value);
            } catch (InvocationTargetException e) {
                value = null;
            }

            //Set the field, it is final so we need to make it accesible
            field.setAccessible(true);
            field.set(
                    result,
                    value
            );
        }

        System.out.println(result);
        return result;
    }

    @Override
    public Iterator<T> iterator() {
        return new TSVIterator(this);
    }

    private class TSVIterator implements Iterator<T> {
        private TSVReader reader;
        private List<String> nextLine;
        public TSVIterator(TSVReader reader) {
            this.reader = reader;
            nextLine = nextLine();
        }

        private List<String> nextLine() {
            List<String> result;
            while(true) {
                try {
                    result = reader.extractLine();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }

        @Override
        public boolean hasNext() {
            return nextLine != null;
        }

        @Override
        public T next() {
            List<String> curr = nextLine;
            nextLine = nextLine();

            try {
                return (T) reader.convertLine(curr);
            } catch (
                    NoSuchMethodException |
                    InvocationTargetException |
                    InstantiationException |
                    IllegalAccessException e
            ) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
