package crowdmixexcercise.client;

import crowdmixexcercise.Wall;
import crowdmixexcercise.domain.Message;
import crowdmixexcercise.domain.User;
import crowdmixexcercise.server.CommunicationServer;
import crowdmixexcercise.util.Command;

import java.util.Scanner;
import java.util.SortedSet;

import static crowdmixexcercise.util.Command.Action.*;

/**
 * Console application that starts demo application
 */
public class ConsoleApp {

    public static void main(String[] args) {

        CommunicationServer server = new CommunicationServer();

        try (Scanner console = new Scanner(System.in)) {

            welcome();
            help();

            String line;
            while (prompt() && console.hasNext() && !"exit".equals(line = console.nextLine())) {

                Command command = Command.parseConsole(line);

                if (post == command.getAction()) {

                    server.post(
                            (User) command.getParameters().get("user"),
                            new Message((String) command.getParameters().get("data"))
                    );

                } else if (read == command.getAction()) {

                    SortedSet<Message> messages = server.read((User) command.getParameters().get("user"));
                    for (Message message : messages) {
                        System.out.println(message.getText());
                    }

                } else if (follow == command.getAction()) {

                    server.follow(
                            (User) command.getParameters().get("user"),
                            (User) command.getParameters().get("following")
                    );

                } else if (wall == command.getAction()) {

                    Wall wall = server.wall((User) command.getParameters().get("user"));
                    for (Message message : wall.getAllMessages()) {
                        System.out.println(message.getText());
                    }

                } else {

                    help();

                }

            }
        }
        System.out.println("\nBye");
    }


    private static void help() {
        System.out.println("# usage : ");
        System.out.println("# \tuse \"<user name> -> <message>\" to post a message");
        System.out.println("# \tuse \"<user name>\" to read user's messages that haven't been read yet");
        System.out.println("# \tuse \"<user name>\" follows <user name>\" to subscribe to the user's updates");
        System.out.println("# \tuse \"<user name> wall\" to see user's wall");
        System.out.println("# \tuse \"exit\" to exit the application");
        System.out.println("#");
    }

    private static void welcome() {
        System.out.println("#");
        System.out.println("# Welcome to Semi Twitter application");
        System.out.println("#");
    }

    private static boolean prompt() {
        System.out.print("semi-twitter > ");
        return true;
    }

}
