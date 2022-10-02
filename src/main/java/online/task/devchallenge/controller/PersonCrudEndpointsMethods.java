package online.task.devchallenge.controller;

import online.task.devchallenge.domain.clientDto.PersonSaveDTO;
import online.task.devchallenge.domain.clientDto.PersonViewDTO;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

public interface PersonCrudEndpointsMethods {

    /**
     * It takes a PersonSaveDTO object as a parameter, saves a new Person in database and returns
     * this saved Person DTO object
     *
     * @param personSaveDTO The object that will be saved in the database.
     * @return PersonSaveDTO object that has been saved
     */
    @PostMapping("/people")
    PersonSaveDTO savePerson(@RequestBody PersonSaveDTO personSaveDTO);

    /**
     * This method takes a person's id and a map of trust connections that should be saved or updated,
     * according to the key value existence.
     *
     * @param id The id of the person which trust connections should be updated or saved.
     * @param trust_connections A map of key: "person id" and value: "trust level".
     * @return A saved or updated map of the trust connections.
     */
    @PostMapping("/people/{id}/trust_connections")
    Map<String, Integer> saveConnections(
            @PathVariable("id") String id,
            @RequestBody Map<String, Integer> trust_connections);

    /**
     * This method deletes the trust connections of a person with the given id
     *
     * @param id The id of the person whose trust connections should be deleted.
     * @param trust_connections A map of the person's id and trust level.
     * @return A map of the trust connections that were deleted.
     */
    @DeleteMapping("/people/{id}/trust_connections")
    Map<String, Integer> deleteConnections(
            @PathVariable ("id") String id,
            @RequestBody Map<String, Integer> trust_connections);

    /**
     * Get a person by id.
     *
     * @param id The id of the person to retrieve.
     * @return PersonViewDTO object with specific fields
     */
    @GetMapping("/people/{id}")
    PersonViewDTO getPerson(@PathVariable("id") String id);

}
