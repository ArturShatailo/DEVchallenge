package online.task.devchallenge.domain.messageDto;

import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
public class MessageDTO {

    private String text;

    private Set<String> topics = new HashSet<>();

    private String from_person_id;

    private Integer min_trust_level;

}
