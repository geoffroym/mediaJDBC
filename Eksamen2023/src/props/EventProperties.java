package props;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class EventProperties {
        public static final Properties PROPS;
        static {
            PROPS = new Properties();
            try {
                PROPS.load(new FileReader("files/event.properties"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
}
