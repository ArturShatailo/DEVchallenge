package online.task.devchallenge.util.mapper;

import online.task.devchallenge.domain.Message;
import online.task.devchallenge.domain.MessageDirected;
import online.task.devchallenge.domain.messageDto.MessageResponseDTO;
import online.task.devchallenge.domain.messageDto.MessageDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = MessageMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MessageMapper {

    /**
     * Convert a MessageDTO to a Message.
     *
     * @param dto The DTO to convert to an object.
     * @return A Message object
     */
    Message messageToObject(MessageDTO dto);

    /**
     * It converts a MessageDTO to a MessageDirected.
     *
     * @param dto The DTO that is being converted to a MessageDirected object.
     * @return A MessageDirected object.
     */
    MessageDirected messageDirectedToObject(MessageDTO dto);

    /**
     * It converts a MessageDTO to a MessageResponseDTO.
     *
     * @param message The message object to be converted to a MessageResponseDTO object.
     * @return A MessageResponseDTO object.
     */
    MessageResponseDTO messageToResponseDTO(Message message);
}
