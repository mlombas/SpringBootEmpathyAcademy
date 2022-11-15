package co.empathy.academy.demo_search.ports.requests.senders;

import co.empathy.academy.demo_search.model.Title;
import co.empathy.academy.demo_search.model.Ratings;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import co.empathy.academy.demo_search.ports.requests.commands.document.PipelineIndexCommand;
import co.empathy.academy.demo_search.ports.requests.commands.document.pipelines.DocumentZipperPipe;
import co.empathy.academy.demo_search.util.TSVReader;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Nullable;
import javax.print.Doc;
import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/index")
public class DocumentController {
    @Autowired
    private PRequestReactor reactor;

    @Operation(summary = "Index a list of documents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of documents indexed successfully"),
            @ApiResponse(responseCode = "500", description = "Error indexing the documents")
    })
    @Parameters(value = {
            @Parameter(name = "basics", required = true, description = "Basics document"),
            @Parameter(name = "ratings", required = false, description = "Ratings document, will be zipped with basics"),
    })
    @PostMapping("/")
    public ResponseEntity<Title> document(
            @RequestParam MultipartFile basics,
            @RequestParam Optional<MultipartFile> ratings
    ) throws IOException {
        Path bpath = Paths.get(".", basics.getOriginalFilename());
        basics.transferTo(bpath);

        var command = new PipelineIndexCommand<Title>(
                new TSVReader<>(
                        new File(bpath.toUri()),
                        Title.class,
                        "genres"
                )
        );

        if(ratings.isPresent()) {
            var inside = ratings.get();
            Path rpath = Paths.get(".", inside.getOriginalFilename());
            inside.transferTo(rpath);

            command.addPipe(
                    new DocumentZipperPipe<Title, Ratings>(
                            (m, r) -> m
                                    .withAverageRating(r.getAverageRating())
                                    .withNumVotes(r.getNumVotes()),
                            (m, r) -> m.getTconst().equals(r.getTconst()),
                            new TSVReader<>(new File(rpath.toUri()), Ratings.class)
                    )
            );
        }
        reactor.reactToDocument(command);

        return ResponseEntity.ok(null);
    }

}
