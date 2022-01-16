package com.example.CreateRESTfulApi;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController //marks the class as a controller where every method returns a domain object instead of a view
public class GreetingController {

    private static final String template = "Hello, %s";
    private final AtomicLong counter = new AtomicLong();

    //When running go to http://localhost:8080/greeting for default
    //Or go to http://localhost:8080/greeting?name=NameHere to get other name arguments
    @GetMapping("/greeting") //ensures that HTTP GET requests to /greeting are mapped to the greeting() method
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name)  {
        //binds the value of the query string parameter name into the name parameter
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
