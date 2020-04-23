package com.lim.poly.springboot.domain.movie;

import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor
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

    @Builder
    public Movie(String movie_rank, String movie_name, String score, String open_day){
        this.movie_rank=movie_rank;
        this.movie_name=movie_name;
        this.score=score;
        this.open_day=open_day;
    }
}