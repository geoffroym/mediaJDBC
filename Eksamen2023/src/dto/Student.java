package dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Student extends Person {

    private StudyProgram studyProgram;
    private Set<Guest> guests;

    public Student(int id, String name) {
        super(id, name);
    }

    public Student(int id, String name, StudyProgram studyProgram) {
        this(id, name, studyProgram, new HashSet<>());
    }

    public Student(int id, String name, StudyProgram studyProgram, Set<Guest> guests) {
        super(id, name);
        this.studyProgram = studyProgram;
        this.guests = guests;
    }

    public Set<Guest> getGuests() {
        return guests;
    }

    public void setGuests(Set<Guest> guests) {
        this.guests = guests;
    }

    public StudyProgram getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(StudyProgram studyProgram) {
        this.studyProgram = studyProgram;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(studyProgram, student.studyProgram);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(studyProgram);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studyProgram=" + studyProgram +
                "} " + super.toString();
    }
}
