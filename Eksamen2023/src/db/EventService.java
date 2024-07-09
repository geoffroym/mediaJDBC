package db;

import dto.*;

import java.sql.SQLException;
import java.util.*;

public class EventService {
    private final UniversityDao universityDao;
    private final EventDao eventDao;

    public EventService() {
        universityDao = new UniversityDao();
        eventDao = new EventDao();
    }

    public Optional<Student> getStudentByName(String name) throws SQLException {
        return universityDao.getStudentByName(name);
    }

    public boolean isStudentRegisteredForEvent(Student student) throws SQLException {
        return eventDao.isStudentregisteredForEvent(student);
    }

    public List<Guest> getGuestsForStudent(Student student) throws SQLException {
        return eventDao.getGuestForStudent(student);
    }

    public void updateStudentRegistration(Student student) throws SQLException {
        eventDao.updateRegistration(student);
    }

    public void deleteStudentRegistration(Student student) throws SQLException {
        eventDao.deleteStudentRegistration(student);
    }

    public void addStudentRegistration(Student student) throws SQLException {
        eventDao.addStudentRegistration(student);
    }

    public boolean isStudentRegisteredForEvent(String name) throws SQLException {
        Optional<Student> possibleStudent = universityDao.getStudentByName(name);
        if (possibleStudent.isPresent()){
            return eventDao.isStudentregisteredForEvent(possibleStudent.get());
        }
        return false;
    }

    public List<Person> getAllParticipants() throws SQLException {
        List<Student> students = universityDao.getStudents();
        List<Staff> staff = universityDao.getStaff();
        List <Person> persons = new ArrayList<>(staff); //add staff straight away
        Map<String, Guest> uniqueGuest = new HashMap<>(); //put guest in a map, so the names don't come as duplicates
        for (Student student : students) {
            if (eventDao.isStudentregisteredForEvent(student)){ //add students that are registered
                persons.add(student);
                List<Guest> guests = eventDao.getGuestForStudent(student); //add guests for students
                for (Guest guest : guests) {
                    uniqueGuest.put(guest.getName(), guest);
                }
            }
        }
        persons.addAll(uniqueGuest.values());
        return persons;
    }

    public List<Student> getAllRegisteredStudents() throws SQLException {
        // hente ut id til alle reg stud fra event
        List<Student> students = new ArrayList<>();
        List<Integer> registeredStudentIds = eventDao.getRegisteredStudentIds();
        for (Integer registeredStudentId : registeredStudentIds) {
            Optional<Student> possibleStudent = universityDao.getStudent(registeredStudentId);
            if(possibleStudent.isPresent()){
                students.add(possibleStudent.get());
            }
        }
        return students;
    }

    public List<Student> getAllRegisteredStudentsInProgram(int studyProgramId) throws SQLException {
        List<Integer> registeredStudentIds = eventDao.getRegisteredStudentIds();
        List<Student> students = new ArrayList<>();
        for (Integer registeredStudentId : registeredStudentIds) {
            Optional<Student> possibleStudent = universityDao.getStudent(registeredStudentId);
            if(possibleStudent.isPresent() && possibleStudent.get().getStudyProgram().id() == studyProgramId) {
                students.add(possibleStudent.get());
            }
        }
        return students;
    }

    public String getCeremonyProgram(Student student) throws SQLException {
        boolean studentRegistered = student!=null && eventDao.isStudentregisteredForEvent(student);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Introduction: 30 min. \n");
        List<Integer> registeredStudentIds = eventDao.getRegisteredStudentIds();
        List<StudyProgram> studyPrograms = universityDao.getStudyProgram();
        for (StudyProgram studyProgram : studyPrograms) {
            stringBuilder.append(studyProgram.name()).append(" - ").append(studyProgram.programResponsible().getName());
            stringBuilder.append(" - 1 minute+ \n");
            if(studentRegistered && student.getStudyProgram().id() == studyProgram.id()){
                stringBuilder.append("== You are registered as part of this program == \n");
            }
            int registeredStudentsInProgram = getNumberOfRegisteredStudentsInProgram(studyProgram.id(), registeredStudentIds);
            stringBuilder.append("\t").append(registeredStudentsInProgram).append(" student(s) - ");
            stringBuilder.append((1+registeredStudentsInProgram/5)).append(" minutes\n");
            stringBuilder.append("Break - 5 minutes\n");
        }
        stringBuilder.append("Closing remarks: 15 minutes\n");
        return  stringBuilder.toString();
    }

    private int getNumberOfRegisteredStudentsInProgram(int studyProgramId, List<Integer> registeredStudentIds) throws SQLException {
        List<Student> studentsInProgram = universityDao.getStudentsInStudyProgram(studyProgramId);
        int count = 0;
        for (Student student : studentsInProgram) {
            if (registeredStudentIds.contains(student.getId())){
                count++;
            }
        }
        return count;
    }
}
