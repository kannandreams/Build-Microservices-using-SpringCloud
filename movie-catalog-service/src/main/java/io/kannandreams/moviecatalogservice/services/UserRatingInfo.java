package io.kannandreams.moviecatalogservice.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.kannandreams.moviecatalogservice.models.CatalogItem;
import io.kannandreams.moviecatalogservice.models.Movie;
import io.kannandreams.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserRatingInfo {

  @Autowired
  private RestTemplate restTemplate;

  @HystrixCommand(fallbackMethod = "getFallbackCatalogItem",
  threadPoolKey = "movieInfoPool",
  threadPoolProperties = {
          @HystrixProperty(name="coreSize",value="20"),
          @HystrixProperty(name="maxQueueSize",value="10")
  })
  public CatalogItem getCatalogItem(Rating rating) {
    Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
    return new CatalogItem(movie.getName(),movie.getDescription(),rating.getRating());
  }

  public CatalogItem getFallbackCatalogItem(Rating rating) {
    return new CatalogItem("Movie name not found","",rating.getRating());
  }

}
