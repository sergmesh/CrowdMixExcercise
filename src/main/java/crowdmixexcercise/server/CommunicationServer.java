package crowdmixexcercise.server;

import crowdmixexcercise.MessagingServer;
import crowdmixexcercise.SubcriptionRepository;
import crowdmixexcercise.Wall;
import crowdmixexcercise.domain.Message;
import crowdmixexcercise.domain.User;

import java.util.Set;
import java.util.SortedSet;

/**
 * Implements server interface to coimmunicate with the user of the application.
 */
public class CommunicationServer implements MessagingServer {

    private final SubcriptionRepository subscriptionRepository = new ConcurrentSubcriptionRepository();
    private final DataStore dataStore = new DataStore();

    @Override
    public void post(User user, Message message) {
        dataStore.storeMessage(user, message);
    }

    @Override
    public SortedSet<Message> read(User user) {
        return dataStore.getWallFor(user).getAllUnreadMessages();
    }

    @Override
    public void follow(User user, User follows) {
        subscriptionRepository.subscribe(user, follows);
    }

    @Override
    public Wall wall(User user) {
        Set<User> subscriptionsForUser = subscriptionRepository.getSubscriptionsFor(user);
        Wall result = new LastMessageAtTheTopWall(user);
        for (User followedByTheUser : subscriptionsForUser) {
            populateWallMessages(result, followedByTheUser);
        }
        populateWallMessages(result, user);
        return result;
    }

    private void populateWallMessages(Wall result, User followedByTheUser) {
        SortedSet<Message> allMessages = dataStore.getWallFor(followedByTheUser).getAllMessages();
        for (Message message : allMessages) {
            result.storeMessage(new Message(followedByTheUser.getName() + " - " + message.getText()));
        }
    }

}
