package online.task.devchallenge.util.mapper;

import online.task.devchallenge.domain.Person;
import online.task.devchallenge.domain.clientDto.PersonDTO;
import online.task.devchallenge.domain.clientDto.PersonSaveDTO;
import online.task.devchallenge.domain.clientDto.PersonViewDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PersonMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PersonMapper {

    PersonDTO personToDTO(Person object);

    Person personToObject(PersonDTO dto);

    PersonSaveDTO personToSaveDTO(Person object);

    Person personToObject(PersonSaveDTO dto);

    PersonViewDTO personToViewDTO(Person object);

    Person personToObject(PersonViewDTO dto);

}