package command;

import Model.Model;

public class InfoCommand implements Command {
    @Override
    public void execute() {
        Model.printInfo();
    }
}
