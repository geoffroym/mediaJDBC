import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class TestDrive {
    public static void main(String[] args) throws IOException {
        BookRegister br = new BookRegister();
        br.initRegister();
        br.amountOfBooksRegistered();
        System.out.println(br.amountOfBooksRegistered());
        br.showAllBooks();
        Program program = new Program();
        program.setBookRegister(br);
        program.run();

        try{
            br.writeBookFile();
        }catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}