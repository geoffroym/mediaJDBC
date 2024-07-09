import java.util.Arrays;

public class UserCommunication {
    public static final String WELCOME = "Welcome to the book register. Here are your options:";
    public static final String OPTIONS = "Choose option from 1 - 8:";
    public static final String ENTER_AUTHOR;
    static {
        ENTER_AUTHOR = "Enter author:";
    }
    public static final String ENTER_GENRE = String.format("Enter genre %s", Arrays.toString(Genre.values()));
}
