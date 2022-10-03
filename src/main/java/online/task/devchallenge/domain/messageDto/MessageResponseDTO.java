package online.task.devchallenge.domain.messageDto;

import lombok.Data;
import java.util.Map;

@Data
public class MessageResponseDTO {

    private Map<String, String> destinations;

}
