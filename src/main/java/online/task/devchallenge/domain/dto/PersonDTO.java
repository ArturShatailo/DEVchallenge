package online.task.devchallenge.domain.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import online.task.devchallenge.domain.Topic;

import java.util.Set;



@NoArgsConstructor
@Getter
@Setter
public class PersonDTO {

    private Integer id;

    private String name;

    private Set<String> topics;

}
