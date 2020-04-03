package io.javabrains.springbootstarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController  {

    @Autowired
    UserService service;

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}/places")
    public List<Place> getAllPlaces(@PathVariable String userId) {
        return service.getPlacesOfUser(userId);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/user")
    public void  createUser(@RequestBody User user) {
           service.createUser(user);
    };

    @RequestMapping(method = RequestMethod.GET, value = "/user/{userId}")
    public User getUser(String userId) {
        return service.getUser(userId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{username}/{password}")
    public User getUser(@PathVariable String username, @PathVariable String password) {
        return service.getUserForUsernameAndPassword(username, password);
    }

    @RequestMapping(method = RequestMethod.POST, value = "user/{userId}/place")
    public void savePlaceForUser(@PathVariable String userId, @RequestBody Place place) {
        service.savePlaceForUser(userId, place);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "user/{userId}/deleteAllFavourites")
    public void deleteAllFavourites(@PathVariable String userId) {
        service.deleteAllFavouritePlaces(userId);
    }

    @RequestMapping(method = RequestMethod.PATCH, value = "user/{userId}/deletePlace")
    public void deletePlaceForUser(@PathVariable String userId, @RequestBody Place place) {
        service.deleteFavouritePlaceOfUser(userId, place);
    }

    @RequestMapping(method = RequestMethod.GET, value = "user/{userId}/radius/{radius}/latitude/{latitude}/longitude/{longitude}/places")
    public List<Place> getPlacesWithinRadius(@PathVariable String userId, @PathVariable Double radius, @PathVariable Double latitude, @PathVariable Double longitude) {
        return service.getPlacesWithinRadius(userId, radius, latitude, longitude);
    }
}
