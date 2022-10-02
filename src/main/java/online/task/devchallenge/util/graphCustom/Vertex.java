package online.task.devchallenge.util.graphCustom;

import lombok.Data;
import online.task.devchallenge.domain.Person;

@Data
public class Vertex {

    private Person person;

    Vertex(Person person) {
        this.person = person;
    }
}
