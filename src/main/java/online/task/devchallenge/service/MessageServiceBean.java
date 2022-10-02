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

        return messageRepository.save(message);
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



    public Map<String, String> directedBroadcasting22(MessageDirected messageDirected) {

        String from = messageDirected.getFrom_person_id();

        Map<String, String> directedMessageResponse = new HashMap<>();
        directedMessageResponse.put("from", from);

        List<Person> allPersons = personRepository.findAll();

        Graph graph = new Graph();

        allPersons.forEach( p -> graph.addEdge(p, defineConnections(p, messageDirected.getMin_trust_level())));

        Set<Person> set = breadthFirstTraversal(messageDirected, graph, personRepository.findById(messageDirected.getFrom_person_id()).orElse(null));

        //System.err.println(set);

        return directedMessageResponse;
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


    public Set<Person> breadthFirstTraversal(MessageDirected messageDirected, Graph graph, Person root) {
        Set<Person> visited = new LinkedHashSet<>();
        Queue<Person> queue = new LinkedList<>();

        LinkedList<String> currentPath = new LinkedList<>();
        List<LinkedList<String>> paths = new ArrayList<>();

        queue.add(root);
        visited.add(root);

        while (!queue.isEmpty()) {
            Person person = queue.poll();

            //if (!person.equals(root))
                //currentPath.add(person.getId());

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

                    if (checkReceiverTopics(p, messageDirected.getTopics())) {
                        //return paths.get(paths.size()-1);
                        return visited;
                    }
                    queue.add(p);
                }
            }
        }
        return visited;
    }

    //****************************************************
//    public Set<Person> breadthFirstTraversal(MessageDirected messageDirected, Graph graph, Person root) {
//        Set<Person> visited = new LinkedHashSet<>();
//        Queue<Person> queue = new LinkedList<>();
//
//        List<String> currentPath = new ArrayList<>();
//        List<List<String>> paths = new ArrayList<>();
//
//        queue.add(root);
//        visited.add(root);
//
//        while (!queue.isEmpty()) {
//            Person person = queue.poll();
//
//            for (Person p : graph.getAdjVertices().get(person)) {
//
//                if (!visited.contains(p)) {
//                    visited.add(p);
//
//                    if (checkReceiverTopics(p, messageDirected.getTopics())) {
//                        return visited;
//                    }
//                    queue.add(p);
//                }
//            }
//        }
//        return visited;
//    }
//*************************************************




    private boolean checkReceiverTopics(Person person, Set<String> topics) {
        return person.getTopics().containsAll(topics);
    }














    public Map<String, String> directedBroadcasting(MessageDirected messageDirected) {

        String from = messageDirected.getFrom_person_id();

        Map<String, String> directedMessageResponse = new HashMap<>();
        directedMessageResponse.put("from", from);

        List<List<String>> paths = findPathOfDirectedMessage(messageDirected, new HashSet<>(), new ArrayList<>());

        System.err.println(paths);

        List<String> path = paths.stream().sorted(Comparator.comparingInt(List::size)).collect(Collectors.toList()).get(0);

        directedMessageResponse.put("path", path.toString());

        return directedMessageResponse;
    }

    private List<List<String>> findPathOfDirectedMessage(MessageDirected messageDirected, HashSet<Object> visited, ArrayList<String> response) {

        String top = messageDirected.getFrom_person_id();

        Map<Integer, List<String>> path = new HashMap<>();;

        Queue<String> queue = new LinkedList<>();
        // Отметить начальную точку
        visited.add(top);
        // Начать запись
        queue.add(top);

        Integer i = 0;

        while(!queue.isEmpty()){

            path.put(i, messageDirected.getPath());

            String topper = queue.poll();  // Возвращаем заголовок очереди или null, если очередь пуста

            Person personFrom = personRepository.findById(topper)
                    .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + messageDirected.getFrom_person_id()));

            List<String> fromIds = new ArrayList<>();

            personFrom.getConnections()
                    .forEach((a, b) -> {
                        if (b >= messageDirected.getMin_trust_level()) {
                            fromIds.add(a);
                        }
                    });

            for (String s : fromIds) {
                if (!visited.contains(s)) {
                    visited.add(s);
                    response.add(topper);
                    //path.put(s, topper);

                    if (checkCurrentReceiver(s, messageDirected.getTopics())) {
                        response.add(s);
                        List<List<String>> list = new ArrayList<>();
                        list.add(response);
                        return list;
                    }

                    queue.add(s);   // Добавленные позже будут обработаны после обработки всех предыдущих вершин с расстоянием currDist
                }
            }
        }

        List<List<String>> list = new ArrayList<>();
        list.add(response);
        return list;
    }

