package online.task.devchallenge.domain.messageDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Map;
import java.util.Set;

@Data
public class MessageResponseDTO {

    @Schema(description = "Destinations of this message", example = "['Harry': ['Kelvin', 'Greg']]")
    private Map<String, Set<String>> destinations;

}
