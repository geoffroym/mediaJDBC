import java.io.FileNotFoundException;
import java.util.List;

public interface BookProvider {
    List<Book> retrieveBooks() throws Exception;
}
