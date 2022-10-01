package online.task.devchallenge.service;

import lombok.AllArgsConstructor;
import online.task.devchallenge.domain.Message;
import online.task.devchallenge.domain.Person;
import online.task.devchallenge.repository.MessageRepository;
import online.task.devchallenge.repository.PersonRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MessageServiceBean {

    private final MessageRepository messageRepository;

    private final PersonRepository personRepository;

    public Message create(Message message) {

        Person personFrom = personRepository.findById(message.getFrom_person_id())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + message.getFrom_person_id()));

        Set<String> receiversIDs = new HashSet<>();

        personFrom.getConnections().forEach( (a, b) -> {
            if (b >= message.getMin_trust_level())
                receiversIDs.add(a);
        });

        message.getDestinations()
                .put(personFrom.getId(),
                        checkReceiversTopics(receiversIDs, message.getTopics()));

        //sending email/message/notification code may be here

        return messageRepository.save(message);
    }

    private Set<String> checkReceiversTopics(Set<String> receiversIDs, Set<String> topics) {

        return receiversIDs.stream()
                .filter(id -> checkCurrentReceiver(id, topics))
                .collect(Collectors.toSet());
    }

    private boolean checkCurrentReceiver(String id, Set<String> topics) {

        Person person = personRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + id));

        return person.getTopics().containsAll(topics);
    }

}
