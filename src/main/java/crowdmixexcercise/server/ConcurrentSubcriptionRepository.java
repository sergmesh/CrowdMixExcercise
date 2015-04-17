package crowdmixexcercise.server;

import crowdmixexcercise.SubcriptionRepository;
import crowdmixexcercise.domain.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Serves as a main subscription point of the system.
 */
public final class ConcurrentSubcriptionRepository implements SubcriptionRepository {

    // who the user (key) is subscribed to
    private final Map<User, CopyOnWriteArraySet<User>> subscriptions = new ConcurrentHashMap<>();

    @Override
    public void subscribe(User user, User to) {
        CopyOnWriteArraySet<User> subscriptionsForUser = subscriptions.get(user);
        if (subscriptionsForUser == null) {
            CopyOnWriteArraySet<User> newSubscriptionsForUser = new CopyOnWriteArraySet<>();
            subscriptionsForUser = subscriptions.putIfAbsent(user, newSubscriptionsForUser);
            if (subscriptionsForUser == null) {
                subscriptionsForUser = newSubscriptionsForUser;
            }
        }
        subscriptionsForUser.add(to);
    }

    @Override
    public Set<User> getSubscriptionsFor(User user) {
        CopyOnWriteArraySet<User> subscriptionsForUser = subscriptions.get(user);
        if (subscriptionsForUser != null) {
            return Collections.unmodifiableSet(subscriptionsForUser);
        }
        return Collections.emptySet();
    }

}
