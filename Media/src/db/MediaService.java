package db;

import com.mysql.cj.jdbc.MysqlDataSource;
import dto.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MediaService {
    private static final String GET_ALL_BOOKS_SQL = "SELECT books.id, title, author, genre, pages, mediaId, media.type FROM books JOIN media ON books.mediaId = media.id";
    private static final String GET_ALL_MOVIES_SQL = "SELECT movies.id, title, director, genre, ageRating, mediaId, media.type FROM movies JOIN media ON movies.mediaId = media.id";
    private static final String GET_ALL_GAMES_SQL = """
            SELECT g.id, g.title, g.developer, g.publisher, g.ageRating, g.mode, g.mediaId, m.type, GROUP_CONCAT(p.platformName SEPARATOR ', ') AS platforms
            FROM games g JOIN media m ON g.mediaId = m.id
            JOIN game_platform gp ON g.id = gp.gameId
            JOIN platform p ON gp.platform_id = p.platform_id
            GROUP BY g.id""";
    private static final String GET_USER_BY_NAME_SQL = "SELECT id, username FROM user WHERE username =?";
    private static final String INSERT_NEW_USER_SQL = "INSERT INTO user(username) VALUES (?)";
    private static final String GET_BOOKS_BY_AUTHOR_SQL = "SELECT id, title, author, genre, pages FROM books WHERE author = ?";
    private static final String ADD_BOOK_SQL = "INSERT INTO books (title, author, genre, pages, mediaId) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_BOOKS_BY_TITLE_SQL = "SELECT id, title, author, genre, pages FROM books WHERE title = ?";
    private static final String DELETE_BOOK_SQL = "DELETE FROM books WHERE title = ?";
    private static final String GET_MOVIES_BY_DIRECTOR_SQL = "SELECT id, title, director, genre, ageRating FROM movies WHERE director=?";
    private static final String ADD_MOVIE_SQL = "INSERT INTO movies (title, director, genre, ageRating, mediaId) VALUES (?, ?, ?, ?, ?)";
    private static final String GET_MOVIES_BY_TITLE_SQL = "SELECT title, director, genre, ageRating FROM movies WHERE title=?";
    private static final String DELETE_MOVIE_SQL = "DELETE FROM movies WHERE title=?";
    private static final String GET_GAMES_BY_DEVELOPER_OR_PUBLISHER = """
            SELECT g.id, g.title, g.developer, g.publisher, g.ageRating, g.mode, g.mediaId, m.type, GROUP_CONCAT(p.platformName SEPARATOR ', ') AS platforms
            FROM games g JOIN media m ON g.mediaId = m.id
            JOIN game_platform gp ON g.id = gp.gameId
            JOIN platform p ON gp.platform_id = p.platform_id
            WHERE g.developer LIKE ? OR g.publisher LIKE ?
            GROUP BY g.id;""";
    private static final String ADD_GAME_SQL = "INSERT INTO games (title, developer, publisher, ageRating, mode, mediaId) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String ADD_GAME_PLATFORM_SQL = "INSERT INTO game_platform (gameId, platform_id) VALUES (?, ?)";
    private static final String GET_PLATFORM_ID_SQL = "SELECT platform_id FROM platform WHERE platformName =?";
    private static final String DELETE_GAME_SQL = "DELETE FROM games WHERE title =?";
    private static final String GET_GAMES_BY_TITLE_SQL = "SELECT title, developer, publisher, ageRating, mode FROM games WHERE title=?";
    private static final String GET_SEARCH_MEDIA_SQL = """
            SELECT 'Book' AS media_type, title, author AS creators
            FROM books
            WHERE LOWER(title) LIKE LOWER(?) OR LOWER(author) LIKE LOWER(?)
            UNION
            SELECT 'Game' AS media_type, title, developer AS creators
            FROM games
            WHERE LOWER(title) LIKE LOWER(?) OR LOWER(developer) LIKE LOWER(?)
            UNION
            SELECT 'Movie' AS media_type, title, director AS creators
            FROM movies
            WHERE LOWER(title) LIKE LOWER(?) OR LOWER(director) LIKE LOWER(?)""";

    private MysqlDataSource mediaDS;

    public MediaService() {
        mediaDS = new MysqlDataSource();
        mediaDS.setPassword(MediaProperties.PROPS.getProperty("pwd"));
        mediaDS.setUser(MediaProperties.PROPS.getProperty("uname"));
        mediaDS.setDatabaseName(MediaProperties.PROPS.getProperty("db_name"));
        mediaDS.setPortNumber(Integer.parseInt(MediaProperties.PROPS.getProperty("port")));
        mediaDS.setServerName(MediaProperties.PROPS.getProperty("host"));
    }

    public List<Books> getAllBooks() throws SQLException {
        List<Books> books = new ArrayList<>();
        try (Connection conn = mediaDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_BOOKS_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int bookId = rs.getInt("books.id");
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                int pages = rs.getInt("pages");
                int mediaId = rs.getInt("mediaId");
                String type = rs.getString("media.type");
                books.add(new Books(bookId, title, author, genre, pages, new Media(mediaId, type)));
            }
        }
        return books;
    }

    public List<Movies> getAllMovies() throws SQLException {
        List<Movies> movies = new ArrayList<>();
        try (Connection conn = mediaDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_MOVIES_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int moviesId = rs.getInt("movies.id");
                String title = rs.getString("title");
                String director = rs.getString("director");
                String genre = rs.getString("genre");
                int ageRating = rs.getInt("ageRating");
                int mediaId = rs.getInt("mediaId");
                String type = rs.getString("media.type");
                movies.add(new Movies(moviesId, title, director, genre, ageRating, new Media(mediaId, type)));
            }
        }
        return movies;
    }

    public List<Games> getAllGames() throws SQLException {
        List<Games> games = new ArrayList<>();
        try (Connection conn = mediaDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_GAMES_SQL);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int gameId = rs.getInt("g.id");
                String title = rs.getString("g.title");
                String developer = rs.getString("g.developer");
                String publisher = rs.getString("g.publisher");
                int ageRating = rs.getInt("g.ageRating");
                String mode = rs.getString("g.mode");
                int mediaId = rs.getInt("g.mediaId");
                String type = rs.getString("m.type");
                String platformsConcat = rs.getString("platforms");

                String[] platformNames = platformsConcat.split(", ");
                List<Platform> platforms = new ArrayList<>();
                for (String platformName : platformNames) {
                    platforms.add(new Platform(platformName));
                }
                games.add(new Games(gameId, title, developer, publisher, ageRating, mode, new Media(mediaId, type), platforms));
            }
        }
        return games;
    }

    public Optional<User> loginUser(String name) throws SQLException {
        Optional<User> user = getUser(name);
        if (user.isPresent()) {
            return user;
        }
        return createNewUser(name);
    }

    private Optional<User> createNewUser(String username) throws SQLException {
        try (Connection conn = mediaDS.getConnection(); //when there is an auto_incremented id, we have to pass not only sql code, but also connection with generated keys
             PreparedStatement ps = conn.prepareStatement(INSERT_NEW_USER_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, username);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 1) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        return Optional.of(new User(keys.getInt(1), username));
                    }
                }
            }
        }
        return Optional.empty();
    }

    public Optional<User> getUser(String username) throws SQLException {
        try (Connection conn = mediaDS.getConnection();
             PreparedStatement ps = conn.prepareStatement(GET_USER_BY_NAME_SQL)
        ) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    return Optional.of(new User(id, username));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Books> getBooksByAuthor(String author) throws SQLException {
        try (Connection conn = mediaDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BOOKS_BY_AUTHOR_SQL)) {
            stmt.setString(1, author);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String genre = rs.getString("genre");
                    int pages = rs.getInt("pages");
                    return Optional.of(new Books(id, title, author, genre, pages));
                }
            }
        }
        return Optional.empty();
    }


    public int addBook(Books books) throws SQLException {
        try (Connection conn = mediaDS.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(ADD_BOOK_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, books.getTitle());
            ps.setString(2, books.getAuthor());
            ps.setString(3, books.getGenre());
            ps.setInt(4, books.getPages());
            ps.setInt(5, books.getMedia().getId());
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

    public Optional<Books> getBooksByTitle(String title) throws SQLException {
        try (Connection conn = mediaDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_BOOKS_BY_TITLE_SQL)) {
            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String author = rs.getString("title");
                    String genre = rs.getString("genre");
                    int pages = rs.getInt("pages");
                    return Optional.of(new Books(id, title, author, genre, pages));
                }
            }
        }
        return Optional.empty();
    }

    public int deleteBook(String title) throws SQLException {
        try (Connection conn = mediaDS.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(DELETE_BOOK_SQL);
            ps.setString(1, title);
            return ps.executeUpdate();
        }
    }

    public Optional<Movies> getMoviesByDirector(String director) throws SQLException {
        try (Connection conn = mediaDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_MOVIES_BY_DIRECTOR_SQL)) {
            stmt.setString(1, director);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String title = rs.getString("title");
                    String genre = rs.getString("genre");
                    int ageRating = rs.getInt("ageRating");
                    return Optional.of(new Movies(id, title, director, genre, ageRating));
                }
            }
        }
        return Optional.empty();
    }

    public int addMovie(Movies movies) throws SQLException {
        try (Connection conn = mediaDS.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(ADD_MOVIE_SQL, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, movies.getTitle());
            ps.setString(2, movies.getDirector());
            ps.setString(3, movies.getGenre());
            ps.setInt(4, movies.getAgeRating());
            ps.setInt(5, movies.getMedia().getId());
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

    public Optional<Movies> getMoviesByTitle(String title) throws SQLException {
        try (Connection conn = mediaDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_MOVIES_BY_TITLE_SQL)) {
            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String director = rs.getString("director");
                    String genre = rs.getString("genre");
                    int ageRating = rs.getInt("ageRating");
                    return Optional.of(new Movies(title, director, genre, ageRating));
                }
            }
        }
        return Optional.empty();
    }

    public int deleteMovie(String title) throws SQLException {
        try (Connection conn = mediaDS.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(DELETE_MOVIE_SQL);
            ps.setString(1, title);
            return ps.executeUpdate();
        }
    }

    public Optional<Games> getGamesByDeveloperOrPublisher(String choice) throws SQLException {
        try (Connection conn = mediaDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_GAMES_BY_DEVELOPER_OR_PUBLISHER)) {
            stmt.setString(1, choice);
            stmt.setString(2, choice);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int gameId = rs.getInt("g.id");
                    String title = rs.getString("g.title");
                    String developer = rs.getString("g.developer");
                    String publisher = rs.getString("g.publisher");
                    int ageRating = rs.getInt("g.ageRating");
                    String mode = rs.getString("g.mode");
                    int mediaId = rs.getInt("g.mediaId");
                    String type = rs.getString("m.type");
                    String platformsConcat = rs.getString("platforms");

                    String[] platformNames = platformsConcat.split(", ");
                    List<Platform> platforms = new ArrayList<>();
                    for (String platformName : platformNames) {
                        platforms.add(new Platform(platformName));
                    }
                    return Optional.of(new Games(gameId, title, developer, publisher, ageRating, mode, new Media(mediaId, type), platforms));
                }
            }
        }
        return Optional.empty();
    }

    public int addGame(Games games, List<Platform> platforms) throws SQLException {
        try (Connection conn = mediaDS.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int gameId = addGame(conn, games);
                List<Integer> platformIds = addPlatforms(conn, platforms);
                addGamePlatform(conn, gameId, platformIds);

                conn.commit();
                return gameId;
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    private int addGame(Connection conn, Games games) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(ADD_GAME_SQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, games.getTitle());
            ps.setString(2, games.getDeveloper());
            ps.setString(3, games.getPublisher());
            ps.setInt(4, games.getAgeRating());
            ps.setString(5, games.getMode());
            ps.setInt(6, games.getMedia().getId());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getInt(1);
                } else {
                    throw new SQLException("Game ID not generated.");
                }
            }
        }
    }

    private List<Integer> addPlatforms(Connection conn, List<Platform> platforms) throws SQLException {
        List<Integer> platformIds = new ArrayList<>();

        try (PreparedStatement psGet = conn.prepareStatement(GET_PLATFORM_ID_SQL)) {
            for (Platform platform : platforms) {
                psGet.setString(1, platform.getPlatformName());
                try (ResultSet rs = psGet.executeQuery()) {
                    if (rs.next()) {
                        platformIds.add(rs.getInt("platform_id"));
                    } else {
                        throw new SQLException("Platform ID not found");
                    }
                }
            }
        }
        return platformIds;
    }


    private void addGamePlatform(Connection conn, int gameId, List<Integer> platformIds) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(ADD_GAME_PLATFORM_SQL)) {
            for (int platformId : platformIds) {
                ps.setInt(1, gameId);
                ps.setInt(2, platformId);
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    public Optional<Games> getGamesByTitle(String title) throws SQLException {
        try (Connection conn = mediaDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_GAMES_BY_TITLE_SQL)) {
            stmt.setString(1, title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String developer = rs.getString("developer");
                    String publisher = rs.getString("publisher");
                    int ageRating = rs.getInt("ageRating");
                    String mode = rs.getString("mode");
                    return Optional.of(new Games(title, developer, publisher, ageRating, mode));
                }
            }
        }
        return Optional.empty();
    }

    public int deleteGame(String title) throws SQLException {
        try (Connection conn = mediaDS.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(DELETE_GAME_SQL);
            ps.setString(1, title);
            return ps.executeUpdate();
        }
    }

    public List<SearchMedia> searchMedia(String searchTerm) throws SQLException {
        List<SearchMedia> results = new ArrayList<>();

        try (Connection conn = mediaDS.getConnection();
        PreparedStatement ps = conn.prepareStatement(GET_SEARCH_MEDIA_SQL)){
            String searchPatters = "%" + searchTerm + "%";

            for (int i = 1; i <= 6; i++){
                ps.setString(i, searchPatters);
            }

            try (ResultSet rs = ps.executeQuery()){
                while (rs.next()){
                    String mediaType = rs.getString("media_type");
                    String title = rs.getString("title");
                    String creators = rs.getString("creators");

                    results.add((new SearchMedia(mediaType, title, creators)));
                }
            }
        }
        return  results;
    }
}