package online.task.devchallenge.domain.clientDto;

import lombok.Data;
import java.util.Map;
import java.util.Set;

@Data
public class PersonViewDTO {

    private String id;

    private Set<String> topics;

    private Map<String, Integer> connections;

}
