package crowdmixexcercise.server;

import crowdmixexcercise.domain.Message;
import crowdmixexcercise.domain.User;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 */
public class DataStoreTest {

    @Test
    public void testMultiThreadedInitialization() throws InterruptedException {
        final DataStore dataStore = new DataStore();

        final User alice = new User("alice");

        int numberOfThreads = 100;
        final CountDownLatch startLatch = new CountDownLatch(numberOfThreads);
        final CountDownLatch stopLatch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            Thread thread = new Thread(
                    () -> {
                        try {
                            startLatch.await();
                            dataStore.storeMessage(alice, new Message(Thread.currentThread().getName()));
                            stopLatch.countDown();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    },
                    String.valueOf(i));

            thread.start();
            startLatch.countDown();
        }

        stopLatch.await();

        assertThat(dataStore.getWallFor(alice).getAllMessages().size(), is(numberOfThreads));

    }

}