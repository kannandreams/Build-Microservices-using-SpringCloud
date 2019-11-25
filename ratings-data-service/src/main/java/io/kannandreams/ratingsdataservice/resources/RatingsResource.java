package io.kannandreams.ratingsdataservice.resources;

import io.kannandreams.ratingsdataservice.model.Rating;
import io.kannandreams.ratingsdataservice.model.UserRating;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsResource {

    @RequestMapping("/movies/{movieId}")
    public Rating getRating(@PathVariable("movieId") String movieId) {
      return new Rating(movieId, 4);
    }

    @RequestMapping("/user/{userId}")
    public UserRating getUserRatings(@PathVariable("userId") String userId) {
      UserRating userRating = new UserRating();
      userRating.initData(userId);
      return userRating;
  }
}