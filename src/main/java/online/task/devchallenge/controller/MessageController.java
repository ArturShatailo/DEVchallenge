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

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {

    private final MessageMapper messageMapper;

    private final MessageServiceBean messageServiceBean;

    @PostMapping("/messages")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO newMessage(@RequestBody MessageDTO messageDTO) {
        Message message = messageMapper.messageToObject(messageDTO);
        return messageMapper.messageToResponseDTO(messageServiceBean.create(message));
    }

}
