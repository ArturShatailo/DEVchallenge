package online.task.devchallenge.service;

import lombok.AllArgsConstructor;
import online.task.devchallenge.domain.Message;
import online.task.devchallenge.domain.MessageDirected;
import online.task.devchallenge.domain.Person;
import online.task.devchallenge.repository.MessageDirectedRepository;
import online.task.devchallenge.repository.MessageRepository;
import online.task.devchallenge.repository.PersonRepository;
import online.task.devchallenge.util.graphCustom.Graph;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class MessageServiceBean {

    private final MessageRepository messageRepository;

    private final MessageDirectedRepository messageDirectedRepository;

    private final PersonRepository personRepository;


    public MessageDirected createMessageDirected(MessageDirected messageDirected) {
        return messageDirectedRepository.save(messageDirected);
    }

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public Message broadcastToTrusted(Message message) {

        Person personFrom = personRepository.findById(message.getFrom_person_id())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + message.getFrom_person_id()));

        Set<String> receiversIDs = new HashSet<>();

        personFrom.getConnections()
                .forEach((a, b) -> {
                    if (b >= message.getMin_trust_level()) receiversIDs.add(a);
                });

        message.setDestinations(
                checkReceiversTopics(receiversIDs, message.getTopics()));

        return createMessage(message);
    }


    public Map<String, Set<String>> broadcasting(Message message, Set<String> stack, Map<String, Set<String>> response) {

        stack.add(message.getFrom_person_id());

        Person personFrom = personRepository.findById(message.getFrom_person_id())
                .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + message.getFrom_person_id()));

        Set<String> ids = new HashSet<>();

        personFrom.getConnections()
                .forEach((a, b) -> {
                    if (stack.contains(a)) return;
                    if (b >= message.getMin_trust_level()) {
                        stack.add(a);
                        ids.add(a);
                    }
                });

        Set<String> filterIds = checkReceiversTopics(ids, message.getTopics());

        message.setDestinations(filterIds);

        for (String a : filterIds) {

            if (filterIds.size() == 0) return response;

            messageRepository.save(message);

            response.put(message.getFrom_person_id(), message.getDestinations());
            response.putAll(
                    broadcasting(
                            new Message(message.getText(), message.getTopics(), a, message.getMin_trust_level()),
                            stack,
                            response));
        }

        return response;
    }


    public Map<String, String> directedBroadcasting(MessageDirected messageDirected) {

        Graph graph = createAndFillGraph(messageDirected.getMin_trust_level());

        LinkedList<String> paths = findTheShortestPathInGraph(
                graph,
                messageDirected.getTopics(),
                messageDirected.getFrom_person_id());

        messageDirected.setPath(paths);
        createMessageDirected(messageDirected);

        return createDirectedMessageResponse(
                messageDirected.getFrom_person_id(),
                paths.toString());
    }

    private Map<String, String> createDirectedMessageResponse(String from, String path) {
        Map<String, String> directedMessageResponse = new HashMap<>();
        directedMessageResponse.put("from", from);
        directedMessageResponse.put("path", path);
        return directedMessageResponse;
    }

    private Graph createAndFillGraph(Integer trust_level) {
        Graph graph = new Graph();
        personRepository.findAll()
                .forEach( p -> graph.addEdge(p, defineConnections(p, trust_level)));
        return graph;
    }

    private LinkedList<String> findTheShortestPathInGraph(Graph graph, Set<String> topics, String from) {
        return breadthFirstTraversal(
                topics,
                graph,
                personRepository.findById(from)
                        .orElse(null)
        );
    }

    private List<Person> defineConnections(Person p, Integer m) {

        List<String> connections = new ArrayList<>();
        List<Person> persons = new ArrayList<>();

        if (p.getConnections().size() == 0) return Collections.emptyList();

        p.getConnections()
                .forEach((a, b) -> {
                    if (b >= m) {
                        connections.add(a);
                    }
                });

        for (String id : connections) {
            persons.add(personRepository.findById(id).orElse(null));
        }

        return persons;
    }


    public LinkedList<String> breadthFirstTraversal(Set<String> topics, Graph graph, Person root) {

        if (root == null) return null;

        Set<Person> visited = new LinkedHashSet<>();
        Queue<Person> queue = new LinkedList<>();
        LinkedList<String> currentPath = new LinkedList<>();
        List<LinkedList<String>> paths = new ArrayList<>();

        queue.add(root);
        visited.add(root);

        while (!queue.isEmpty()) {
            Person person = queue.poll();

            for (Person p : graph.getAdjVertices().get(person)) {

                if (paths.stream().anyMatch(path -> path.getLast().equals(person.getId()))) {

                    currentPath = new LinkedList<>(paths.stream()
                                    .filter(path -> path.getLast().equals(person.getId()))
                                    .findFirst()
                                    .orElse(new LinkedList<>()));
                }

                currentPath.add(p.getId());

                if (!paths.contains(currentPath)) {
                    paths.add(new LinkedList<>(currentPath));
                    currentPath.remove(p.getId());
                }

                if (!visited.contains(p)) {
                    visited.add(p);

                    if (checkReceiverTopics(p, topics)) {
                        currentPath.add(p.getId());
                        return currentPath;
                    }
                    queue.add(p);
                }
            }
        }
        return new LinkedList<>();
    }

    private boolean checkReceiverTopics(Person person, Set<String> topics) {
        return person.getTopics().containsAll(topics);
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
