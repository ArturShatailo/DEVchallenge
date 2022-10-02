package online.task.devchallenge.util.mapper;

import online.task.devchallenge.domain.Message;
import online.task.devchallenge.domain.MessageDirected;
import online.task.devchallenge.domain.messageDto.MessageResponseDTO;
import online.task.devchallenge.domain.messageDto.MessageDTO;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class MessageMapperImpl implements MessageMapper{

    @Override
    public Message messageToObject(MessageDTO dto) {
        if (dto == null) return null;

        Message message = new Message();

        message.setText( dto.getText() );
        message.setTopics( dto.getTopics() );
        message.setFrom_person_id( dto.getFrom_person_id() );
        message.setMin_trust_level( dto.getMin_trust_level() );

        return message;
    }

    @Override
    public MessageDirected messageDirectedToObject(MessageDTO dto) {
        if (dto == null) return null;

        MessageDirected messageDirected = new MessageDirected();

        messageDirected.setText( dto.getText() );
        messageDirected.setTopics( dto.getTopics() );
        messageDirected.setFrom_person_id( dto.getFrom_person_id() );
        messageDirected.setMin_trust_level( dto.getMin_trust_level() );

        return messageDirected;
    }

    @Override
    public MessageResponseDTO messageToResponseDTO(Message object) {

        if (object == null) return null;

        MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
        Map<String, Set<String>> destinations = new HashMap<>();
        destinations.put(object.getFrom_person_id(), object.getDestinations());
        messageResponseDTO.setDestinations( destinations );

        return messageResponseDTO;
    }
}
