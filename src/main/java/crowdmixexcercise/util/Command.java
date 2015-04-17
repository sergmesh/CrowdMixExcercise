package crowdmixexcercise.util;

import crowdmixexcercise.domain.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

/**
 * Represents user's command to the system
 */
public class Command {

    public static final Command unknownCommand = new Command(Action.unknown, null);

    private final Action action;
    private final Map<String, Object> parameters;

    public Command(Action action, Map<String, Object> parameters) {
        this.action = action;
        this.parameters = parameters;
    }

    public Action getAction() {
        return action;
    }

    public Map<String, Object> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    public enum Action {
        post, read, follow, unknown, wall
    }

    public static Command parseConsole(String line) {

        if (line.contains("->")) {

            String[] tokens = line.split("->");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("user", new User(tokens[0].trim()));
            parameters.put("data", tokens[1].trim());
            return new Command(Action.post, parameters);

        } else if (line.contains("follows")) {

            String[] tokens = line.split("follows");
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("user", new User(tokens[0].trim()));
            parameters.put("following", new User(tokens[1].trim()));
            return new Command(Action.follow, parameters);

        } else if (line.contains("wall")) {

            String userName = line.substring(0, line.indexOf("wall"));
            User user = new User(userName.trim());
            return new Command(Action.wall, singletonMap("user", user));

        } else if (!line.trim().contains(" ")) {

            return new Command(Action.read, singletonMap("user", new User(line.trim())));

        }

        return Command.unknownCommand;
    }

}
