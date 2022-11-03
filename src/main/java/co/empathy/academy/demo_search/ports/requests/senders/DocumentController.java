package co.empathy.academy.demo_search.ports.requests.senders;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/document")
public class DocumentController {
    @PostMapping("/index")
    public void document(@RequestBody String document) {

    }

}
