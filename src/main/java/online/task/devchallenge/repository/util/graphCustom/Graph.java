package online.task.devchallenge.repository.util.graphCustom;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import online.task.devchallenge.domain.Person;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Graph {

    private Map<Person, List<Person>> adjVertices = new HashMap<>();

    //creates and adds "Edges" as a Person objects for this Graph according to the
    //received Person and it's connections.
    public void addEdge(Person p1, List<Person> connections) {
        adjVertices.put(p1, new ArrayList<>());
        for (Person p : connections) adjVertices.get(p1).add(p);
    }

}