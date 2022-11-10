package co.empathy.academy.demo_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
public class DemoSearchApplication {

    public static void main(String[] args) {
	SpringApplication.run(DemoSearchApplication.class, args);
    }

}
