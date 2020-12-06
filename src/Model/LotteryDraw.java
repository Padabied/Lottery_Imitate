package Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;
@JsonAutoDetect
public class LotteryDraw {

    // Every lottery draw consists 13 tours
    private static int TOURS_COUNT = 13;
    private int players;
    @JsonSerialize (contentAs = Tour.class)
    private List<Tour> tours;

    public List<Tour> getTours() {
        return tours;
    }

    public int getPlayers() {
        return players;
    }

    public static int getToursCount() {
        return TOURS_COUNT;
    }

    public static void setToursCount(int toursCount) {
        TOURS_COUNT = toursCount;
    }

    @Override
    public String toString() {
        return String.format("Tours count = %d, players = %d, Tours = %s", TOURS_COUNT, players, tours.toString());
    }
}
