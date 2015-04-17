package crowdmixexcercise.domain;


import crowdmixexcercise.util.IdGenerator;

/**
 * Represents abstract message anyone can write in the system.
 * Once typed in does not allow changing existing message.
 */
public class Message {

    // id which uniquely identifies the message
    private final long id = IdGenerator.nextId();
    // when the user typed in the information into the system
    // is used to define which messages are new for the subscribers
    private final long timeStamp = System.nanoTime();
    // text user typed in
    private final String text;

    public Message(String text) {
        this.text = text;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public String getText() {
        return text;
    }

    public long getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return id == message.id
                && timeStamp == message.timeStamp
                && text.equals(message.text);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (timeStamp ^ (timeStamp >>> 32));
        result = 31 * result + text.hashCode();
        return result;
    }

    /**
     * Orders messages based on the time when they are written.
     * This sorting will be used to represent the wall of written messages with the most recent message at the top.
     */
    public final static class ReverseCreationOrderComparator implements java.util.Comparator<Message> {

        @Override
        public int compare(Message m1, Message m2) {
            return (int) Math.signum((double) (m2.getId() - m1.getId()));
        }

    }

}
