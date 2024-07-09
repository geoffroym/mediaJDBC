package dto;

public class Museum{
int id;
String navn;
String sted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNavn() {
        return navn;
    }

    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getSted() {
        return sted;
    }

    public void setSted(String sted) {
        this.sted = sted;
    }

    @Override
    public String toString() {
        return "Museum{" +
                "id=" + id +
                ", navn='" + navn + '\'' +
                ", sted='" + sted + '\'' +
                '}';
    }

    public Museum(int id, String navn, String sted) {
        this.id = id;
        this.navn = navn;
        this.sted = sted;
    }

    public Museum(int id, String navn){
        this.id = id;
        this.navn = navn;
    }
}

