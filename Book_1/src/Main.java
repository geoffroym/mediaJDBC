import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            BookRegister bookRegister = new BookRegister(new BookService());
            //BookRegister bookRegister = new BookRegister(new BooksFileReader());
            new Program(bookRegister).run();
        } catch (Exception e) {
            System.out.println("Unable to write to file:" + e.getMessage());
            e.printStackTrace();
        }
    }
}