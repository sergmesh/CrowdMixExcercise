package crowdmixexcercise.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Generates unique IDs for messages
 */
public final class IdGenerator {

    private static final AtomicLong store = new AtomicLong();

    private IdGenerator() {
    }

    /**
     * Returns next id
     * @return next id
     */
    public static long nextId() {
        return store.incrementAndGet();
    }
}
