package crowdmixexcercise;

import crowdmixexcercise.domain.Message;
import crowdmixexcercise.domain.User;

import java.util.Collection;
import java.util.SortedSet;

/**
 * Wall represents a set of messages grouped together for a user
 */
public interface Wall {

    /**
     * Stores message in the system.
     * @param message new message to store
     */
    void storeMessage(Message message);

    /**
     * Convenience method to store all messages
     * @param messages messages to store
     */
    void storeAllMessages(Collection<Message> messages);

    /**
     * Provides a list of all messages user typed in so far.
     * @return all messages for the given user
     */
    SortedSet<Message> getAllMessages();

    /**
     * Read all messages since last read
     * @return messages since the last read
     */
    SortedSet<Message> getAllUnreadMessages();

    /**
     * Provides details on who this wall belongs to.
     * @return the owner of the wall
     */
    User getOwner();

}
