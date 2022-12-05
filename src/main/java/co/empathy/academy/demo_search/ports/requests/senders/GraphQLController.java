package co.empathy.academy.demo_search.ports.requests.senders;

import co.empathy.academy.demo_search.model.titles.Title;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import co.empathy.academy.demo_search.ports.requests.commands.search.GenreSearchCommand;
import graphql.nextgen.GraphQL;
import graphql.parser.antlr.GraphqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Null;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/graphql")
public class GraphQLController {
    @QueryMapping
    public List<Title> search(@Argument String genre) {
        System.out.println(genre);
        return List.of();
    }
}
