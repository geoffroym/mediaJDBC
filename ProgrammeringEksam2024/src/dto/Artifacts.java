package dto;

public abstract class Artifacts {
    private int id;
    private int finnerId;
    private String funnSted;
    private String  funntidspunkt;
    private int antattAarstall;
    private int museumId;
    private String type;

    public Artifacts(int antattAarstall, int finnerId, String funnSted, String funntidspunkt, int id, int museumId, String type) {
        this.antattAarstall = antattAarstall;
        this.finnerId = finnerId;
        this.funnSted = funnSted;
        this.funntidspunkt = funntidspunkt;
        this.id = id;
        this.museumId = museumId;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFunnSted() {
        return funnSted;
    }

    public void setFunnSted(String funnSted) {
        this.funnSted = funnSted;
    }

    public void setAntattAarstall(int antattAarstall) {
        this.antattAarstall = antattAarstall;
    }

    public int getFinnerId() {
        return finnerId;
    }

    public void setFinnerId(int finnerId) {
        this.finnerId = finnerId;
    }

    public String getFunntidspunkt() {
        return funntidspunkt;
    }

    public void setFunntidspunkt(String funntidspunkt) {
        this.funntidspunkt = funntidspunkt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getAntattAarstall() {
        return antattAarstall;
    }

    public void setAntattÅrstall(int antattÅrstall) {
        this.antattAarstall = antattÅrstall;
    }

    public int getMuseumId() {
        return museumId;
    }

    public void setMuseumId(int museumId) {
        this.museumId = museumId;
    }

    @Override
    public String toString() {
        return "Artifacts{" +
                "id=" + id +
                ", funnSted='" + funnSted + '\'' +
                ", finnerId='" + finnerId + '\'' +
                ", funntidspunkt=" + funntidspunkt +
                ", antattÅrstall=" + antattAarstall +
                ", museumId=" + museumId +
                '}';
    }
}
