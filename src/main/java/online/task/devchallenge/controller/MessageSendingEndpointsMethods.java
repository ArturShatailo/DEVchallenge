package online.task.devchallenge.controller;

import online.task.devchallenge.domain.messageDto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.Map;
import java.util.Set;

public interface MessageSendingEndpointsMethods {

    @PostMapping("/trusted/messages")
    Map<String, Set<String>> broadcastMessageOnlyTrusted(@RequestBody MessageDTO messageDTO);

    @PostMapping("/messages")
    Map<String, Set<String>> broadcastMessage(@RequestBody MessageDTO messageDTO);

    @PostMapping("/path")
    Map<String, String> directedMessage(@RequestBody MessageDTO messageDTO);

}
