package co.empathy.academy.demo_search.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public interface SearchEngine {
    /**
     * Returns the cluster version
     *
     * @return the cluster version
     */
    String getVersion();

    /**
     * Returns whatever was passed in
     *
     * @param query the thing to return
     * @return the parameter query
     */
    String echo(String query);

    /**
     * Indexes a document into the cluster
     *
     * @param obj the document to index
     */
    void postDocument(JSONObject obj);

    /**
     * Search for all documents which have a series of certain genres
     *
     * @param genres the genres the document MUST have
     * @return the list of documents that match the criteria
     */
    JSONArray searchGenre(List<String> genres);

    /**
     * Search for all documents which have a series of certain genres.
     * Allows for ALL and ANY operations (must have all or must have any)
     *
     * @param genres the genres to search for
     * @param and true if the document must have all genres, false if only needs to have one
     * @return the list of documents that match the criteria
     */
    JSONArray searchGenre(List<String> genres, boolean and);

    /**
     * Search for documents which contain the query in either their originalTitle or primaryTitle
     *
     * @param inTitle the title to search
     * @return the list of documents that match the criteria
     */
    JSONArray searchTitle(String inTitle);
}
