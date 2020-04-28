package com.lim.poly.springboot.domain.movie;

import com.lim.poly.springboot.web.dto.MovieDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("select new com.lim.poly.springboot.web.dto.MovieDto(m.movie_rank, m.movie_name, m.score, m.open_day, m.rank_check_time) from Movie m")
    List<MovieDto> findMovieDto();

}
