package command;


import Model.Game;

public class WinMessagesCommand implements Command {

    @Override
    public void execute() {
        Game.printWinBilletsMessages();
    }
}
