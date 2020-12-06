package command;
import Model.Operation;

import java.util.HashMap;
import java.util.Map;

public class CommandExecutor {

    public static Map<Operation, Command> commands = new HashMap<>();

    static {
        commands.put(Operation.PLAY, new PlayCommand());
        commands.put(Operation.INFO, new InfoCommand());
        commands.put(Operation.GET_WIN_MESSAGES, new WinMessagesCommand());
        commands.put(Operation.EXIT, new ExitCommand());
    }

    public static void execute(Operation operation)  {
        commands.get(operation).execute();
    }
}
