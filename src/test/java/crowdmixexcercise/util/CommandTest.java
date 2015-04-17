package crowdmixexcercise.util;

import crowdmixexcercise.domain.User;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 */
public class CommandTest {

    @Test
    public void parseConsole() {
        Command post = Command.parseConsole("Alice -> I love the weather today");
        assertThat(post.getAction(), is(Command.Action.post));
        assertThat(post.getParameters().get("user"), is(new User("Alice")));
        assertThat(post.getParameters().get("data"), is("I love the weather today"));

        Command read = Command.parseConsole("Alice");
        assertThat(read.getAction(), is(Command.Action.read));
        assertThat(read.getParameters().get("user"), is(new User("Alice")));

        Command follow = Command.parseConsole("Charlie follows Alice");
        assertThat(follow.getAction(), is(Command.Action.follow));
        assertThat(follow.getParameters().get("user"), is(new User("Charlie")));
        assertThat(follow.getParameters().get("following"), is(new User("Alice")));

        Command wall = Command.parseConsole("Charlie wall");
        assertThat(wall.getAction(), is(Command.Action.wall));
        assertThat(wall.getParameters().get("user"), equalTo(new User("Charlie")));

    }


}