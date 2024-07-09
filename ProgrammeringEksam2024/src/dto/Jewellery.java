package dto;

public class Jewellery extends Artifacts {
    private int verdiEstimat;
    private String filNavn;

    public Jewellery(int antattAarstall, int finnerId, String funnSted, String funntidspunkt, int id, int museumId, String type, String filNavn, int verdiEstimat) {
        super(antattAarstall, finnerId, funnSted, funntidspunkt, id, museumId, type);
        this.filNavn = filNavn;
        this.verdiEstimat = verdiEstimat;
    }

    public String getFilNavn() {
        return filNavn;
    }

    public void setFilNavn(String filNavn) {
        this.filNavn = filNavn;
    }

    public int getVerdiEstimat() {
        return verdiEstimat;
    }

    public void setVerdiEstimat(int verdiEstimat) {
        this.verdiEstimat = verdiEstimat;
    }

    @Override
    public String toString() {
        return "Jewellery{" +
                "filNavn='" + filNavn + '\'' +
                ", verdiEstimat=" + verdiEstimat +
                "} " + super.toString();
    }
}
