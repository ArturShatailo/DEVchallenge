package online.task.devchallenge.controller;

import lombok.AllArgsConstructor;
import online.task.devchallenge.domain.Person;
import online.task.devchallenge.domain.dto.PersonDTO;
import online.task.devchallenge.domain.dto.PersonSaveDTO;
import online.task.devchallenge.service.PersonServiceBean;
import online.task.devchallenge.util.mapper.PersonMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    private final PersonMapper personConverter;

    private final PersonServiceBean personServiceBean;

    @PostMapping("/people/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Person savePerson(@RequestBody PersonDTO personDTO) {
        Person person = personConverter.personToObject(personDTO);
        return personServiceBean.create(person);
    }


    @PostMapping("/people/{id}/trust_connections")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Integer> savePerson(@PathVariable ("id") Integer id, @RequestBody Map<String, Integer> trust_connections) {

        //add connections ot update them

        return trust_connections;
    }

    /*@GetMapping("/people")
    @ResponseStatus(HttpStatus.OK)
    public Person getClient(@RequestBody PersonDTO personDTO) {
        Person person = personConverter.personToObject(personDTO);
        return personServiceBean.getPerson(person);
    }*/

    @GetMapping("/people/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO getPerson(@PathVariable("id") int id) {
        Person person = personServiceBean.getById(id);
        return personConverter.personToDTO(person);
    }

}
