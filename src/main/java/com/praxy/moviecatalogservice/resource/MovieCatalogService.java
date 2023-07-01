package com.praxy.moviecatalogservice.resource;

import com.praxy.moviecatalogservice.model.CatalogItem;
import com.praxy.moviecatalogservice.model.MoviePojo;
import com.praxy.moviecatalogservice.model.UserRating;
import com.praxy.moviecatalogservice.service.InfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("catalog")
@RequiredArgsConstructor
@Slf4j
public class MovieCatalogService {
    private final MoviePojo moviePojo;

    private final InfoService infoService;

    @GetMapping("/{userId}")
    public List<CatalogItem> getCatalogItems(@PathVariable("userId") String userId) {

        UserRating userRating = infoService.getUserRating(userId);

        return userRating.getRatings()
                .stream()
                .map(rating -> infoService.getCatalogItem(rating))
                .collect(Collectors.toList());
    }

    @GetMapping("/movie")
    public String getMovieDetail() {
        return moviePojo.getName() + moviePojo.getId() + moviePojo.getLanguage();
    }

}
