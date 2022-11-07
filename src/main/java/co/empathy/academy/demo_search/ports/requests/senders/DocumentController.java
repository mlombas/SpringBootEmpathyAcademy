package co.empathy.academy.demo_search.ports.requests.senders;

import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import co.empathy.academy.demo_search.ports.requests.commands.DocumentCommand;
import co.empathy.academy.demo_search.ports.requests.commands.document.BulkIndexCommand;
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
    public ResponseEntity<Movie> document(@RequestParam MultipartFile document) throws IOException {
        Path fpath = Paths.get(".", document.getOriginalFilename());
        document.transferTo(fpath);
        reactor.reactToDocument(
                new BulkIndexCommand<>(
                        new File(fpath.toUri()),
                        Movie.class
                )
        );

        return ResponseEntity.ok(null);
    }

}
