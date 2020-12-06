package Model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Model {

    /**
     * averageWinnersInTour - average number of winners in specific tour
     * averagePrizeInTour - average prize value in specific tour
     * chanceToWinInTour - chance to win in a specific tour
     * ticketsInDraw - number of people who won a new lottery ticket in a specific draw
     * chanceToWinTicket - chance to win a new ticket
     * **/
    @JsonSerialize (contentAs = LotteryDraw.class)
    private static List<LotteryDraw> draws;

    private static File data = new File("src/Model/data/data");
    private static File newTicketsWonInTourData = new File("src/Model/data/newTicketsWonInDraw");
    private static int averagePlayersCount;
    private static int averageBilletWinners;
    private static double chanceToWinTicket;
    private static double chanceToLose;
    private static Map<Integer, Double> averageWinnersInTour;
    private static Map<Integer, Double> averagePrizeInTour;
    private static Map<Integer, Double> chanceToWinInTour;
    private static Map<Integer, Integer> ticketsInDraw;

    public Model(int toursCount){
        LotteryDraw.setToursCount(toursCount);
    }

    static {
        loadNewTicketsWonInDraw();
        loadPreviousLotteryDraws();
        averageWinnersInTour = new TreeMap<>();
        chanceToWinInTour = new TreeMap<>();
        averagePrizeInTour = new TreeMap<>();

        initValues();
    }

    //loads data to "draws"
    private static void loadNewTicketsWonInDraw() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<Integer, Integer>> typeRef = new TypeReference<>() {};
        try {
            ticketsInDraw = mapper.readValue(newTicketsWonInTourData, typeRef);
        } catch (IOException ignored) {}
    }

    //loads data to "billetsInDraw"
    private static void loadPreviousLotteryDraws()  {
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, LotteryDraw.class);
        try {
            draws = mapper.readValue(data, javaType);
        } catch (IOException ignored) {}
    }

    //init static fields
    public static void initValues() {
        calculateAveragePlayersCount();
        calculateAverageWinnersInTour();
        calculateAverageBilletWinners();
        calculateAveragePrizeInTour();
        calculateChanceToWinInTour();
        calculateChanceToWinTheNewBillet();
        calculateChanceToLose();
    }

    public static Map<Integer, Double> getAveragePrizeInTour() {
        return averagePrizeInTour;
    }

    public static Map<Integer, Double> getChanceToWinInTour() {
        return chanceToWinInTour;
    }

    public static void setAveragePlayersCount(int averagePlayersCount) {
        Model.averagePlayersCount = averagePlayersCount;
    }

    public static int getAveragePlayersCount() {
        return averagePlayersCount;
    }

    public static double getChanceToWinTicket() {
        return chanceToWinTicket;
    }

    //Next methods calculate values of static fields
    private static void calculateAveragePlayersCount() {
        int sum = 0;
        for (LotteryDraw draw : draws) {
            sum += draw.getPlayers();
        }
        averagePlayersCount = sum / draws.size();
    }

    private static void calculateAverageWinnersInTour() {

        for (int i = 0; i < LotteryDraw.getToursCount(); i++) {
            int sum = 0;
            double result;
            for (LotteryDraw draw : draws) {
                sum += draw.getTours().get(i).getWinners();
            }
            result =  (double) sum / (double) draws.size();

            averageWinnersInTour.put(i, result);
        }
    }

    private static void calculateAveragePrizeInTour() {

        for (int i = 0; i < LotteryDraw.getToursCount(); i++) {
            int prizeSum = 0;
            double result;
            for (LotteryDraw draw : draws) {
                prizeSum += draw.getTours().get(i).getPrize();
            }
            result = (double) prizeSum / (double) draws.size();
            averagePrizeInTour.put(i, result);
        }
    }

    private static void calculateAverageBilletWinners() {
        int sum = 0;
        for (Map.Entry<Integer, Integer> pair : ticketsInDraw.entrySet()) {
           sum += pair.getValue();
        }
        averageBilletWinners = sum / ticketsInDraw.size();
    }

    private static void calculateChanceToWinInTour() {
        for (Map.Entry<Integer, Double> pair : averageWinnersInTour.entrySet()) {
            int tourNumber = pair.getKey();
            double winners = pair.getValue();
            double chanceToWin =  ((winners / averagePlayersCount) * 100);

            chanceToWinInTour.put(tourNumber, chanceToWin );
        }
    }

    private static void calculateChanceToWinTheNewBillet() {
        chanceToWinTicket = (((double) averageBilletWinners / (double) averagePlayersCount) * 100.0d);
    }

    private static void calculateChanceToLose() {
        double sum = 0;
        for (int i = 0; i < LotteryDraw.getToursCount(); i++) {
            sum += chanceToWinInTour.get(i);
        }
        chanceToLose = 100 - sum;
    }

    public static void printInfo() {
        ConsoleHelper.writeMessage(String.format("INFORMATION about average chances of last %d games :", draws.size()));
        for (Map.Entry<Integer, Double> pair : chanceToWinInTour.entrySet()) {
            double prize = averagePrizeInTour.get(pair.getKey());
            ConsoleHelper.writeMessage(String.format("Tour #%d : chance to win = %f%%", pair.getKey(), pair.getValue()));
            ConsoleHelper.writeMessage(String.format("Average prize in Tour #%d = %.1f BYN \n", pair.getKey(), prize));

        }
        ConsoleHelper.writeMessage("********************");
        ConsoleHelper.writeMessage(String.format("Chance to win the new billet = %f%%", chanceToWinTicket));
        ConsoleHelper.writeMessage(String.format("Chance to loose = %f%%", chanceToLose));
    }

}

