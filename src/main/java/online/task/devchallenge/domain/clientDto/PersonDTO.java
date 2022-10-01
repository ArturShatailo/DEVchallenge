package online.task.devchallenge.domain.clientDto;

import lombok.*;

import java.util.Set;



@NoArgsConstructor
@Getter
@Setter
public class PersonDTO {

    private Integer id;

    private String name;

    private Set<String> topics;

}
