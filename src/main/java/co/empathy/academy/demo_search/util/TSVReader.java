package co.empathy.academy.demo_search.util;

import org.elasticsearch.search.aggregations.metrics.InternalHDRPercentiles;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TSVReader<T> implements Iterable<T> {
    Class<T> clazz;

    BufferedReader reader;
    List<String> headers;

    public TSVReader(File file, Class<T> clazz) throws IOException {
        this.clazz = clazz;

        reader = new BufferedReader(
                new FileReader(file)
        );

        headers = extractLine();
    }

    private List<String> extractLine() throws IOException {
        return Arrays.stream(
                    reader.readLine().split("\t")
                )
                .toList();
    }

    private T convertLine(List<String> line)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException
    {
        //Lotta reflection and such
        List<Method> setters = Arrays.stream(clazz.getDeclaredMethods())
                .filter(method -> method.getName().contains("set"))
                .collect(Collectors.toList());

        T result = clazz.getDeclaredConstructor().newInstance();
        for(int i = 0; i < line.size(); i++) {
            String name = headers.get(i);
            String value = line.get(i);

            //Get the setter that contains the name of the field
            Method method = setters.stream()
                    .filter(m ->
                            m.getName().contains(name)
                    )
                    .findFirst().get();

            //Invoke it trying to convert parameter from string
            method.invoke(
                    result,
                    method.getParameters()[0]
                    .getType()
                    .getDeclaredConstructor(String.class)
                    .newInstance(value)
            );
        }

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
                return null;
            }
        }
    }
}
