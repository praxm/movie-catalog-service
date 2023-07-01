package com.praxy.moviecatalogservice.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties("movie")
public class MoviePojo {
    private String name;
    private String id;
    private String language;
}
