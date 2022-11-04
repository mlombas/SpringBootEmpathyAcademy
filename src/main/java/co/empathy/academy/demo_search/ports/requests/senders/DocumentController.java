package co.empathy.academy.demo_search.ports.requests.senders;

import co.empathy.academy.demo_search.model.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/document")
public class DocumentController {
    @PostMapping("/index")
    public ResponseEntity<Movie> document(@RequestBody String document) {
        return ResponseEntity.badRequest().build();
    }

}
