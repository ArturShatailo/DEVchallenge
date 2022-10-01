package online.task.devchallenge.domain.messageDto;

import lombok.Data;
import java.util.Set;

@Data
public class MessageDTO {

    private String text;

    private Set<String> topics;

    private String from_person_id;

    private Integer min_trust_level;

}
