package online.task.devchallenge.controller;

import lombok.AllArgsConstructor;
import online.task.devchallenge.domain.Message;
import online.task.devchallenge.domain.messageDto.MessageResponseDTO;
import online.task.devchallenge.domain.messageDto.MessageDTO;
import online.task.devchallenge.service.MessageServiceBean;
import online.task.devchallenge.util.mapper.MessageMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController implements MessageSendingEndpointsMethods{

    private final MessageMapper messageMapper;

    private final MessageServiceBean messageServiceBean;

    @Override
    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Set<String>> broadcastMessage(@RequestBody MessageDTO messageDTO) {
        Message message = messageMapper.messageToObject(messageDTO);
        return messageMapper
                .messageToResponseDTO(messageServiceBean.broadcast(message))
                .getDestinations();
    }

    @PostMapping("/messages1")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Set<String>> broadcastMessage1(@RequestBody MessageDTO messageDTO) {
        Message message = messageMapper.messageToObject(messageDTO);
        return messageServiceBean.broadcasting(message, new HashSet<>(), new HashMap<>());
    }

    @Override
    @PostMapping("/path")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Set<String>> directedMessage(@RequestBody MessageDTO messageDTO) {
        return null;
    }

}
