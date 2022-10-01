package online.task.devchallenge.util.mapper;

import online.task.devchallenge.domain.Person;
import online.task.devchallenge.domain.Topic;
import online.task.devchallenge.domain.clientDto.PersonDTO;
import online.task.devchallenge.domain.clientDto.PersonSaveDTO;
import online.task.devchallenge.domain.clientDto.PersonViewDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PersonMapperImpl implements PersonMapper{


    @Override
    public PersonDTO personToDTO(Person object) {
        if (object == null) return null;

        PersonDTO personDTO = new PersonDTO();

        //personDTO.setId( object.getId() );
        //personDTO.setName( object.getName() );
        Set<String> topics = new HashSet<>();
        //object.getTopics().forEach(t -> topics.add(t.getName()));
        personDTO.setTopics( topics );

        return personDTO;
    }

    @Override
    public Person personToObject(PersonDTO dto) {

        if (dto == null) return null;

        Person person = new Person();

       // person.setId( dto.getId() );
        //person.setName( dto.getName() );
        Set<Topic> topics = new HashSet<>();
        dto.getTopics().forEach(t -> topics.add(new Topic(t)));
        //person.setTopics( topics );

        return person;

    }



    @Override
    public PersonSaveDTO personToSaveDTO(Person object) {
        if (object == null) return null;

        PersonSaveDTO personSaveDTO = new PersonSaveDTO();

        personSaveDTO.setId( object.getId() );
        personSaveDTO.setTopics( object.getTopics() );

        return personSaveDTO;
    }

    @Override
    public Person personToObject(PersonSaveDTO dto) {

        if (dto == null) return null;

        Person person = new Person();

        person.setId( dto.getId() );
        person.setTopics( dto.getTopics() );

        return person;

    }

    @Override
    public PersonViewDTO personToViewDTO(Person object) {
        if (object == null) return null;

        PersonViewDTO personViewDTO = new PersonViewDTO();

        personViewDTO.setId( object.getId() );
        personViewDTO.setTopics( object.getTopics() );
        personViewDTO.setConnections(object.getConnections());

        return personViewDTO;
    }

    @Override
    public Person personToObject(PersonViewDTO dto) {

        if (dto == null) return null;

        Person person = new Person();

        person.setId( dto.getId() );
        person.setTopics( dto.getTopics() );
        person.setConnections(dto.getConnections());

        return person;
    }

}
