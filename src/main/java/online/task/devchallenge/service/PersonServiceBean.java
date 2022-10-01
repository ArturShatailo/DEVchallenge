package online.task.devchallenge.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.task.devchallenge.domain.Person;
import online.task.devchallenge.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@AllArgsConstructor
@Service
public class PersonServiceBean {

    private final PersonRepository personRepository;

    public Person create(Person person) {
        return personRepository.save(person);
    }

    public Person getById(Integer id) {
        Person p = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + id));
        return p;
    }

    public Person getPerson(Person person) {
        Person p = personRepository.findById(person.getId())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + person.getId()));
        return p;
    }

}
