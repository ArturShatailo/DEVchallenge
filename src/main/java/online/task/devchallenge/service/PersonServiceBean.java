package online.task.devchallenge.service;

import lombok.AllArgsConstructor;
import online.task.devchallenge.domain.Person;
import online.task.devchallenge.repository.PersonRepository;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class PersonServiceBean implements PersonCrudService{

    private final PersonRepository personRepository;

    @Override
    public Person create(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person getPersonById(String id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + id));
    }

    @Override
    public List<Person> gerAllPersons(){
        return personRepository.findAll();
    }

    @Override
    public Map<String, Integer> updateConnections(String id, Map<String, Integer> trust_connections) {

        Person person = getPersonById(id);

        person.getConnections()
                .putAll(trust_connections);

        person = personRepository.save(person);

        return checkConnectionsAdded(person, trust_connections);
    }

    @Override
    public Map<String, Integer> deleteConnections(String id, Map<String, Integer> trust_connections) {

        Person person = getPersonById(id);
        Map<String, Integer> connections = person.getConnections();

        for (String key : trust_connections.keySet())
            connections.remove(key);

        personRepository.save(person);

        return trust_connections;
    }

     /*
     * The method will check if connections received as a Map are included in Person's connections.
     * In other words, method to check if connections were added.
     * Returns a Map of connections that were added.
     */
    private Map<String, Integer> checkConnectionsAdded(Person person, Map<String, Integer> trust_connections) {

        Map<String, Integer> returnConnectionsAdded = new HashMap<>();

        for (String key : trust_connections.keySet())
            if (person.getConnections().containsKey(key))
                returnConnectionsAdded.put(key, trust_connections.get(key));

        return returnConnectionsAdded;
    }



}
