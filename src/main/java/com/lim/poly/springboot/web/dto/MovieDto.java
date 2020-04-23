package com.lim.poly.springboot.web.dto;

import com.lim.poly.springboot.domain.movie.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class MovieDto {

    public String movie_rank;

    public String movie_name;

    public String score;

    public String open_day;

    public Movie toEntity(){
        return Movie.builder()
                .movie_rank(movie_rank)
                .movie_name(movie_name)
                .score(score)
                .open_day(open_day)
                .build();
    }
}
