package co.empathy.academy.demo_search;

import co.empathy.academy.demo_search.model.Movie;
import co.empathy.academy.demo_search.services.SearchService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.web.bind.annotation.*;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@SpringBootApplication
@RestController
public class DemoSearchApplication {

    @Autowired
    SearchService service;

    public static void main(String[] args) {
	SpringApplication.run(DemoSearchApplication.class, args);
    }

    @GetMapping("/search")
    public String search(@RequestParam String query) throws IOException {
        JSONObject response = new JSONObject();
        response.put("query", query);
        response.put("clusterName", service.engineVersion());

        return response.toString();
    }

    @PostMapping("/document")
    public void document(@RequestBody String document) {
        service.postMovie(Movie.make(document));
    }

    @GetMapping("/genres/{and}")
    public String genres(@PathVariable boolean and, @RequestBody String document) {
        return new JSONArray(service.searchGenres(document, and)).toString();
    }
    @GetMapping("/genres")
    public String genres(@RequestBody String document) {
        return new JSONArray(service.searchGenres(document)).toString();
    }

    @GetMapping("/intitle")
    public String intitle(@RequestBody String intitle) {
        return new JSONArray(service.searchTitle(intitle)).toString();
    }

}
