package dto;

public class Weapon extends Artifacts {
    private String vaapenType;
    private String materiale;
    private int vekt;

    public Weapon(int antattAarstall, int finnerId, String funnSted, String funntidspunkt, int id, int museumId, String type, String materiale, String vaapenType, int vekt) {
        super(antattAarstall, finnerId, funnSted, funntidspunkt, id, museumId, type);
        this.materiale = materiale;
        this.vaapenType = vaapenType;
        this.vekt = vekt;
    }

    public String getMateriale() {
        return materiale;
    }

    public void setMateriale(String materiale) {
        this.materiale = materiale;
    }

    public int getVekt() {
        return vekt;
    }

    public void setVekt(int vekt) {
        this.vekt = vekt;
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "materiale='" + materiale + '\'' +
                ", vekt=" + vekt +
                "} " + super.toString();
    }
}
