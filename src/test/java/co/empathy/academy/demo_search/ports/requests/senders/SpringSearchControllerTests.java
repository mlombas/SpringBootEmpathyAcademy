package co.empathy.academy.demo_search.ports.requests.senders;

import co.empathy.academy.demo_search.hexagon.Boundary;
import co.empathy.academy.demo_search.model.titles.Title;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class SpringSearchControllerTests {
    @Test
    void givenController_whenGenreSearch_thenListOfGenresReturned() throws ExecutionException, InterruptedException {
        List<String> search = List.of(new String[]{"a", "b"});
        List<Object> response = List.of(new Object[]{new Title()});
        CompletableFuture<List<Object>> future = new CompletableFuture<>();
        future.complete(response);

        PRequestReactor reactor = mock(Boundary.class);
        given(reactor.reactToSearch(argThat(searchCommand ->
                ReflectionTestUtils.getField(searchCommand, "genres")
                        .equals(search)
            )))
                .willReturn(future);

        SpringSearchController controller = new SpringSearchController();
        ReflectionTestUtils.setField(controller, "reactor", reactor);
        var result = controller.genres(search);

        assertEquals(result.get().getBody(), future.get());
    }

    @Test
    void givenController_whenGenreSearchWithOr_thenListOfGenresReturned() throws ExecutionException, InterruptedException {
        List<String> search = List.of(new String[]{"a", "b"});
        List<Object> response = List.of(new Object[]{new Title()});
        CompletableFuture<List<Object>> future = new CompletableFuture<>();
        future.complete(response);

        PRequestReactor reactor = mock(Boundary.class);
        given(reactor.reactToSearch(argThat(searchCommand ->
                ReflectionTestUtils
                        .getField(searchCommand, "genres")
                        .equals(search) &&
                        ! (boolean) ReflectionTestUtils
                                .getField(searchCommand, "and")
        )))
                .willReturn(future);

        SpringSearchController controller = new SpringSearchController();
        ReflectionTestUtils.setField(controller, "reactor", reactor);
        var result = controller.genres(false, search);

        assertEquals(result.get().getBody(), future.get());
    }

    @Test
    void givenController_inTitleSearch_thenListOfGenresReturned() throws ExecutionException, InterruptedException {
        String search = "a";
        List<Object> response = List.of(new Object[]{new Title()});
        CompletableFuture<List<Object>> future = new CompletableFuture<>();
        future.complete(response);

        PRequestReactor reactor = mock(Boundary.class);
        given(reactor.reactToSearch(argThat(searchCommand ->
                ReflectionTestUtils.getField(searchCommand, "intitle")
                        .equals(search)
        )))
                .willReturn(future);

        SpringSearchController controller = new SpringSearchController();
        ReflectionTestUtils.setField(controller, "reactor", reactor);
        var result = controller.intitle(search,null, null, null, null, null);

        assertEquals(result.get().getBody(), future.get());
    }
}
