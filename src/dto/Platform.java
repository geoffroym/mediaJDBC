package dto;

public class Platform{
    int platformId;
    String platformName;

    public Platform(int platformId, String platformName) {
        this.platformId = platformId;
        this.platformName = platformName;
    }

    public Platform(String platformName){
        this.platformName = platformName;
    }

    public int getPlatformId() {
        return platformId;
    }

    public void setPlatformId(int platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    @Override
    public String toString() {
        return "Platform{" +
                "platformId=" + platformId +
                ", platformName='" + platformName + '\'' +
                '}';
    }
}
