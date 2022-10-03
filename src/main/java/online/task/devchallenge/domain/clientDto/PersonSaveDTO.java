package online.task.devchallenge.domain.clientDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.util.Set;

@Data
public class PersonSaveDTO {

    @Schema(description = "ID of person.", example = "Alberto")
    private String id;

    @Schema(description = "List of topics interested", example = "['Books', 'Movies']")
    private Set<String> topics;

}
