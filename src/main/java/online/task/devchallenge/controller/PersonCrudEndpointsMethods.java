package online.task.devchallenge.controller;

import online.task.devchallenge.domain.clientDto.PersonSaveDTO;
import online.task.devchallenge.domain.clientDto.PersonViewDTO;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

public interface PersonCrudEndpointsMethods {

    @PostMapping("/people")
    PersonSaveDTO savePerson(@RequestBody PersonSaveDTO personSaveDTO);

    @PostMapping("/people/{id}/trust_connections")
    Map<String, Integer> saveConnections(
            @PathVariable("id") String id,
            @RequestBody Map<String, Integer> trust_connections);

    @DeleteMapping("/people/{id}/trust_connections")
    Map<String, Integer> deleteConnections(
            @PathVariable ("id") String id,
            @RequestBody Map<String, Integer> trust_connections);

    @GetMapping("/people/{id}")
    PersonViewDTO getPerson(@PathVariable("id") String id);

}
