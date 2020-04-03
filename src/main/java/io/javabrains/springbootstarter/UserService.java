package io.javabrains.springbootstarter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PlaceRepository placeRepository;


    public User getUserForUsernameAndPassword(String username, String password) {

        List<User> users = new ArrayList<>();

        repository.findAll().forEach(user -> {
            if (user.username.equals(username) && user.password.equals(password)) {
                users.add(user);
            }
        });

        return users.get(0);
    }

    public List<Place> getPlacesOfUser(String userId) {
        List<Place> places = new ArrayList<>();

        for (User user : repository.findAll()) {
            if (user.userId.equals(userId)) {
                places = user.getFavouritePlaces();
            }
        }

        return places;

    }

    public void createUser(User user) {
        repository.save(user);
    }


    public User getUser(String userId) {
        return repository.findOne(userId);
    }

    public void savePlaceForUser(String userID, Place place) {
        User user = repository.findOne(userID);
        List<Place> favouritePlaces = user.getFavouritePlaces();
        favouritePlaces.add(place);
        user.setFavouritePlaces(favouritePlaces);
        place.setUser(user);
        repository.save(user);
    }


    public void deleteAllFavouritePlaces(String userId) {
        User user = repository.findOne(userId);
        List<Place> favouritePlaces = user.getFavouritePlaces();
        favouritePlaces.forEach(place -> placeRepository.delete(place));
        favouritePlaces.clear();
        user.setFavouritePlaces(favouritePlaces);
        repository.save(user);
    }

    public void deleteFavouritePlaceOfUser(String userId, Place inputPlace) {
        User user = repository.findOne(userId);
        List<Place> favouritePlaces = user.getFavouritePlaces();
        for (Place place : favouritePlaces) {
            if (place.getId().equals(inputPlace.getId())) {
                placeRepository.delete(place);
            }
        }
    }

    public List<Place> getPlacesWithinRadius(String userId, Double radius, Double latitude, Double longitude) {

        GeoLocation geoLocation = GeoLocation.fromRadians(latitude, longitude);

        try {

            java.sql.ResultSet resultSet =  GeoLocationUtility.findPlacesWithinDistance(userId, radius, geoLocation);;

            List<Place> places = new ArrayList<Place>();

            while(resultSet.next()) {
                Place place = new Place();
                place.setId(resultSet.getString("id"));
                place.setDescription(resultSet.getString("description"));
                place.setLatitude(resultSet.getDouble("latitude"));
                place.setLongitude(resultSet.getDouble("longitude"));
                place.setTitle(resultSet.getString("title"));
                places.add(place);
            }
            return places;
        }
        catch(SQLException e) {
            e.printStackTrace();
            return this.getPlacesOfUser(userId);
        }
    }
}
