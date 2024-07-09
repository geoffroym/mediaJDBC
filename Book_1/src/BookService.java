import com.mysql.cj.jdbc.MysqlDataSource;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookService implements BookProvider{
    private final MysqlDataSource bookDB;

    private static final String GET_ALL_BOOKS_SQL = "SELECT isbn, published, title, author, numberOfPages, genre, minutesPerPage FROM Book";

    private static final String GET_ALL_CHAPTERS_FOR_BOOKS_SQL = "SELECT id, book_isbn, title, numberOfPages from Chapter WHERE book_isbn=?";

    private static final String ADD_BOOK_SQL = "INSERT INTO Book VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String DELETE_BOOK_SQL = "DELETE FROM Book WHERE isbn=?";
    public BookService(){
        bookDB = new MysqlDataSource();
        bookDB.setServerName(PropertiesProvider.PROPS.getProperty("host"));
        bookDB.setPortNumber(Integer.parseInt(PropertiesProvider.PROPS.getProperty("port")));
        bookDB.setDatabaseName(PropertiesProvider.PROPS.getProperty("db_name"));
        bookDB.setUser(PropertiesProvider.PROPS.getProperty("uname"));
        bookDB.setPassword(PropertiesProvider.PROPS.getProperty("pwd"));
    }

    @Override
    public List<Book> retrieveBooks() throws Exception {
        List<Book> books = new ArrayList<>();
        try (Connection conn = bookDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL_BOOKS_SQL)
        ){
            while (rs.next()){
                String isbn = rs.getString("isbn");
                LocalDate published = rs.getDate("published").toLocalDate();
                String title = rs.getString("title");
                String author = rs.getString("author");
                int numberOfPages = rs.getInt("numberOfPages");
                Genre genre = Genre.valueOf(rs.getString("genre"));
                int minutesPerPage = rs.getInt("minutesPerPage");
                Book book = new Book(isbn, published, title, author, numberOfPages, genre, minutesPerPage);
                book.setChapters(getChaptersForBook(isbn));
                books.add(book);
            }
        }
        return books;
    }

    private List<Chapter> getChaptersForBook(String isbn) throws SQLException {
        List<Chapter> chapters = new ArrayList<>();
        try (Connection conn = bookDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_CHAPTERS_FOR_BOOKS_SQL);
        ){
            stmt.setString(1, isbn);
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    int id = rs.getInt("id");
                    String book_isbn = rs.getString("book_isbn");
                    String title = rs.getString("title");
                    int numberOfPages = rs.getInt("numberOfPages");
                    chapters.add(new Chapter(title, numberOfPages));
                }
            }
        }
        return chapters;
    }

    public int addBook(Book book, Connection con) throws SQLException {
        try (PreparedStatement ps = con.prepareStatement(ADD_BOOK_SQL)
                ){
            ps.setString(1, book.getIsbn());
            ps.setDate(2, Date.valueOf(book.getPublished()));
            ps.setString(3, book.getTitle());
            ps.setString(4, book.getAuthor());
            ps.setInt(5, book.getNumberOfPages());
            ps.setString(6, String.valueOf(book.getGenre()));
            ps.setInt(7, book.getMinutesPerPage());
            return ps.executeUpdate();
        }
    }

    public int addNewBook(String isbn, LocalDate published, String title, String author, int numberOfPages, Genre genre, int minutesPerPage) throws SQLException {
        try (Connection conn = bookDB
                .getConnection()) {
            PreparedStatement ps = conn.prepareStatement(ADD_BOOK_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, isbn);
            ps.setDate(2, Date.valueOf(published));
            ps.setString(3, title);
            ps.setString(4, author);
            ps.setInt(5, numberOfPages);
            ps.setString(6, String.valueOf(genre));
            ps.setInt(7, minutesPerPage);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
        }
        return 0;
    }

    public int deleteBook(String isbn) throws SQLException {
        try (Connection conn = bookDB.getConnection()){
            PreparedStatement ps = conn.prepareStatement(DELETE_BOOK_SQL);
            ps.setString(1, isbn);
            return ps.executeUpdate();
        }
    }
}
