package com.lim.poly.springboot.domain.movie;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "movie_info")
@Entity
public class Movie {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_id")
    private Long id;

    private String movie_rank;

    private String movie_name;

    private String score;

    private String open_day;

    private LocalDateTime rank_check_time;

    @Builder
    public Movie(String movie_rank, String movie_name, String score, String open_day, LocalDateTime rank_check_time){
        this.movie_rank=movie_rank;
        this.movie_name=movie_name;
        this.score=score;
        this.open_day=open_day;
        this.rank_check_time=rank_check_time;
    }
}
