package io.javabrains.springbootstarter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlacesController {

    @Autowired
    PlaceService service;

    @RequestMapping("/hello")
    public String sayHi() {
        return "Hi";
    }

    @RequestMapping("/getPlaces")
    List<Place> getAllPlaces() {
       return service.getAllPlaces();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/places")
    public void addPlace(@RequestBody Place place) {
        service.addPlace(place);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/places/{placeId}")
    @ResponseBody
    public Place getPlace(@PathVariable String placeId) {
        return service.getPlaceById(placeId);
    }


//    @RequestMapping(method = RequestMethod.GET, value = "/places/{radius}/{latitude}/{longitude:.+}")
//    @ResponseBody
//    public List<Place> getPlacesWithinRadius(@PathVariable Integer radius, @PathVariable double latitude, @PathVariable("longitude") double longitude ) {
//        return service.getPlacesWithinRadius(radius, latitude, longitude);
//    }
}
