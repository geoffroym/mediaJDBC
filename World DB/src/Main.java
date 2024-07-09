import java.io.FileNotFoundException;
import java.sql.*;
public class Main {
    public static void main(String[] args) throws SQLException {
        Program program = new Program();
        /*try {
            program.splitAustralia();
        } catch (SQLException e){
            System.out.println("SQL exception caught: " + e.getMessage());
            e.printStackTrace();
        } catch (FileNotFoundException e){
            System.out.println("FileNotFoundExcepction caught: " + e.getMessage());
            e.printStackTrace();
        }*/

        program.run();
        }
    }
