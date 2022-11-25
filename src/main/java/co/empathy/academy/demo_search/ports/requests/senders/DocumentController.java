package co.empathy.academy.demo_search.ports.requests.senders;

import co.empathy.academy.demo_search.model.titles.*;
import co.empathy.academy.demo_search.ports.index.settings.PSettingsSetter;
import co.empathy.academy.demo_search.ports.requests.PRequestReactor;
import co.empathy.academy.demo_search.ports.requests.commands.SettingsCommand;
import co.empathy.academy.demo_search.ports.requests.commands.document.PipelineIndexCommand;
import co.empathy.academy.demo_search.ports.requests.commands.document.pipelines.DocumentZipperPipe;
import co.empathy.academy.demo_search.ports.requests.commands.settings.JsonSettingReader;
import co.empathy.academy.demo_search.ports.requests.commands.settings.SetJsonSettings;
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
            @RequestParam Optional<MultipartFile> ratings,
            @RequestParam Optional<MultipartFile> akas,
            @RequestParam Optional<MultipartFile> crew,
            @RequestParam Optional<MultipartFile> principals
    ) throws IOException {
        Path bpath = Paths.get(".", basics.getOriginalFilename());
        basics.transferTo(bpath);

        reactor.reactToSettings(new SetJsonSettings(
                new JsonSettingReader(
                        "custom_analyzer.json", "mapping.json"
                )
        ));

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
                            (m, r) -> m,
                                    //.withAverageRating(r.getAverageRating())
                                    //.withNumVotes(r.getNumVotes()),
                            (m, r) -> m.getTconst().equals(r.getTconst()),
                            new TSVReader<>(new File(rpath.toUri()), Ratings.class)
                    )
            );
        }

        if(akas.isPresent()) {
            var inside = akas.get();
            Path apath = Paths.get(".", inside.getOriginalFilename());
            inside.transferTo(apath);

            command.addPipe(
                    new DocumentZipperPipe<Title, FullAka>(
                            (m, fa) -> m
                                        .withOneMoreAka(fa.getBaseAka()),
                            (m, fa) -> m.getTconst().equals(fa.getTitleId()),
                            new TSVReader<>(new File(apath.toUri()), FullAka.class)
                    )
            );
        }
        
        if(crew.isPresent()) {
            var inside = crew.get();
            Path cpath = Paths.get(".", inside.getOriginalFilename());
            inside.transferTo(cpath);

            command.addPipe(
                    new DocumentZipperPipe<Title, Crew>(
                            (m, c) -> m
                                    .withOneMoreDirector(c.getDirectors())
                                    .withOneMoreWriter(c.getWriters()),
                            (m, c) -> m.getTconst().equals(c.getTconst()),
                            new TSVReader<>(new File(cpath.toUri()), Crew.class)
                    )
            );
        }

        if(principals.isPresent()) {
            var inside = principals.get();
            Path ppath = Paths.get(".", inside.getOriginalFilename());
            inside.transferTo(ppath);

            command.addPipe(
                    new DocumentZipperPipe<Title, Principals>(
                            (m, p) -> m
                                    .withOneMoreStarring(p.getStarring()),
                            (m, p) -> (
                                    Integer.parseInt(m.getTconst().substring(2)) ==
                                    Integer.parseInt(p.getTconst().substring(2))
                                    ),
                            new TSVReader<>(new File(ppath.toUri()), Principals.class)
                    )
            );
        }

        reactor.reactToDocument(command);

        return ResponseEntity.ok(null);
    }

}
