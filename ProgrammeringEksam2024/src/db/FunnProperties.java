package db;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FunnProperties {
    public static final Properties PROPS;

    static {
        PROPS = new Properties();
        try {
            PROPS.load(new FileReader("funn.properties"));
        } catch (IOException ex) {
            System.out.println("Unable to load properties:" + ex.getMessage());
        }
    }
}
