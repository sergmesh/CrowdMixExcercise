package crowdmixexcercise.server;

import crowdmixexcercise.SubcriptionRepository;
import crowdmixexcercise.domain.User;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 */
public class ConcurrentSubcriptionRepositoryTest {

    @Test
    public void testSubscription() {
        User alice = new User("alice");
        User bob = new User("bob");
        User peter = new User("peter");
        User john = new User("john");

        SubcriptionRepository repository = new ConcurrentSubcriptionRepository();

        repository.subscribe(bob, alice);
        repository.subscribe(peter, alice);

        repository.subscribe(alice, john);
        repository.subscribe(bob, john);

        assertThat(repository.getSubscriptionsFor(bob).size(), is(2)); // alice and john
        assertThat(repository.getSubscriptionsFor(peter).size(), is(1)); // alice
        assertThat(repository.getSubscriptionsFor(alice).size(), is(1)); // john
        assertThat(repository.getSubscriptionsFor(john).size(), is(0)); // no one
    }

}