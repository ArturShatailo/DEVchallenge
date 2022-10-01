package online.task.devchallenge.domain.clientDto;

import lombok.Data;
import java.util.Set;

@Data
public class PersonSaveDTO {

    private String id;

    private Set<String> topics;

}
