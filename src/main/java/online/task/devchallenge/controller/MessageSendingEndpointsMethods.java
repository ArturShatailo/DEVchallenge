package online.task.devchallenge.controller;

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
    @PostMapping("/path")
    Map<String, String> directedMessage(@RequestBody MessageDTO messageDTO);

}
