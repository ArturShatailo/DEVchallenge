package online.task.devchallenge;

import lombok.RequiredArgsConstructor;
import online.task.devchallenge.domain.Connection;
import online.task.devchallenge.domain.Person;
import online.task.devchallenge.domain.Topic;
import online.task.devchallenge.repository.ConnectionRepository;
import online.task.devchallenge.repository.PersonRepository;
import online.task.devchallenge.repository.TopicRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@RequiredArgsConstructor
@SpringBootApplication
public class DevChallengeApplication implements CommandLineRunner {

	/*private final PersonRepository personRepository;
	private final TopicRepository topicRepository;

	private final ConnectionRepository connectionRepository;*/

	public static void main(String[] args) {
		SpringApplication.run(DevChallengeApplication.class, args);
	}

	@Override
	@Transactional
	public void run(String... strings) throws Exception {
/*
		Connection c;
		Topic t;
		Person p;

		Topic t1 = new Topic("Spring Boot");
		Topic t2 = new Topic("Spring Data JPA");

		Person p1 = new Person("HelloKoding 1");
		Person p2 = new Person("HelloKoding 2");
		Person p3 = new Person("HelloKoding 3");

		Connection c1 = new Connection(p1, p2, 10);
		Connection c2 = new Connection(p2, p1, 5);
		Connection c3 = new Connection(p1, p3, 8);

		p1.getTopics().add(t1);
		p2.getTopics().add(t1);
		p2.getTopics().add(t2);

		t1.getPersons().add(p1);
		t1.getPersons().add(p2);
		t2.getPersons().add(p2);

		p1.getConnections().add(c1);
		p2.getConnections().add(c2);
		p1.getConnections().add(c3);

		t = topicRepository.save(t1);
		System.err.println("Topic1 saved "+t.getId()+t.getName());
		t = topicRepository.save(t2);
		System.err.println("Topic2 saved "+t.getId()+t.getName());

		p = personRepository.save(p1);
		System.err.println("Person1 saved "+p.getId()+p.getName());
		p = personRepository.save(p2);
		System.err.println("Person2 saved "+p.getId()+p.getName());
		p = personRepository.save(p3);
		System.err.println("Person3 saved "+p.getId()+p.getName());

		c = connectionRepository.save(c1);
		System.err.println("Connection1 saved "+c.getId());
		c = connectionRepository.save(c2);
		System.err.println("Connection2 saved "+c.getId());
		c = connectionRepository.save(c3);
		System.err.println("Connection3 saved "+c.getId());
		System.err.println("That's it. enough");

		System.err.println("Connections p1: "+p1.getConnections().size());
		p1.getConnections().forEach(e -> System.err.println(e.getId()+" "));
		System.err.println("Connections p2: "+p2.getConnections().size());
		p2.getConnections().forEach(e -> System.err.println(e.getId()+" "));
		System.err.println("Connections p3: "+p3.getConnections().size());*/
	}

}
