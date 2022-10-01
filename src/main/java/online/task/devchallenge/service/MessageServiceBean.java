package online.task.devchallenge.service;

import lombok.AllArgsConstructor;
import online.task.devchallenge.domain.Message;
import online.task.devchallenge.domain.Person;
import online.task.devchallenge.repository.MessageRepository;
import online.task.devchallenge.repository.PersonRepository;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MessageServiceBean {

    private final MessageRepository messageRepository;

    private final PersonRepository personRepository;

    public Message broadcast(Message message) {

        Person personFrom = personRepository.findById(message.getFrom_person_id())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + message.getFrom_person_id()));

        Set<String> receiversIDs = new HashSet<>();

        personFrom.getConnections().forEach( (a, b) -> {
            if (b >= message.getMin_trust_level())
                receiversIDs.add(a);
        });

        message.setDestinations(
                checkReceiversTopics(receiversIDs, message.getTopics()));

        //sending mail/message/notification code may be here

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


    public Map<String, Set<String>> broadcasting (Message message, Set<String> stack, Map<String, Set<String>> response) {

        stack.add(message.getFrom_person_id());

        Person personFrom = personRepository.findById(message.getFrom_person_id())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + message.getFrom_person_id()));

        Set<String> ids = new HashSet<>();

        personFrom.getConnections().forEach( (a, b) -> {

            System.err.println("-----------------------");

            System.err.println(stack.contains(a));
            System.err.println("stack: " + stack);
            System.err.println("object: " + a);

            if (stack.contains(a)) return;

            if (b >= message.getMin_trust_level()){
                stack.add(a);
                ids.add(a);

                System.err.println("object: " + a);
                System.err.println("ids: " + ids);

            }
        });

        Set<String> filterIds = checkReceiversTopics(ids, message.getTopics());

        message.setDestinations(filterIds);

        for (String a : filterIds) {

            if (filterIds.size() == 0) {
                System.err.println("size is 0");
                return response;
            }

            messageRepository.save(message);
            response.put(message.getFrom_person_id(), message.getDestinations());

            response.putAll(
                    broadcasting(new Message(
                                    message.getText(),
                                    message.getTopics(),
                                    a,
                                    message.getMin_trust_level()), stack, response));
        }

        System.err.println(response);

        return response;
    }

}
