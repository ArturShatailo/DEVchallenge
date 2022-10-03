package online.task.devchallenge.domain.messageDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Set;

@Data
public class MessageDTO {

    @Schema(description = "Text of the message", example = "Some interesting news")
    private String text;

    @Schema(description = "List of topics that this message is related to", example = "['Books', 'Movies']")
    private Set<String> topics;

    @Schema(description = "ID of sender Person", example = "Alberto")
    private String from_person_id;

    @Schema(description = "Min trusted level that is used to define if Person should receive the message or not", example = "10")
    private Integer min_trust_level;

}
