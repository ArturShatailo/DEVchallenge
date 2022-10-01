package online.task.devchallenge.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.task.devchallenge.domain.Person;
import online.task.devchallenge.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class PersonServiceBean {

    private final PersonRepository personRepository;

    public Person create(Person person) {
        return personRepository.save(person);
    }

    public Person getPersonById(String id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + id));
    }

    public Map<String, Integer> updateConnections(String id, Map<String, Integer> trust_connections) {

        Person person = getPersonById(id);

        person.getConnections()
                .putAll(trust_connections);

        person = personRepository.save(person);

        return checkConnectionsAdded(person, trust_connections);
    }

    private Map<String, Integer> checkConnectionsAdded(Person person, Map<String, Integer> trust_connections) {

        Map<String, Integer> returnConnectionsAdded = new HashMap<>();

        for (String key : trust_connections.keySet())
            if (person.getConnections().containsKey(key))
                returnConnectionsAdded.put(key, trust_connections.get(key));

        return returnConnectionsAdded;
    }

    public Map<String, Integer> deleteConnections(String id, Map<String, Integer> trust_connections) {

        Person person = getPersonById(id);
        Map<String, Integer> connections = person.getConnections();

        for (String key : trust_connections.keySet())
            connections.remove(key);

        personRepository.save(person);

        return trust_connections;
    }

//    public Person getById(Integer id) {
//        Person p = personRepository.findById(id)
//                .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + id));
//        return p;
//    }

    /*public Person getPerson(Person person) {
        Person p = personRepository.findById(person.getId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + person.getId()));
        return p;
    }*/

}
