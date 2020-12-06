package command;

import Model.ConsoleHelper;
import Model.Game;

import java.io.IOException;

public class PlayCommand implements Command {
    @Override
    public void execute() {
        Game.reset();
        ConsoleHelper.writeMessage("Enter, how many lottery tickets would you like to get?");
        int numberOfTickets = ConsoleHelper.readInt();
        long roublesSpent = 5L * (long) numberOfTickets;
        ConsoleHelper.writeMessage
                (String.format("You bought %d tickets and spent %d BYN", numberOfTickets, roublesSpent));
        ConsoleHelper.writeMessage("***************");

        Game.reinitializeServer(numberOfTickets);
        Game.play(numberOfTickets);
        Game.printResult();
    }
}
