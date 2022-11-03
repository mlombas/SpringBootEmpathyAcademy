package co.empathy.academy.demo_search.ports.requests.senders;

import co.empathy.academy.demo_search.model.Movie;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/document")
public class DocumentController {
    @PostMapping("/index")
    public void document(@RequestBody String document) {

    }

}
