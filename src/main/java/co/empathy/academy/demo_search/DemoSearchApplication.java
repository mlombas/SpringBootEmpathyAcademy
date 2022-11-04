package co.empathy.academy.demo_search;

import co.empathy.academy.demo_search.model.Movie;
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
import java.util.Arrays;
import java.util.Map;

@SpringBootApplication
@RestController
public class DemoSearchApplication {

    public static void main(String[] args) {
	SpringApplication.run(DemoSearchApplication.class, args);
    }

}
