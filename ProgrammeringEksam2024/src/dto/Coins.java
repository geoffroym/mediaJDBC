package dto;

public class Coins extends Artifacts {

    private int diameter;
    private String metall;

    public Coins(int antattAarstall, int finnerId, String funnSted, String funntidspunkt, int id, int museumId, String type, int diameter, String metall) {
        super(antattAarstall, finnerId, funnSted, funntidspunkt, id, museumId, type);
        this.diameter = diameter;
        this.metall = metall;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public String getMetall() {
        return metall;
    }

    public void setMetall(String metall) {
        this.metall = metall;
    }

    @Override
    public String toString() {
        return "Coins{" +
                "diameter=" + diameter +
                ", metall='" + metall + '\'' +
                "} " + super.toString();
    }
}
