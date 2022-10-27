package co.empathy.academy.demo_search.query;

public class LeafQuery implements Query {
    private String name;
    private Object value;

    public LeafQuery(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String make() {
       return "{\"" + name + "\": " + value + "}";
    }
}
