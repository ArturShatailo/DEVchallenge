package online.task.devchallenge.util.mapper;

import online.task.devchallenge.domain.Message;
import online.task.devchallenge.domain.messageDto.MessageResponseDTO;
import online.task.devchallenge.domain.messageDto.MessageDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = MessageMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MessageMapper {

    Message messageToObject(MessageDTO dto);

    MessageDTO messageToDTO(Message message);

    Message messageToObject(MessageResponseDTO dto);

    MessageResponseDTO messageToResponseDTO(Message message);
}
