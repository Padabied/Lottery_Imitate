package command;

import Model.ConsoleHelper;

public class ExitCommand implements Command {
    @Override
    public void execute() {
        ConsoleHelper.writeMessage("See you later !");
    }
}
