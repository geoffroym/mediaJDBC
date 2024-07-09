import java.time.LocalDate;

public class Artist {
    private int id;
    private String artistName;
    private LocalDate dateOfBirth;
    private String city;
    private String country;

    public Artist(){
    }

    public Artist(int id, String artistName, LocalDate dateOfBirth, String city, String country) {
        this.id = id;
        this.artistName = artistName;
        this.dateOfBirth = dateOfBirth;
        this.city = city;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "id= '" + id  + '\'' +
                "artistName='" + artistName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    public void printString(){
        System.out.println(this.toString());
    }

}
