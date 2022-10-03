package online.task.devchallenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "This is endpoint to save person in database",
            description = "Create request to save person in database.",
            tags = {"Person API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The person has been successfully saved in database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "409", description = "conflict in the request")})
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
    @Operation(summary = "This is endpoint to save connections of the person in database",
            description = "Create request to save connections of the person in database.",
            tags = {"Person API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The person's connections has been successfully saved in database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified person request not found."),
            @ApiResponse(responseCode = "409", description = "conflict in the request")})
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
    @Operation(summary = "This is endpoint to delete connections from the person in database",
            description = "Create request to delete connections from the person in database.",
            tags = {"Person API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "DELETED. The person's connections has been successfully deleted in database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified person request not found."),
            @ApiResponse(responseCode = "409", description = "conflict in the request")})
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
    @Operation(summary = "This is endpoint to get person from database by id",
            description = "Create request to get person from database by id.",
            tags = {"Person API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "FOUND. The person's connections has been successfully deleted in database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified person request not found."),
            @ApiResponse(responseCode = "409", description = "conflict in the request")})
    @GetMapping("/people/{id}")
    PersonViewDTO getPerson(@PathVariable("id") String id);

}
