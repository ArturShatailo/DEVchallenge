package online.task.devchallenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import online.task.devchallenge.domain.messageDto.MessageDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Map;
import java.util.Set;

public interface MessageSendingEndpointsMethods {

    /**
     * It takes a messageDTO in RequestBody, and broadcasts it to all the users who are trusted
     * by the sender
     *
     * @param messageDTO The messageDTO object to be broadcast.
     * @return A map with pair (sender -> set of receivers).
     */
    @Operation(summary = "This is endpoint to send message to trusted persons and save into database",
            description = "Create request to send message to trusted persons and save into database",
            tags = {"Message API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The message has been successfully saved in database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified message request not found."),
            @ApiResponse(responseCode = "409", description = "conflict in the request")})
    @PostMapping("/trusted/messages")
    Map<String, Set<String>> broadcastMessageOnlyTrusted(@RequestBody MessageDTO messageDTO);


    /**
     * It takes a messageDTO in RequestBody from the user, and broadcasts it to all the trusted persons
     * making them becoming a parts of messages chain.
     *
     * @param messageDTO The messageDTO object to be broadcast.
     * @return A map with pairs (sender -> set of receivers) where included all the persons trusted
     * one by one in chains and with appropriate topics and their receivers as sets.
     */
    @Operation(summary = "This is endpoint to send message to trusted persons and their trusted persons and so on and save " +
            "all messages into database",
            description = "Create request to send messages to and save them into database",
            tags = {"Message API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The messages have been successfully saved in database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified message request not found."),
            @ApiResponse(responseCode = "409", description = "conflict in the request")})
    @PostMapping("/messages")
    Map<String, Set<String>> broadcastMessage(@RequestBody MessageDTO messageDTO);

    /**
     * This method takes a messageDTO object as a parameter and sends message to the nearest person
     * that is included in trusted chain and have the appropriate topics.
     * Intermediate participants of the chain don't have appropriate topics.
     *
     * @param messageDTO The message to be sent.
     * @return A map with a key of "from" that is sender person and a value of "chain" that is the shortest
     * path to another person with available trusted level in a chain and appropriate topics.
     */
    @Operation(summary = "This is endpoint to send message in network and define the nearest receivers according to the topics " +
            "condition",
            description = "Create request to send message to and save it into database",
            tags = {"Message API"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED. The message has been successfully saved in database."),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND. Specified message request not found."),
            @ApiResponse(responseCode = "409", description = "conflict in the request")})
    @PostMapping("/path")
    Map<String, String> directedMessage(@RequestBody MessageDTO messageDTO);

}
