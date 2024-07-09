package db;

import com.mysql.cj.jdbc.MysqlDataSource;
import dto.Staff;
import dto.Student;
import dto.StudyProgram;
import props.UniversityProperties;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UniversityDao {
    private MysqlDataSource universityDS;

    private static final String GET_ALL_STUDENTS_SQL = "SELECT s.id, s.name, StudyProgram_id, sp.name, sp.programResponsible_id, st.name\n" +
            "FROM Student s JOIN studyProgram sp ON s.StudyProgram_id = sp.id\n" +
            "JOIN Staff st ON sp.programResponsible_id = st.id";
    private static final String GET_STUDYPROGRAM_SQL = "SELECT sp.id, sp.name, sp.programResponsible_id, s.name\n" +
            "FROM StudyProgram sp JOIN staff s ON sp.programResponsible_id = s.id";
    private static final String GET_ALL_STAFF = "SELECT id, name FROM Staff";
    private static final String GET_STUDENT_BY_NAME_SQL = "SELECT s.id, s.name, StudyProgram_id, sp.name, sp.programResponsible_id, st.name\n" +
            "FROM Student s JOIN studyProgram sp ON s.StudyProgram_id = sp.id\n" +
            "JOIN Staff st ON sp.programResponsible_id = st.id WHERE s.name = ?";
    private static final String GET_STUDENT_BY_ID_SQL = "SELECT s.id, s.name, StudyProgram_id, sp.name, sp.programResponsible_id, st.name\n" +
            "FROM Student s JOIN studyProgram sp ON s.StudyProgram_id = sp.id\n" +
            "JOIN Staff st ON sp.programResponsible_id = st.id WHERE s.id = ?";
    private static final String GET_STUDENTS_IN_PROGRAM_SQL = "SELECT s.id, s.name, StudyProgram_id, sp.name, sp.programResponsible_id, st.name\n" +
            "FROM Student s JOIN studyProgram sp ON s.StudyProgram_id = sp.id\n" +
            "JOIN Staff st ON sp.programResponsible_id = st.id WHERE sp.id=?";

    public UniversityDao (){
        universityDS = new MysqlDataSource();
        universityDS.setPassword(UniversityProperties.PROPS.getProperty("pwd"));
        universityDS.setUser(UniversityProperties.PROPS.getProperty("uname"));
        universityDS.setDatabaseName(UniversityProperties.PROPS.getProperty("db_name"));
        universityDS.setPortNumber(Integer.parseInt(UniversityProperties.PROPS.getProperty("port")));
        universityDS.setServerName(UniversityProperties.PROPS.getProperty("host"));
    }

    public List<Student> getStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection conn = universityDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_STUDENTS_SQL);
             ResultSet rs = stmt.executeQuery()){
            while (rs.next()){
                int id = rs.getInt("s.id");
                String name = rs.getString("s.name");
                int studyProgramId = rs.getInt("StudyProgram_id");
                String studyProgramName = rs.getString("StudyProgram_id");
                int programResponsibleID = rs.getInt("sp.programResponsible_id");
                String programResponsibleName = rs.getString("st.name");
                Staff staff = new Staff(programResponsibleID, programResponsibleName);
                StudyProgram studyProgram = new StudyProgram(studyProgramId, studyProgramName, staff);
                students.add(new Student(id, name, studyProgram));
            }
        }
        return students;
    }

    public List<StudyProgram> getStudyProgram() throws SQLException {
        List<StudyProgram> studyPrograms = new ArrayList<>();
        try (Connection conn = universityDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_STUDYPROGRAM_SQL);
             ResultSet rs = stmt.executeQuery()){
            while (rs.next()){
                int id = rs.getInt("sp.id");
                String name = rs.getString("sp.name");
                int programResponsibleId = rs.getInt("sp.programResponsible_id");
                String programResponsibleName = rs.getString("s.name");
                Staff staff = new Staff(programResponsibleId, programResponsibleName);
                studyPrograms.add(new StudyProgram(id, name, staff));
            }
        }
        return studyPrograms;
    }

    public List<Staff> getStaff() throws SQLException {
        List<Staff> staff = new ArrayList<>();
        try (Connection conn = universityDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_ALL_STAFF);
             ResultSet rs = stmt.executeQuery()){
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                staff.add(new Staff(id, name));
            }
        }
        return staff;
    }


    public Optional<Student> getStudentByName(String name) throws SQLException {
        try (Connection conn = universityDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_STUDENT_BY_NAME_SQL)){
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    int id = rs.getInt("s.id");
                    int studyProgramId = rs.getInt("StudyProgram_id");
                    String studyProgramName = rs.getString("StudyProgram_id");
                    int programResponsibleID = rs.getInt("sp.programResponsible_id");
                    String programResponsibleName = rs.getString("st.name");
                    Staff staff = new Staff(programResponsibleID, programResponsibleName);
                    StudyProgram studyProgram = new StudyProgram(studyProgramId, studyProgramName, staff);
                    return Optional.of(new Student(id, name, studyProgram));
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Student> getStudent(int registeredStudentId) throws SQLException {
        try (Connection conn = universityDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_STUDENT_BY_ID_SQL)){
            stmt.setInt(1, registeredStudentId);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){
                    int id = rs.getInt("s.id");
                    String name = rs.getString("s.name");
                    int studyProgramId = rs.getInt("StudyProgram_id");
                    String studyProgramName = rs.getString("StudyProgram_id");
                    int programResponsibleID = rs.getInt("sp.programResponsible_id");
                    String programResponsibleName = rs.getString("st.name");
                    Staff staff = new Staff(programResponsibleID, programResponsibleName);
                    StudyProgram studyProgram = new StudyProgram(studyProgramId, studyProgramName, staff);
                    return Optional.of(new Student(id, name, studyProgram));
                }

            }
        }
        return Optional.empty();
    }

    public List<Student> getStudentsInStudyProgram(int studyProgramId) throws SQLException {
        List<Student> students = new ArrayList<>();
        try (Connection conn = universityDS.getConnection();
             PreparedStatement stmt = conn.prepareStatement(GET_STUDENTS_IN_PROGRAM_SQL);
             ){
            stmt.setInt(1, studyProgramId);
            try(ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("s.id");
                    String name = rs.getString("s.name");
                    String studyProgramName = rs.getString("StudyProgram_id");
                    int programResponsibleID = rs.getInt("sp.programResponsible_id");
                    String programResponsibleName = rs.getString("st.name");
                    Staff staff = new Staff(programResponsibleID, programResponsibleName);
                    StudyProgram studyProgram = new StudyProgram(studyProgramId, studyProgramName, staff);
                    students.add(new Student(id, name, studyProgram));
                }
            }
        }
        return students;
    }
}
