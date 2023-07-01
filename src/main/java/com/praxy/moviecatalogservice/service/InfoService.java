package com.praxy.moviecatalogservice.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.praxy.moviecatalogservice.model.CatalogItem;
import com.praxy.moviecatalogservice.model.Movie;
import com.praxy.moviecatalogservice.model.Rating;
import com.praxy.moviecatalogservice.model.UserRating;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class InfoService {

    private final RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFaultBackCatalogItems",
            commandProperties = {
                    @HystrixProperty(name = EXECUTION_ISOLATION_THREAD_TIMEOUT_IN_MILLISECONDS, value = "2000"),
                    @HystrixProperty(name = CIRCUIT_BREAKER_REQUEST_VOLUME_THRESHOLD, value = "5"),
                    @HystrixProperty(name = CIRCUIT_BREAKER_ERROR_THRESHOLD_PERCENTAGE, value = "50"),
                    @HystrixProperty(name = CIRCUIT_BREAKER_SLEEP_WINDOW_IN_MILLISECONDS, value = "5000")})
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://MOVIE-INFO-SERVICE/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    @HystrixCommand(fallbackMethod = "getDefaultUserRating")
    public UserRating getUserRating(String userId) {
        return restTemplate.getForObject("http://RATING-DATA-SERVICE/ratings/{userId}", UserRating.class, userId);
    }

    private CatalogItem getFaultBackCatalogItems(Rating rating) {
        log.info("fall back called");
        return new CatalogItem("default movie name", "dummy", 0);
    }

    private UserRating getDefaultUserRating(String userId) {
        log.info("fall back called for getDefaultUserRating");
        return new UserRating(Collections.singletonList(new Rating("0", 0)));
    }
}
