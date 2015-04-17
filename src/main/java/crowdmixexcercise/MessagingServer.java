package crowdmixexcercise;

import crowdmixexcercise.domain.Message;
import crowdmixexcercise.domain.User;

import java.util.SortedSet;

/**
 * Command interface for communicating with background components.
 */
public interface MessagingServer {

    /**
     * Post a message into the system
     * @param user user who posts messages
     * @param message message
     */
    void post(User user, Message message);

    /**
     * Reading messages from the system of the given user
     * @param user user we are interested in
     * @return messages since last read
     */
    SortedSet<Message> read(User user);

    /**
     * Record the information who follows who
     * @param user user
     * @param follows follows another suer
     */
    void follow(User user, User follows);

    /**
     * Wall object which wraps messages on the user;s wall
     * @param user the owner of the wall
     * @return wall object with corresponding messages
     */
    Wall wall(User user);

}
