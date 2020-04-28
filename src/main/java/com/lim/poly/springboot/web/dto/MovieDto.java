package com.lim.poly.springboot.web.dto;

import com.lim.poly.springboot.domain.movie.Movie;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class MovieDto {

    public String movie_rank;

    public String movie_name;

    public String score;

    public String open_day;

    public LocalDateTime rank_check_time;

    public MovieDto(String movie_rank, String movie_name, String score, String open_day, LocalDateTime rank_check_time) {
        this.movie_rank = movie_rank;
        this.movie_name = movie_name;
        this.score = score;
        this.open_day = open_day;
        this.rank_check_time=rank_check_time;
    }

    public Movie toEntity(){
        return Movie.builder()
                .movie_rank(movie_rank)
                .movie_name(movie_name)
                .score(score)
                .open_day(open_day)
                .rank_check_time(LocalDateTime.now())
                .build();
    }
}
