package io.javabrains.springbootstarter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    List<Place> getAllPlaces() {
        List<Place> places = new ArrayList<>();
        placeRepository.findAll().forEach(place -> places.add(place));
        return  places;
    }

    public void addPlace(Place place) {
        placeRepository.save(place);
    }


    public Place getPlaceById(String placeId) {
        return placeRepository.findOne(placeId);
    }

//    public List<Place>getPlacesWithinRadius(Integer radius, double latitude, double longitude) {
//
//
//        double earthRadius = 6371.01;
//        GeoLocation myLocation = GeoLocation.fromRadians(latitude, longitude);
//        try {
//
//            java.sql.ResultSet resultSet =  GeoLocationUtility.findPlacesWithinDistance(earthRadius, myLocation, radius);
//
//            List<Place> places = new ArrayList<Place>();
//
//            while(resultSet.next()) {
//                Place place = new Place();
//                place.setId(resultSet.getString("id"));
//                place.setDescription(resultSet.getString("description"));
//                place.setLatitude(resultSet.getDouble("latitude"));
//                place.setLongitude(resultSet.getDouble("longitude"));
//                places.add(place);
//            }
//            return places;
//        }
//        catch(SQLException e) {
//            e.printStackTrace();
//            return this.getAllPlaces();
//        }
//
//
//    }

}
