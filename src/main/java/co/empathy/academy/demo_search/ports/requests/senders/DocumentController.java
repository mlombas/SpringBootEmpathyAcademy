package co.empathy.academy.demo_search.ports.requests.senders;

import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.model.Ratings;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import co.empathy.academy.demo_search.ports.requests.commands.DocumentCommand;
import co.empathy.academy.demo_search.ports.requests.commands.document.BulkIndexCommand;
import co.empathy.academy.demo_search.ports.requests.commands.document.MultipleFileBulkIndexCommand;
import co.empathy.academy.demo_search.util.TSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/document")
public class DocumentController {
    @Autowired
    private PRequestReactor reactor;

    @PostMapping("/index")
    public ResponseEntity<Movie> document(
            @RequestParam MultipartFile basic,
            @RequestParam MultipartFile ratings
    ) throws IOException {
        Path bpath = Paths.get(".", basic.getOriginalFilename());
        basic.transferTo(bpath);
        Path rpath = Paths.get(".", ratings.getOriginalFilename());
        ratings.transferTo(rpath);

        reactor.reactToDocument(
                new MultipleFileBulkIndexCommand<Movie, Ratings>(
                        (m, r) -> m
                                .withAverageRating(r.getAverageRating())
                                .withNumVotes(r.getNumVotes()),
                        (m, r) -> m.getTconst().equals(r.getTconst()),
                        new File(bpath.toUri()), new File(rpath.toUri()),
                        Movie.class, Ratings.class
                )
        );

        return ResponseEntity.ok(null);
    }

}
