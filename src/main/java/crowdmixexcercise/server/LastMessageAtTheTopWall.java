package crowdmixexcercise.server;

import crowdmixexcercise.Wall;
import crowdmixexcercise.domain.Message;
import crowdmixexcercise.domain.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This class represents history of all messages user posted in in the reverse time order
 */
public class LastMessageAtTheTopWall implements Wall {

    private final User user;
    private final SortedSet<Message> messages;
    private AtomicLong lastReadAt;

    public LastMessageAtTheTopWall(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Wall must belong to someone. User can't be null");
        }
        this.user = user;
        this.messages = Collections.synchronizedSortedSet(new TreeSet<>(new Message.ReverseCreationOrderComparator()));
        this.lastReadAt = new AtomicLong(0);
    }

    @Override
    public void storeMessage(Message message) {
        messages.add(message);
    }

    @Override
    public void storeAllMessages(Collection<Message> collection) {
        messages.addAll(collection);
    }

    @Override
    public SortedSet<Message> getAllMessages() {
        return Collections.unmodifiableSortedSet(messages);
    }

    @Override
    public SortedSet<Message> getAllUnreadMessages() {
        SortedSet<Message> result = new TreeSet<>(new Message.ReverseCreationOrderComparator());

        boolean messagesTooOld = false;

        synchronized (messages) {
            Iterator<Message> iterator = messages.iterator();
            while (iterator.hasNext() && !messagesTooOld) {
                Message message = iterator.next();
                if (message.getTimeStamp() >= lastReadAt.longValue()) {
                    result.add(message);
                } else {
                    messagesTooOld = true;
                }
            }
        }

        lastReadAt.set(System.nanoTime());
        return Collections.unmodifiableSortedSet(result);
    }

    @Override
    public User getOwner() {
        return user;
    }

}
