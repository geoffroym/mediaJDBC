package db;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class QuizProperties {
    public static final Properties PROPS;
    static {
        PROPS = new Properties();
        try {
            PROPS.load(new FileReader("quiz.properties"));
        } catch (IOException ex) {
            System.out.println("Unable to load properties:" + ex.getMessage());
        }
    }
}

