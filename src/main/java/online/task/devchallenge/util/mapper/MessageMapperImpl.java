package online.task.devchallenge.util.mapper;

import online.task.devchallenge.domain.Message;
import online.task.devchallenge.domain.messageDto.MessageResponseDTO;
import online.task.devchallenge.domain.messageDto.MessageDTO;
import org.springframework.stereotype.Component;

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
    public MessageDTO messageToDTO(Message object) {
        if (object == null) return null;

        MessageDTO messageDTO = new MessageDTO();

        messageDTO.setText( object.getText() );
        messageDTO.setTopics( object.getTopics() );
        messageDTO.setFrom_person_id( object.getFrom_person_id() );
        messageDTO.setMin_trust_level( object.getMin_trust_level() );

        return messageDTO;
    }

    @Override
    public Message messageToObject(MessageResponseDTO dto) {
        if (dto == null) return null;

        Message message = new Message();
        message.setDestinations( dto.getDestinations() );

        return message;
    }

    @Override
    public MessageResponseDTO messageToResponseDTO(Message object) {

        if (object == null) return null;

        MessageResponseDTO messageResponseDTO = new MessageResponseDTO();
        messageResponseDTO.setDestinations( object.getDestinations() );

        return messageResponseDTO;
    }
}
