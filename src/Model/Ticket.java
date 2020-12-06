package Model;

import java.util.Map;
import java.util.Random;

public class Ticket {


    private double prize = 0;
    private int number;
    private boolean newTicketWon;
    private Map<Integer, Double> chanceToWinInTour;
    private Map<Integer, Double> averagePrizeInTour;
    private String winMessage;

    public Ticket(int number) {
        this.number = number;
        this.chanceToWinInTour = Model.getChanceToWinInTour();
        this.averagePrizeInTour = Model.getAveragePrizeInTour();
    }

    public String getWinMessage() {
        if (winMessage == null) {
            ConsoleHelper.writeMessage(String.format("Ticket #%d has won a new lottery ticket!", number));
        }
        return winMessage;
    }

    public double getPrize() {
        return prize;
    }

    public boolean isNewTicketWon() {
        return newTicketWon;
    }

    public Ticket play() {
        int winningCount = 1;
        double chanceToWinNewTicket = Model.getChanceToWinTicket();
        int ticketBound = (int) (100 / chanceToWinNewTicket);
        Random random = new Random();
        for (int i = 0; i < LotteryDraw.getToursCount(); i++) {
            double chanceToWin = chanceToWinInTour.get(i);
            double averagePrize = averagePrizeInTour.get(i);

            int bound = (int) (100 / chanceToWin);

            if (random.nextInt(bound) == winningCount) {
                prize += averagePrize;
                double pr = Math.round(prize * 100.0) / 100.0;
                winMessage = "Ticket #" + number + " has won in " + i + " tour. Prize = " + pr + " BYN";
                return this;
            }
        }

        if (random.nextInt(ticketBound) == winningCount) {
            newTicketWon = true;
            return this;
        }
        return null;
    }
}
