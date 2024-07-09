package dto;

public class AllArtifacts {
    private int id;
    private Person person;
    private String funnSted;
    private String funntidspunkt;
    private int antattAarstall;
    private Museum museum;
    private String type;

    public AllArtifacts(int id, Person person, String funnSted, String funntidspunkt, int antattAarstall, Museum museum, String type) {
        this.antattAarstall = antattAarstall;
        this.funnSted = funnSted;
        this.funntidspunkt = funntidspunkt;
        this.id = id;
        this.museum = museum;
        this.person = person;
        this.type = type;
    }

    public int getAntattAarstall() {
        return antattAarstall;
    }

    public void setAntattAarstall(int antattAarstall) {
        this.antattAarstall = antattAarstall;
    }

    public String getFunnSted() {
        return funnSted;
    }

    public void setFunnSted(String funnSted) {
        this.funnSted = funnSted;
    }

    public String getFunntidspunkt() {
        return funntidspunkt;
    }

    public void setFunntidspunkt(String funntidspunkt) {
        this.funntidspunkt = funntidspunkt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Museum getMuseum() {
        return museum;
    }

    public void setMuseum(Museum museum) {
        this.museum = museum;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format(
                "Type: %s, Antatt årstall: %d - Person som funnet: %s - Funnsted: %s - Funn tidspunkt: %s - Museum navn: %s",
                type, antattAarstall, person.getNavn(), funnSted, funntidspunkt.toString(), museum.getNavn());
    }
}
