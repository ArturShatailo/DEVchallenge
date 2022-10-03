package online.task.devchallenge.service;

import online.task.devchallenge.domain.Person;

import java.util.List;
import java.util.Map;

public interface PersonCrudService {

    /**
     * Create a new person and calls save() Spring JpaRepository method from
     * PersonRepository (injected in constructor).
     *
     * @param person The person object to create.
     * @return A Person object that has been created.
     */
    Person create(Person person);

    /**
     * This function returns a Person object with the given id by calling
     * findById() Spring JpaRepository method from PersonRepository (injected in constructor).
     * In case of there are no such person, throws EntityNotFoundException.
     *
     * @param id The id of the person that should be found in database.
     * @return A Person object
     */
    Person getPersonById(String id);

    /**
     * This function returns a list of all persons from database by calling
     * findAll() Spring JpaRepository method from PersonRepository (injected in constructor).
     *
     * @return A list of Person objects.
     */
    List<Person> gerAllPersons();

    /**
     * This method finds by id a Person from database and updates connections of this Person.
     *
     * @param id The id of the Person whose trust connections are being updated.
     * @param trust_connections A map of the connections of the person. The key is the id of the Person and the
     *        value is the trust level.
     * @return A map of the trust connections that were updated or/and added.
     */
    Map<String, Integer> updateConnections(String id, Map<String, Integer> trust_connections);

    /**
     * his method finds by id a Person from database and deletes connections of this Person.
     *
     * @param id The id of the person whose connections should be deleted.
     * @param trust_connections A map of the connections that should be deleted.
     *                          The key is the id of the person, and the value is the trust level.
     * @return A map of the trust connections.
     */
    Map<String, Integer> deleteConnections(String id, Map<String, Integer> trust_connections);

}
