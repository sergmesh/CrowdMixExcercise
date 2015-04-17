package crowdmixexcercise.server;

import crowdmixexcercise.Wall;
import crowdmixexcercise.domain.Message;
import crowdmixexcercise.domain.User;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 */
public class LastMessageAtTheTopWallTest {

    @Test
    public void testAllMessagesGetPersistedCorrectly() {
        Wall wall = new LastMessageAtTheTopWall(new User("test user"));

        List<Message> messages = new ArrayList<>();
        messages.add(new Message("message 1"));
        messages.add(new Message("message 2"));
        messages.add(new Message("message 3"));

        wall.storeAllMessages(messages);

        assertThat(wall.getAllMessages().size(), is(3));

        assertThat(wall.getOwner().getName(), is("test user"));
    }

    @Test
    public void testMessagesGetSortedCorrectly() throws InterruptedException {
        Wall wall = new LastMessageAtTheTopWall(new User("test user"));

        wall.storeMessage(new Message("message 1"));
        wall.storeMessage(new Message("message 2"));
        wall.storeMessage(new Message("message 3"));

        assertThat(wall.getAllMessages().first().getText(), is("message 3"));
        assertThat(wall.getAllMessages().last().getText(), is("message 1"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testUnableToModifySomeonesWall() throws InterruptedException {
        Wall wall = new LastMessageAtTheTopWall(new User("test user"));

        wall.storeMessage(new Message("message 1"));

        SortedSet<Message> allMessages = wall.getAllMessages();
        allMessages.add(new Message("test"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWallMustBelongToSomeone() {
        new LastMessageAtTheTopWall(null);
    }

    @Test
    public void testUnreadMessages() throws InterruptedException {
        Wall wall = new LastMessageAtTheTopWall(new User("alice"));

        wall.storeMessage(new Message("message 1"));
        wall.storeMessage(new Message("message 2"));

        SortedSet<Message> unreadMessages = wall.getAllUnreadMessages();
        assertThat(unreadMessages.size(), is(2));

        TimeUnit.SECONDS.sleep(2);

        wall.storeMessage(new Message("message 3"));

        unreadMessages = wall.getAllUnreadMessages();
        assertThat(unreadMessages.size(), is(1));

    }


}