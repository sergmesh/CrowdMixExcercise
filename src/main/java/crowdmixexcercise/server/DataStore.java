package crowdmixexcercise.server;

import crowdmixexcercise.Wall;
import crowdmixexcercise.domain.Message;
import crowdmixexcercise.domain.User;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores all messages for all users
 */
public class DataStore {

    private final Map<User, Wall> store = new ConcurrentHashMap<>();

    public void storeMessage(User user, Message message) {
        Wall wall = store.get(user);
        if (wall == null) {
            final Wall newWall = new LastMessageAtTheTopWall(user);
            wall = store.putIfAbsent(user, newWall);
            if (wall == null) {
                wall = newWall;
            }
        }
        wall.storeMessage(message);
    }

    public Wall getWallFor(User user) {
        Wall wall = store.get(user);
        if (wall == null) {
            return new LastMessageAtTheTopWall(user);
        }
        return wall;
    }



}
