package online.task.devchallenge.util.mapper;

import online.task.devchallenge.domain.Person;
import online.task.devchallenge.domain.clientDto.PersonSaveDTO;
import online.task.devchallenge.domain.clientDto.PersonViewDTO;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(
        componentModel = "spring",
        uses = PersonMapper.class,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PersonMapper {

    /**
     * Convert a Person object to a PersonSaveDTO object.
     *
     * @param object The Person object to convert.
     * @return PersonSaveDTO object.
     */
    PersonSaveDTO personToSaveDTO(Person object);


    /**
     * Convert a PersonSaveDTO to a Person object.
     *
     * @param dto The PersonSaveDTO to convert to a Person object.
     * @return Person object
     */
    Person personToObject(PersonSaveDTO dto);

    /**
     * Convert a Person object to a PersonViewDTO object.
     *
     * @param object The Person object to convert.
     * @return A PersonViewDTO object.
     */
    PersonViewDTO personToViewDTO(Person object);

}