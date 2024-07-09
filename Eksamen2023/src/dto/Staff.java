package dto;

public class Staff extends Person {
    public Staff(int id, String name) {
        super(id, name);
    }

    @Override
    public String toString() {
        return "Staff{} " + super.toString();
    }
}
