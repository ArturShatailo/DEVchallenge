package online.task.devchallenge.util.mapper;

import online.task.devchallenge.domain.Person;
import online.task.devchallenge.domain.clientDto.PersonSaveDTO;
import online.task.devchallenge.domain.clientDto.PersonViewDTO;
import org.springframework.stereotype.Component;

@Component
public class PersonMapperImpl implements PersonMapper{

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

}
