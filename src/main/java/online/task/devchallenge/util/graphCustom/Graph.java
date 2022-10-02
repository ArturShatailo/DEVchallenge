package online.task.devchallenge.util.graphCustom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.task.devchallenge.domain.Person;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Graph {

    private Map<Person, List<Person>> adjVertices = new HashMap<>();

    //receive all connections allowed
    public void addEdge(Person p1, List<Person> connections) {

        adjVertices.put(p1, new ArrayList<>());

        for (Person p : connections) {
            adjVertices.get(p1).add(p);
        }
    }

}