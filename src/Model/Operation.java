package Model;
public enum Operation {

    PLAY,
    INFO,
    GET_WIN_MESSAGES,
    EXIT;

    public static Operation getOperation (int number) {
        switch (number) {
            case 1 : return PLAY;
            case 2 : return INFO;
            case 3 : return GET_WIN_MESSAGES;
            case 4 : return EXIT;
        }
        return null;
    }
}
