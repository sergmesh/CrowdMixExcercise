package crowdmixexcercise.server;

import crowdmixexcercise.Wall;
import crowdmixexcercise.domain.Message;
import crowdmixexcercise.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.util.SortedSet;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 */
public class CommunicationServerTest {

    CommunicationServer server;

    @Before
    public void setUp() throws Exception {
        server = new CommunicationServer();
    }

    @Test
    public void testMessagesPosting() {
        User alice = new User("alice");
        User bob = new User("bob");

        server.post(alice, new Message("I love the weather today"));
        server.post(bob, new Message("Damn! We lost!"));
        server.post(bob, new Message("Good game though."));

        Wall aliceWall = server.wall(alice);
        assertThat(aliceWall.getAllMessages().size(), is(1));
        assertThat(aliceWall.getOwner(), is(alice));

        Wall bobWall = server.wall(bob);
        assertThat(bobWall.getAllMessages().size(), is(2));
        assertThat(bobWall.getOwner(), is(bob));

    }

    @Test
    public void testReading() throws InterruptedException {
        User alice = new User("alice");

        server.post(alice, new Message("message 1"));
        server.post(alice, new Message("message 2"));

        SortedSet<Message> messages = server.read(alice);
        assertThat(messages.size(), is(2));

        TimeUnit.SECONDS.sleep(1);

        server.post(alice, new Message("message 3"));

        messages = server.read(alice);
        assertThat(messages.size(), is(1));
    }

    @Test
    public void testReadingExampleScenario() throws InterruptedException {
        User alice = new User("alice");
        User bob = new User("bob");

        server.post(alice, new Message("I love the weather today"));

        server.post(bob, new Message("Damn! We lost!"));
        TimeUnit.SECONDS.sleep(1);
        server.post(bob, new Message("Good game though."));


        SortedSet<Message> aliceMessages = server.read(alice);
        assertThat(aliceMessages.size(), is(1));

        SortedSet<Message> bobMessages = server.read(bob);
        assertThat(bobMessages.size(), is(2));
        assertThat(bobMessages.first().getText(), is("Good game though."));
        assertThat(bobMessages.last().getText(), is("Damn! We lost!"));
    }

    @Test
    public void testFollowing() throws InterruptedException {
        User alice = new User("alice");
        User bob = new User("bob");
        User charlie = new User("charlie");

        server.post(alice, new Message("I love the weather today"));
        TimeUnit.SECONDS.sleep(1);
        server.post(bob, new Message("Damn! We lost!"));
        TimeUnit.SECONDS.sleep(1);
        server.post(bob, new Message("Good game though."));
        TimeUnit.SECONDS.sleep(1);
        server.post(charlie, new Message("I'm in New York today! Anyone wants to have a coffee?"));

        Wall charlieWall = server.wall(charlie);
        assertThat(charlieWall.getAllMessages().size(), is(1));

        server.follow(charlie, alice);
        charlieWall = server.wall(charlie);
        assertThat(charlieWall.getAllMessages().size(), is(2));

        server.follow(charlie, bob);
        charlieWall = server.wall(charlie);
        assertThat(charlieWall.getAllMessages().size(), is(4));
        assertThat(charlieWall.getAllMessages().first().getText(), is("charlie - I'm in New York today! Anyone wants to have a coffee?"));
        assertThat(charlieWall.getAllMessages().last().getText(), is("alice - I love the weather today"));

    }


}