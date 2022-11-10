package co.empathy.academy.demo_search.ports.requests.senders;

import co.empathy.academy.demo_search.model.Title;
import co.empathy.academy.demo_search.model.Ratings;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import co.empathy.academy.demo_search.ports.requests.commands.document.PipelineIndexCommand;
import co.empathy.academy.demo_search.ports.requests.commands.document.pipelines.DocumentZipperPipe;
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
@RequestMapping("/index")
public class DocumentController {
    @Autowired
    private PRequestReactor reactor;

    @PostMapping("/")
    public ResponseEntity<Title> document(
            @RequestParam MultipartFile basic,
            @RequestParam MultipartFile ratings
    ) throws IOException {
        Path bpath = Paths.get(".", basic.getOriginalFilename());
        basic.transferTo(bpath);
        Path rpath = Paths.get(".", ratings.getOriginalFilename());
        ratings.transferTo(rpath);

        var command = new PipelineIndexCommand<Title>(
                new TSVReader<>(
                        new File(bpath.toUri()),
                        Title.class,
                        "genres"
                )
        );
        command.addPipe(
                new DocumentZipperPipe<Title, Ratings>(
                        (m, r) -> m
                                .withAverageRating(r.getAverageRating())
                                .withNumVotes(r.getNumVotes()),
                        (m, r) -> m.getTconst().equals(r.getTconst()),
                        new TSVReader<>(new File(rpath.toUri()), Ratings.class)
                )
        );
        reactor.reactToDocument(command);

        return ResponseEntity.ok(null);
    }

}
