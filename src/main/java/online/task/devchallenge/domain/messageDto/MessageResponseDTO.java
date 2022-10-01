package online.task.devchallenge.domain.messageDto;

import lombok.Data;
import java.util.Map;
import java.util.Set;

@Data
public class MessageResponseDTO {

    private Map<String, Set<String>> destinations;

}
