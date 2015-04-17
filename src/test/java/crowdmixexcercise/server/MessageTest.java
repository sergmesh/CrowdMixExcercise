package crowdmixexcercise.server;

import crowdmixexcercise.domain.Message;
import org.junit.Test;

import java.util.TreeSet;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 */
public class MessageTest {

    @Test
    public void testMessagesSorting() throws InterruptedException {
        Message oldMessage = new Message("old message");
        Message newMessage = new Message("new message");

        TreeSet<Message> store = new TreeSet<>(new Message.ReverseCreationOrderComparator());
        store.add(oldMessage);
        store.add(newMessage);

        assertThat(store.size(), is(2));
        assertThat(store.first().getText(), is("new message"));
    }

}