package online.task.devchallenge.domain.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class PersonSaveDTO {

    private String id;

    private Set<String> topics;

}
