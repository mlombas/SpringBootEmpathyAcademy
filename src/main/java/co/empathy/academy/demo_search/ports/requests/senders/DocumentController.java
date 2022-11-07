package co.empathy.academy.demo_search.ports.requests.senders;

import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import co.empathy.academy.demo_search.ports.requests.commands.DocumentCommand;
import co.empathy.academy.demo_search.ports.requests.commands.document.BulkIndexCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/document")
public class DocumentController {
    @Autowired
    private PRequestReactor reactor;

    @PostMapping("/index")
    public ResponseEntity<Movie> document(@RequestBody String document) {
        reactor.reactToDocument(
                new BulkIndexCommand<>(
                        new File("title.basics.tsv"),
                        Movie.class
                )
        );

        return ResponseEntity.ok(null);
    }

}
