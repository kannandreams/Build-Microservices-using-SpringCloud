package io.kannandreams.moviecatalogservice.resources;

import com.netflix.discovery.DiscoveryClient;
import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.kannandreams.moviecatalogservice.models.CatalogItem;
import io.kannandreams.moviecatalogservice.models.Movie;
import io.kannandreams.moviecatalogservice.models.Rating;
import io.kannandreams.moviecatalogservice.models.UserRating;
import io.kannandreams.moviecatalogservice.services.MovieInfo;
import io.kannandreams.moviecatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
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
  MovieInfo movieInfo;

  @Autowired
  UserRatingInfo userRatingInfo;

//  @Autowired
//  private WebClient.Builder webClientBuilder;

  @RequestMapping("/{userId}")
  public List<CatalogItem> getCatalog(@PathVariable("userId") String userId) {

    UserRating userRating = movieInfo.getUserRating(userId);

    return userRating.getRatings().stream()
            .map(rating -> userRatingInfo.getCatalogItem(rating))
            .collect(Collectors.toList());
  }

}

/*
Alternative WebClient way
Movie movie = webClientBuilder.build().get().uri("http://localhost:8082/movies/"+ rating.getMovieId())
.retrieve().bodyToMono(Movie.class).block();
*/