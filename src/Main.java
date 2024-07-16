import db.FileReader;
import db.MediaService;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        new Program();
        /*MediaService mediaService = new MediaService();
        try {
            //mediaService.addGameFromFile();
            //mediaService.addBookFromFile();
            //mediaService.addMovieFromFile();
            //mediaService.addGameFromFile();
        } catch (FileNotFoundException | SQLException e){
            e.printStackTrace();
        }*/
    }
}