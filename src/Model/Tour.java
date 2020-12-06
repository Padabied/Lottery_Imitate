package Model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect
public class Tour {

    private int winners;
    private double prize;

    public int getWinners() {
        return winners;
    }
    public double getPrize() {
        return prize;
    }

    @Override
    public String toString() {
        return String.format("Winners = %d, prize = %f", winners, prize);
    }
}
