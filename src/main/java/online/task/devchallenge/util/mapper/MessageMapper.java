package online.task.devchallenge.util.mapper;

import online.task.devchallenge.domain.Message;
import online.task.devchallenge.domain.messageDto.MessageResponseDTO;
import online.task.devchallenge.domain.messageDto.MessageDTO;

public interface MessageMapper {

    Message messageToObject(MessageDTO dto);

    MessageDTO messageToDTO(Message message);

    Message messageToObject(MessageResponseDTO dto);

    MessageResponseDTO messageToResponseDTO(Message message);
}
