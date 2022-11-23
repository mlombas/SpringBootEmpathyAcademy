package co.empathy.academy.demo_search.ports.index;

/**
 * Represents the attributes any document
 * needs have to be able to be indexed
 */
public interface Indexable {

    /**
     * A unique indentifier for the document
     * @return the id
     */
    String id();
}