//______________________________________________________________//

    public Map<String, String> directedBroadcasting1(MessageDirected messageDirected) {

        String from = messageDirected.getFrom_person_id();

        Map<String, String> directedMessageResponse = new HashMap<>();
        directedMessageResponse.put("from", from);

        List<String> nearestPerson = findNearestPersonToSendMessage(messageDirected);

        System.err.println(nearestPerson);

        return directedMessageResponse;
    }

    private List<String> findNearestPersonToSendMessage(MessageDirected messageDirected) {

        Queue<String> queue = new LinkedList<>();
        Set<String> stack = new HashSet<>();
        Map<Integer, List<String>> path = new HashMap<>();
        List<String> probablyPath = messageDirected.getPath();

        var s = messageDirected.getFrom_person_id();

        // добавляем s в очередь
        queue.add(s);
        // помечаем s как посещенную вершину во избежание повторного добавления в очередь
        stack.add(s);
        int i = 1;
        while (queue.size() > 0) {
            // удаляем первый (верхний) элемент из очереди
            var t = queue.poll();

            probablyPath.add(t);

            Person personFrom = personRepository.findById(t)
                    .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + s));

            //connections who are trusted
            Set<String> fromIds = new HashSet<>();

            personFrom.getConnections()
                    .forEach((a, b) -> {
                        if (b >= messageDirected.getMin_trust_level()) {
                            fromIds.add(a);
                        }
                    });

            for (String trusted : fromIds) {
                // если сосед не посещался
                if (!stack.contains(trusted)) {

                    if (path.containsValue(probablyPath)) {
                        Integer key = path.entrySet()
                                .stream()
                                .filter(entry -> probablyPath.equals(entry.getValue()))
                                .map(Map.Entry::getKey)
                                .collect(Collectors.toList())
                                .get(0);
                        path.get(key).add(trusted);
                    } else {
                        path.put(i, new ArrayList<>(probablyPath));
                        path.get(i).add(trusted);
                        i++;
                    }
                    // добавляем его в очередь
                    queue.add(trusted);
                    // помечаем вершину как посещенную
                    stack.add(trusted);
                    // если сосед является пунктом назначения, мы победили
                    if (checkCurrentReceiver(trusted, messageDirected.getTopics())) {
                        return path.get(i-1);
                    }
                }
            }

        }

        return null;
    }

//______________________________________________________________//


//    private List<List<String>> findPathOfDirectedMessage(MessageDirected messageDirected, HashSet<String> stack, ArrayList<List<String>> responses) {
//
//
//        Stack<String> globalStack = new Stack<>();
//
//        Set<List<String>> paths = new HashSet<>();
//
//
//        Person personFrom = personRepository.findById(messageDirected.getFrom_person_id())
//                .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + messageDirected.getFrom_person_id()));
//
//        Set<String> fromIds = new HashSet<>();
//
//        personFrom.getConnections()
//                .forEach( (a, b) -> {
//                    //if (stack.contains(a)) return;
//                    if (b >= messageDirected.getMin_trust_level()){
//                        //stack.add(a);
//                        fromIds.add(a);
//                    }
//                });
//        fromIds.forEach(globalStack::push);
//
//        boolean isRowEmpty = false; //loop marker creation
//
//        while (!isRowEmpty) {
//
//            //Stack of the parent objects of the current iterating element
//            Stack<String> localStack = new Stack<>();
//            isRowEmpty = true; //loop marker change
//
//            while (!globalStack.isEmpty()) { //checks globalStack for elements and starts new loop
//
//                //get element from Stack and delete it.
//                String temp = globalStack.pop();
//                if (!checkCurrentReceiver(temp, messageDirected.getTopics())) {
//
//                    if(paths.contains())
//                    messageDirected.getPath().add(temp);
//
//                    Person person = personRepository.findById(temp)
//                            .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + temp));
//
//                    Set<String> ids = new HashSet<>();
//
//                    person.getConnections()
//                            .forEach( (a, b) -> {
//                                //if (stack.contains(a)) return;
//                                if (b >= messageDirected.getMin_trust_level()){
//                                    //stack.add(a);
//                                    ids.add(a);
//                                }
//                            });
//                    ids.forEach(localStack::push);
//
//                    //if (temp.left != null || temp.right != null)
//                        isRowEmpty = false; //change marker again
//
//                } else {
//                    messageDirected.getPath().add(temp);
//                    responses.add(new ArrayList<>(messageDirected.getPath()));
//                    break;
//                }
//            }
//            while (!localStack.isEmpty())
//                globalStack.push(localStack.pop()); //transfer all elements from localStack into globalStack
//        }
//
//        return responses;
//
//    }


