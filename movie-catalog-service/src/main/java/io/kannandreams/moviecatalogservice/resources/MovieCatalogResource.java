package io.kannandreams.moviecatalogservice.resources;

import com.netflix.discovery.DiscoveryClient;
import io.kannandreams.moviecatalogservice.models.CatalogItem;
import io.kannandreams.moviecatalogservice.models.Movie;
import io.kannandreams.moviecatalogservice.models.Rating;
import io.kannandreams.moviecatalogservice.models.UserRating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private WebClient.Builder webClientBuilder;

//  @Autowired
//  private DiscoveryClient discoveryClient;

  @RequestMapping("/{userId}")
  public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

//    UserRating ratings = restTemplate.getForObject("http://localhost:8083/ratingsdata/users/" + userId, UserRating.class);
    UserRating ratings = restTemplate.getForObject("http://ratings-data-service/ratingsdata/users/" + userId, UserRating.class);


    return ratings.getUserRating().stream().map(rating -> {
      // For each Movie Id , call movie info service and get details.
//      Movie movie = restTemplate.getForObject("http://localhost:8082/movies/" + rating.getMovieId(), Movie.class);
      Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);

      // Put them all together
        return new CatalogItem(movie.getName(),"test",rating.getRating());
    }).collect(Collectors.toList());
  }
}


//RestTemplate restTemplate = new RestTemplate();

    /*
    List<Rating> ratings = Arrays.asList(
            new Rating("Terminator",3),
            new Rating("Good Will Hunting",5),
            new Rating("Titanic",4)
    );
    */

// Using WebClient instead of RestTemplate
      /*
      Movie movie = webClientBuilder.build()
              .get()
              .uri("http://localhost:8082/movies/" + rating.getMovieId())
              .retrieve()
              .bodyToMono(Movie.class)
              .block();

      */