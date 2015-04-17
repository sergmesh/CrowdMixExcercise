package crowdmixexcercise;

import crowdmixexcercise.domain.User;

import java.util.Set;

/**
 * Represents information about who subscribed to which messages.
 * Repository also serves as data store for a web of subscriptions among users.
 */
public interface SubcriptionRepository {

    /**
     * Subscribe to a user postings
     * @param user the user who wants to subscribe
     * @param to user name to subscribe to
     */
    void subscribe(User user, User to);

    /**
     * Who the user is subscribed to
     * @param user the user
     * @return set of users
     */
    Set<User> getSubscriptionsFor(User user);

}
