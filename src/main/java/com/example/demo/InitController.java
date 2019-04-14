package com.example.demo;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;

@RestController
public class InitController {

    private StringBuffer triples = new StringBuffer();

    @RequestMapping(value = "/fetch-all", method = RequestMethod.GET)
    public String getTriples() {
        return triples.toString();
    }

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public String query(@RequestParam String sparql, @RequestParam String endpoint){
        QueryExecution execute = QueryExecutionFactory.sparqlService(endpoint, QueryFactory.create(sparql));
        Model results = execute.execConstruct();
        execute.close();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        results.write(stream, "NTRIPLES") ;
        String triplesResult = stream.toString();
        triples.append(triplesResult);
        return triplesResult;
    }

}
