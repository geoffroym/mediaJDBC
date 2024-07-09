package props;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class UniversityProperties {
    public static final Properties PROPS;
    static {
        PROPS = new Properties();
        try {
            PROPS.load(new FileReader("files/university.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
