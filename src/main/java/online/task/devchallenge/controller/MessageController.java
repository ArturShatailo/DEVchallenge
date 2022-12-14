package online.task.devchallenge.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import online.task.devchallenge.domain.Message;
import online.task.devchallenge.domain.MessageDirected;
import online.task.devchallenge.domain.messageDto.MessageDTO;
import online.task.devchallenge.service.MessageServiceBean;
import online.task.devchallenge.repository.util.mapper.MessageMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Message API", description = "Message API")
public class MessageController implements MessageSendingEndpointsMethods{

    private final MessageMapper messageMapper;

    private final MessageServiceBean messageServiceBean;

    @Override
    @PostMapping("/trusted/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Set<String>> broadcastMessageOnlyTrusted(@RequestBody MessageDTO messageDTO) {
        Message message = messageMapper.messageToObject(messageDTO);
        return messageMapper
                .messageToResponseDTO(messageServiceBean.broadcastToTrusted(message))
                .getDestinations();
    }

    @Override
    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Set<String>> broadcastMessage(@RequestBody MessageDTO messageDTO) {
        Message message = messageMapper.messageToObject(messageDTO);
        return messageServiceBean.broadcasting(message);
    }

    @Override
    @PostMapping("/path")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, String> directedMessage(@RequestBody MessageDTO messageDTO) {
        MessageDirected messageDirected = messageMapper.messageDirectedToObject(messageDTO);
        return messageServiceBean.directedBroadcasting(messageDirected);
    }

}
