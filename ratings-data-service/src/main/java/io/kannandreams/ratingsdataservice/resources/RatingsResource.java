package io.kannandreams.ratingsdataservice.resources;

import io.kannandreams.ratingsdataservice.model.Rating;
import io.kannandreams.ratingsdataservice.model.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
      return new Rating(movieId, 4);
    }

    @RequestMapping("users/{userId}")
    public UserRating getUserRating(@PathVariable("userId") String userId) {
      List<Rating> ratings = Arrays.asList(
              new Rating("Terminator",3),
              new Rating("Good Will Hunting",5),
              new Rating("Titanic",4)
      );
      UserRating userRating = new UserRating();
      userRating.setUserRating(ratings);
      return userRating;
  }
}