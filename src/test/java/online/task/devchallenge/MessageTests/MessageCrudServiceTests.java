package online.task.devchallenge.MessageTests;

import online.task.devchallenge.domain.Message;
import online.task.devchallenge.domain.MessageDirected;
import online.task.devchallenge.repository.MessageDirectedRepository;
import online.task.devchallenge.repository.MessageRepository;
import online.task.devchallenge.service.MessageServiceBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MessageCrudServiceTests {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageDirectedRepository messageDirectedRepository;

    @InjectMocks
    private MessageServiceBean messageServiceBean;


    @Test
    public void whenSaveMessage_shouldReturnMessage() {
        Set<String> topics = new HashSet<>();
        topics.add("Books");
        topics.add("Food");
        Set<String> destinations = new HashSet<>();
        destinations.add("Diana");
        destinations.add("Fred");
        Message message = new Message(
                1,
                "Message text",
                topics,
                "Den",
                10,
                destinations);

        when(messageRepository
                .save(ArgumentMatchers.any(Message.class)))
                .thenReturn(message);

        Message message1 = messageServiceBean.createMessage(message);

        assertThat(message1.getId()).isSameAs(message.getId());
        assertThat(message.getTopics()).hasSameElementsAs(message1.getTopics());
        assertThat(message.getDestinations()).hasSameElementsAs(message1.getDestinations());

        verify(messageRepository).save(message);
    }

    @Test
    public void whenSaveMessageDirected_shouldReturnMessageDirected() {
        Set<String> topics = new HashSet<>();
        topics.add("Books");
        topics.add("Food");
        List<String> path = new LinkedList<>();
        path.add("Diana");
        path.add("Fred");
        MessageDirected messageDirected = new MessageDirected();
        messageDirected.setTopics(topics);
        messageDirected.setPath(path);
        messageDirected.setId(1);

        when(messageDirectedRepository
                .save(ArgumentMatchers.any(MessageDirected.class)))
                .thenReturn(messageDirected);

        MessageDirected messageDirected1 = messageServiceBean.createMessageDirected(messageDirected);

        assertThat(messageDirected1.getId()).isSameAs(messageDirected.getId());
        assertThat(messageDirected.getTopics()).hasSameElementsAs(messageDirected1.getTopics());
        assertThat(messageDirected.getPath()).hasSameElementsAs(messageDirected1.getPath());

        verify(messageDirectedRepository).save(messageDirected);
    }

}
