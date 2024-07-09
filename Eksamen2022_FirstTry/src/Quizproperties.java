import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Quizproperties {
    public static final Properties PROPS;
    static {
        PROPS = new Properties();
        try {
            PROPS.load(new FileReader("quiz.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
