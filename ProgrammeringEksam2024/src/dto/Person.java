package dto;

public class Person {
    private int id;
    private String navn;
    private String tlf;
    private String e_post;

    public Person(int id, String navn, String tlf, String e_post) {
        this.e_post = e_post;
        this.id = id;
        this.navn = navn;
        this.tlf = tlf;
    }

    public Person(int id, String navn){
        this.id = id;
        this.navn = navn;
    }

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

    public String getTlf() {
        return tlf;
    }

    public void setTlf(String tlf) {
        this.tlf = tlf;
    }

    public String getE_post() {
        return e_post;
    }

    public void setE_post(String e_post) {
        this.e_post = e_post;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", navn='" + navn + '\'' +
                ", tlf='" + tlf + '\'' +
                ", e_post='" + e_post + '\'' +
                '}';
    }
}
