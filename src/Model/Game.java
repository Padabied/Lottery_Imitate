package Model;

import command.CommandExecutor;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private static int ticketCount;
    private static List<Ticket> winners = new ArrayList<>();
    private static int moneySpentOnTickets;
    private static double prize = 0;
    private static int newTicketsWon = 0;

    private Game() {
    }

    // calls when user enter his lottery tickets number to reinitialize average values
    public static void reinitializeServer(int count) {
        ticketCount = count;
        moneySpentOnTickets = 5 * ticketCount;        // 5 BYN - price of a ticket
        Model.setAveragePlayersCount(Model.getAveragePlayersCount() + ticketCount);
        Model.initValues();
    }

    public static void reset() {
        ticketCount = 0;
        winners.clear();
        moneySpentOnTickets = 0;
        prize = 0;
        newTicketsWon = 0;
    }

    public static void play(int ticketsCount) {
        int count = 0;

        for (int i = 0; i < ticketsCount; i++) {
            Ticket result = new Ticket(i).play();
            if (result != null) {
                winners.add(result);
                prize += result.getPrize();
                if (result.isNewTicketWon()) {
                    count++;
                }
            }
        }
        if (count != 0) {
            newTicketsWon += count;
            play(count);
        }
    }

    public static void printResult() {
        ConsoleHelper.writeMessage("RESULT OF THE GAME:");
        ConsoleHelper.writeMessage(winners.size() + "/" + ticketCount + " tickets has won");
        ConsoleHelper.writeMessage("Total prize = " + (double) Math.round(prize * 100) / 100 + " BYN");
        ConsoleHelper.writeMessage("You won " + newTicketsWon + " new ticket(s)");

        if (moneySpentOnTickets > prize) {
            double lost = Math.round((moneySpentOnTickets - prize) * 100.0) / 100.0;
            ConsoleHelper.writeMessage("You lost " + lost + " BYN");
        } else if (prize > moneySpentOnTickets) {
            double won = Math.round((prize - moneySpentOnTickets) * 100.0) / 100.0 ;
            ConsoleHelper.writeMessage("You won " +  won + " BYN");
        } else {
            ConsoleHelper.writeMessage("You won 0 BYN");
        }
        ConsoleHelper.writeMessage("*********************");
    }

    public static void printWinBilletsMessages() {
        if (winners.size() == 0) {
            ConsoleHelper.writeMessage("There is no winning tickets yet.");
        }
        else {
            for (Ticket ticket : winners) {
                if (ticket.getWinMessage() != null) {
                ConsoleHelper.writeMessage(ticket.getWinMessage()); }
            }
        }
        ConsoleHelper.writeMessage("**************");
    }

    public static void main(String[] args) {

        Operation operation;
        while (true) {
            operation = ConsoleHelper.askOperation();
            if (operation.equals(Operation.EXIT)) {
                CommandExecutor.execute(operation);
                break;
            }
            CommandExecutor.execute(operation);
        }
    }
}

