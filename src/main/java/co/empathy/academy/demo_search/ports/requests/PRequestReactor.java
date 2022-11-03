package co.empathy.academy.demo_search.ports.requests;

import co.empathy.academy.demo_search.ports.requests.commands.SearchCommand;

public interface PRequestReactor {
    /**
     * Sends the command to react to
     * @param c the command to send ot the boundary
     */
    public <T> T reactToSearch(SearchCommand<T> c);
}
