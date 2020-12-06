package Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class ConsoleHelper {

    static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public static String readString() {
        try  {
            return reader.readLine();
        }
        catch (IOException ignored) {
            return null;
        }
    }

    public static void writeMessage(String string) {
        System.out.println(string);
    }

    public static int readInt() {
        try {
            int result = Integer.parseInt(Objects.requireNonNull(readString()));
            if (result < 0 || result > 1000000) {
                throw new IllegalArgumentException();
            }
            return result;
        }
        catch (Exception e) {
            ConsoleHelper.writeMessage("Please enter decimal between 1 and 1 mln");
            return readInt();
        }
    }

    public static Operation askOperation() {
        writeMessage("1 - PLAY\n2 - INFO\n3 - WIN_MESSAGES\n4 - EXIT");
        int command = readInt();
        if (command < 1 || command > Operation.values().length) {
            writeMessage("Please, choose an operation : ");
            return askOperation();
        }
        else {
            return Operation.getOperation(command);
        }
    }
}
