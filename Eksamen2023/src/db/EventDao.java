package db;

import com.mysql.cj.jdbc.MysqlDataSource;
import dto.Guest;
import dto.Student;
import props.EventProperties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDao {
    private MysqlDataSource eventDB;

    private static final String ADD_STUDENT_REGISTRATION_SQL = "INSERT INTO StudentRegistration VALUES (?)";
    private static final String ADD_GUEST_SQL = "INSERT INTO GuestRegistration (name, StudentRegistrationId) VALUES(?, ?)";
    private static final String DELETE_ALL_GUESTS_SQL = "DELETE FROM GuestRegistration WHERE StudentRegistrationId = ?";
    private static final String IS_STUDENT_REGISTERED_SQL = "SELECT COUNT(*) AS count FROM StudentRegistration WHERE student_id=?";
    private static final String GET_GUEST_FOR_STUDENT_SQL = "SELECT id, name FROM GuestRegistration WHERE StudentRegistrationId = ?";
    private static final String DELETE_STUDENT_REGISTRATION = "DELETE FROM StudentRegistration WHERE student_id = ?;" ;
    private static final String GET_STUDENT_REGISTERED_ID_SQL = "SELECT student_id FROM StudentRegistration";


    public EventDao() {
        eventDB = new MysqlDataSource();
        eventDB.setPassword(EventProperties.PROPS.getProperty("pwd"));
        eventDB.setUser(EventProperties.PROPS.getProperty("uname"));
        eventDB.setDatabaseName(EventProperties.PROPS.getProperty("db_name"));
        eventDB.setPortNumber(Integer.parseInt(EventProperties.PROPS.getProperty("port")));
        eventDB.setServerName(EventProperties.PROPS.getProperty("host"));
    }

    public void addStudentRegistration(Student student) throws SQLException {
        try (Connection conn = eventDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(ADD_STUDENT_REGISTRATION_SQL)
        ) {
            boolean autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            stmt.setInt(1, student.getId());
            try {
                stmt.executeUpdate();
                for (Guest guest : student.getGuests()) {
                    addGuest(guest, conn);
                }
                conn.commit();
            } catch (SQLException sqle) {
                conn.rollback();
                throw sqle;
            } finally {
                conn.setAutoCommit(autoCommit);
            }
        }
    }

    private void addGuest(Guest guest, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(ADD_GUEST_SQL)) {
            stmt.setString(1, guest.getName());
            stmt.setInt(2, guest.getRegisteredStudent().getId());
            stmt.executeUpdate();
        }
    }

    public void deleteStudentRegistration(Student student) throws SQLException {
        try (Connection conn = eventDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(DELETE_STUDENT_REGISTRATION)
        ){
            stmt.setInt(1, student.getId());
            stmt.executeUpdate();

        }
    }

    public void updateRegistration(Student student) throws SQLException {
        try (Connection conn = eventDB.getConnection()
        ) {
            boolean autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try {
                deleteAllGuestForStudent(student, conn);
                for (Guest guest : student.getGuests()) {
                    addGuest(guest, conn);
                }
                conn.commit();
            } catch (SQLException sqle) {
                conn.rollback();
                throw sqle;
            } finally {
                conn.setAutoCommit(autoCommit);
            }
        }
    }

    private void deleteAllGuestForStudent(Student student, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(DELETE_ALL_GUESTS_SQL)) {
            stmt.setInt(1, student.getId());
            stmt.executeUpdate();
        }
    }

    public boolean isStudentregisteredForEvent(Student student) throws SQLException {
        try (Connection conn = eventDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(IS_STUDENT_REGISTERED_SQL) //count returns number of rows
        ) {
            stmt.setInt(1, student.getId()); //if we search by student's id
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count"); //if there is a row there, then it means student is registered
                    return count == 1;
                }
            }
        }
        throw new SQLException("Unable to retrieve student registration."); //if you don't find the row, return statement
    }

    public List<Guest> getGuestForStudent(Student student) throws SQLException {
        List<Guest> guests = new ArrayList<>();
        try (Connection conn = eventDB.getConnection();
        PreparedStatement stmt = conn.prepareStatement(GET_GUEST_FOR_STUDENT_SQL)
        ){
            stmt.setInt(1, student.getId());
            try (ResultSet rs = stmt.executeQuery()){
                while (rs.next()){
                    guests.add(new Guest(rs.getInt("id"), rs.getString("name"), student));
                }
            }
        }
        return guests;
    }

    public List<Integer> getRegisteredStudentIds() throws SQLException {
        List<Integer> ids = new ArrayList<>();
        try (Connection conn = eventDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_STUDENT_REGISTERED_ID_SQL);
             ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()){
                ids.add(rs.getInt("student_id"));
            }
        }
        return ids;
    }
}


