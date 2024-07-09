package dto;

import java.util.Objects;

public class Guest extends Person{
    private Student registeredStudent;

    public Guest(int id, String name) {
        super(id, name);
    }
    public Guest(String name, Student registeredStudent){
        this(0, name, registeredStudent);
    }
    public Guest(int id, String name, Student registeredStudent){
        super(id, name);
        this.registeredStudent = registeredStudent;
    }

    public Student getRegisteredStudent() {
        return registeredStudent;
    }

    public void setRegisteredStudent(Student registeredStudent) {
        this.registeredStudent = registeredStudent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return Objects.equals(registeredStudent, guest.registeredStudent);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(registeredStudent);
    }

    @Override
    public String toString() {
        return "Guest{" +
                "registeredStudent=" + registeredStudent +
                "} " + super.toString();
    }
}