//
//    private List<List<String>> findPathOfDirectedMessage(MessageDirected messageDirected, HashSet<String> stack, List<List<String>> responses) {
//
//        stack.add(messageDirected.getFrom_person_id());
//
//        Person personFrom = personRepository.findById(messageDirected.getFrom_person_id())
//                .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + messageDirected.getFrom_person_id()));
//
//        List<String> ids = new ArrayList<>();
//
//        personFrom.getConnections()
//                .forEach( (a, b) -> {
//                    if (stack.contains(a)) return;
//                    if (b >= messageDirected.getMin_trust_level()){
//                        stack.add(a);
//                        ids.add(a);
//                    }
//                });
//
//        for (String id : ids) {
//
//            System.err.println("Current id: " + id);
//            System.err.println("Current path: " + messageDirected.getPath());
//            System.err.println("Current responses: " + responses);
//
//            if (checkCurrentReceiver(id, messageDirected.getTopics())) {
//
//                messageDirected.getPath().add(id);
//                System.err.println("Added last: " + messageDirected.getPath().toString());
//
//                responses.add(new ArrayList<>(messageDirected.getPath()));
//
//                System.err.println("Responses now: " + responses);
//
//                messageDirected.getPath().clear();
//                continue;
//            }
//
//            messageDirected.getPath().add(id);
//            System.err.println("Added in path: " + messageDirected.getPath().toString());
//            findPathOfDirectedMessage(
//                                new MessageDirected(
//                                        messageDirected.getText(),
//                                        messageDirected.getTopics(),
//                                        id,
//                                        messageDirected.getMin_trust_level(),
//                                        messageDirected.getPath()),
//                                stack,
//                                responses);
//
//            if (ids.indexOf(id) == ids.size()-1)
//                messageDirected.getPath().clear();
//
//        }
//
//        return responses;
//    }


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



//    public Map<String, Set<String>> broadcasting (Message message, Set<String> stack, Map<String, Set<String>> response) {
//
//        stack.add(message.getFrom_person_id());
//
//        Person personFrom = personRepository.findById(message.getFrom_person_id())
//                .orElseThrow(() -> new EntityNotFoundException("Person not found with id = " + message.getFrom_person_id()));
//
//        Set<String> ids = new HashSet<>();
//
//        personFrom.getConnections().forEach( (a, b) -> {
//
//            System.err.println("-----------------------");
//
//            System.err.println(stack.contains(a));
//            System.err.println("stack: " + stack);
//            System.err.println("object: " + a);
//
//            if (stack.contains(a)) return;
//
//            if (b >= message.getMin_trust_level()){
//                stack.add(a);
//                ids.add(a);
//
//                System.err.println("object: " + a);
//                System.err.println("ids: " + ids);
//
//            }
//        });
//
//        Set<String> filterIds = checkReceiversTopics(ids, message.getTopics());
//
//        message.setDestinations(filterIds);
//
//        for (String a : filterIds) {
//
//            if (filterIds.size() == 0) return response;
//
//            messageRepository.save(message);
//            response.put(message.getFrom_person_id(), message.getDestinations());
//
//            response.putAll(
//                    broadcasting(
//                            new Message(message.getText(), message.getTopics(), a, message.getMin_trust_level()),
//                            stack,
//                            response));
//        }
//
//        System.err.println(response);
//
//        return response;
//    }

}
