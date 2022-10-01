package online.task.devchallenge.util.mapper;

import online.task.devchallenge.domain.Person;
import online.task.devchallenge.domain.dto.PersonDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = PersonMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PersonMapper {

    PersonDTO personToDTO(Person object);

    Person personToObject(PersonDTO dto);

}