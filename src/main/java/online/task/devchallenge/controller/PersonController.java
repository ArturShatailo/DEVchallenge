package online.task.devchallenge.controller;

import lombok.AllArgsConstructor;
import online.task.devchallenge.domain.Person;
import online.task.devchallenge.domain.clientDto.PersonSaveDTO;
import online.task.devchallenge.domain.clientDto.PersonViewDTO;
import online.task.devchallenge.service.PersonServiceBean;
import online.task.devchallenge.util.mapper.PersonMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class PersonController {

    private final PersonMapper personConverter;

    private final PersonServiceBean personServiceBean;

//    @PostMapping("/people/create")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Person savePerson(@RequestBody PersonDTO personDTO) {
//        Person person = personConverter.personToObject(personDTO);
//        return personServiceBean.create(person);
//    }

//    @PostMapping("/people/{id}/trust_connections")
//    @ResponseStatus(HttpStatus.CREATED)
//    public Map<String, Integer> savePerson(@PathVariable ("id") Integer id, @RequestBody Map<String, Integer> trust_connections) {
//
//        //add connections ot update them
//
//        return trust_connections;
//    }

//    @GetMapping("/people/{id}")
//    @ResponseStatus(HttpStatus.OK)
//    public PersonDTO getPerson(@PathVariable("id") int id) {
//        Person person = personServiceBean.getById(id);
//        return personConverter.personToDTO(person);
//    }



    @PostMapping("/people")
    @ResponseStatus(HttpStatus.CREATED)
    public PersonSaveDTO savePerson(@RequestBody PersonSaveDTO personSaveDTO) {
        Person person = personConverter.personToObject(personSaveDTO);
        person = personServiceBean.create(person);
        return personConverter.personToSaveDTO(person);
    }

    @PostMapping("/people/{id}/trust_connections")
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Integer> saveConnections(@PathVariable ("id") String id, @RequestBody Map<String, Integer> trust_connections) {
        return personServiceBean.updateConnections(id, trust_connections);
    }

    @DeleteMapping("/people/{id}/trust_connections")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, Integer> deleteConnections(@PathVariable ("id") String id, @RequestBody Map<String, Integer> trust_connections) {
        return personServiceBean.deleteConnections(id, trust_connections);
    }

    @GetMapping("/people/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonViewDTO getPerson(@PathVariable("id") String id) {
        Person person = personServiceBean.getPersonById(id);
        return personConverter.personToViewDTO(person);
    }


}
