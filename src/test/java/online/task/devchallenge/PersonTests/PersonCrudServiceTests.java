package online.task.devchallenge.PersonTests;

import online.task.devchallenge.domain.Person;
import online.task.devchallenge.repository.PersonRepository;
import online.task.devchallenge.service.PersonServiceBean;
import org.hamcrest.MatcherAssert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PersonCrudServiceTests {

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private PersonServiceBean personServiceBean;


    @Test
    public void whenSavePerson_shouldReturnPerson() {
        Person person = new Person();
        Set<String> topics = new HashSet<>();
        topics.add("Books");
        topics.add("Food");
        person.setId("Den");
        person.setTopics(topics);

        when(personRepository
                .save(ArgumentMatchers.any(Person.class)))
                .thenReturn(person);

        Person person1 = personServiceBean.create(person);

        assertThat(person1.getId()).isSameAs(person.getId());
        assertThat(person1.getTopics()).hasSameSizeAs(person.getTopics());
        assertThat(person1.getTopics()).hasSameElementsAs(person.getTopics());

        verify(personRepository).save(person);
    }

    @Test
    public void whenGivenId_shouldReturnPerson_ifFound() {
        String testId = "Den";
        Set<String> topics = new HashSet<>();
        topics.add("Books");
        topics.add("Food");
        Map<String, Integer> connections = new HashMap<>();
        connections.put("Ken", 10);
        connections.put("Diana", 6);
        Person person = new Person();
        person.setId(testId);
        person.setTopics(topics);
        person.setConnections(connections);

        when(personRepository
                .findById(person.getId()))
                .thenReturn(Optional.of(person));

        Person person1 = personServiceBean.getPersonById(testId);

        assertNotNull("Person with id : "+testId+" not found", person1);
        assertEquals(person.getId(), person1.getId());
        assertThat(person.getTopics()).hasSameElementsAs(person1.getTopics());
        assertThat(person.getConnections()).containsAllEntriesOf(person1.getConnections());

        assertThat(person1).isSameAs(person);
        verify(personRepository).findById(person.getId());
    }

    @Test
    public void whenGetAll_shouldReturnPersonsList() {
        String testId = "Den", testIdA = "Diana";
        Set<String> topics = new HashSet<>();
        topics.add("Books");
        topics.add("Food");
        Map<String, Integer> connections = new HashMap<>();
        connections.put("Ken", 10);
        connections.put("Diana", 6);
        Person person = new Person();
        person.setId(testId);
        person.setTopics(topics);
        person.setConnections(connections);

        Set<String> topicsA = new HashSet<>();
        topics.add("Books");
        topics.add("Food");
        Map<String, Integer> connectionsA = new HashMap<>();
        connections.put("Ken", 10);
        connections.put("Diana", 6);
        Person personA = new Person();
        person.setId(testIdA);
        person.setTopics(topicsA);
        person.setConnections(connectionsA);

        List<Person> persons = Arrays.asList(person, personA);

        when(personRepository.findAll()).thenReturn(persons);

        List<Person> persons1 = personServiceBean.gerAllPersons();

        MatcherAssert.assertThat(persons, is(persons1));
        MatcherAssert.assertThat(persons, hasSize(persons1.size()));
        MatcherAssert.assertThat(persons.size(), is(persons1.size()));
        MatcherAssert.assertThat(persons1, containsInAnyOrder(
                hasProperty("id", is(persons.get(0).getId())),
                hasProperty("id", is(persons.get(1).getId()))
        ));
        MatcherAssert.assertThat(persons, contains(persons1.get(0), persons1.get(1)));

        verify(personRepository).findAll();
    }

    @Test
    public void whenUpdatePersonsConnections_shouldReturnConnections() {
        Person person = new Person();
        Map<String, Integer> connections = new HashMap<>();
        connections.put("Ken", 10);
        connections.put("Diana", 6);
        person.setId("Den");
        person.setConnections(connections);

        Map<String, Integer> connectionsUpdate = new HashMap<>();
        connections.put("Nancy", 10);
        connections.put("Diana", 7);

        Person personA = new Person();
        Map<String, Integer> connectionsA = new HashMap<>();
        connectionsA.put("Ken", 10);
        connectionsA.put("Diana", 7);
        connectionsA.put("Nancy", 10);
        personA.setId("Den");
        personA.setConnections(connectionsA);

        when(personRepository
                .findById("Den"))
                .thenReturn(Optional.of(person));
        person = personServiceBean.getPersonById("Den");
        person.getConnections().putAll(connectionsUpdate);

        connectionsUpdate = personServiceBean.updateConnections("Den", connectionsUpdate);

        assertThat(personA.getConnections()).containsAllEntriesOf(connectionsUpdate);

        verify(personRepository).save(person);
    }

    @Test
    public void whenDeletePersonsConnections_shouldReturnConnections() {
        Person person = new Person();
        Map<String, Integer> connections = new HashMap<>();
        connections.put("Ken", 10);
        connections.put("Diana", 6);
        person.setId("Den");
        person.setConnections(connections);

        Map<String, Integer> connectionsUpdate = new HashMap<>();
        connectionsUpdate.put("Diana", 6);

        Person personA = new Person();
        Map<String, Integer> connectionsA = new HashMap<>();
        connectionsA.put("Ken", 10);
        personA.setConnections(connectionsA);

        when(personRepository
                .findById("Den"))
                .thenReturn(Optional.of(person));
        person = personServiceBean.getPersonById("Den");

        for (String key : connectionsUpdate.keySet())
            person.getConnections().remove(key);

        connectionsUpdate = personServiceBean.deleteConnections("Den", connectionsUpdate);

        assertThat(personA.getConnections()).doesNotContainEntry("Diana", connectionsUpdate.get("Diana"));

        verify(personRepository).save(person);
    }



}

