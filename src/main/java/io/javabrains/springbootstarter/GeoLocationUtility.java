package io.javabrains.springbootstarter;
import java.sql.*;

public class GeoLocationUtility {


    public static java.sql.ResultSet findPlacesWithinDistance(String id, double radius, GeoLocation location) throws java.sql.SQLException {

        double earthRadius = 6371.01;

        GeoLocation[] boundingCoordinates =
                location.boundingCoordinates(radius, earthRadius);
        boolean meridian180WithinDistance =
                boundingCoordinates[0].getLongitudeInRadians() >
                        boundingCoordinates[1].getLongitudeInRadians();

        Connection connection =  DriverManager.getConnection("jdbc:h2:mem:testdb","sa","");

        java.sql.PreparedStatement statement = connection.prepareStatement(
                "SELECT * FROM Place WHERE (USER_ID = ?) AND (latitude >= ? AND latitude <= ?) AND (longitude >= ? " +
                        (meridian180WithinDistance ? "OR" : "AND") + " longitude <= ?) AND " +
                        "acos(sin(?) * sin(latitude) + cos(?) * cos(latitude) * cos(longitude - ?)) <= ?");
        statement.setString(1, id);
        statement.setDouble(2, boundingCoordinates[0].getLatitudeInRadians());
        statement.setDouble(3, boundingCoordinates[1].getLatitudeInRadians());
        statement.setDouble(4, boundingCoordinates[0].getLongitudeInRadians());
        statement.setDouble(5, boundingCoordinates[1].getLongitudeInRadians());
        statement.setDouble(6, location.getLatitudeInRadians());
        statement.setDouble(7, location.getLatitudeInRadians());
        statement.setDouble(8, location.getLongitudeInRadians());
        statement.setDouble(9, radius / earthRadius);
        return statement.executeQuery();

    }
}
