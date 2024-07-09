public class Haversine {

    private static double haversine (double val){
        return Math.pow(Math.sin(val / 2), 2);
    }
    public static double calculateDistance (double endLat, double endLng){
        int earth_radius = 6371;
        double startLat = 0;
        double startLng = 0;
        double dLat = Math.toRadians((endLat - startLat));
        double dLong = Math.toRadians((endLng - startLng));

        startLat = Math.toRadians(0);
        endLat = Math.toRadians(endLat);

        double a = haversine(dLat) + Math.cos(startLat) * Math.cos(endLat) * haversine(dLong);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return earth_radius * c;
    }


}
