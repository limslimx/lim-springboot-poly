package com.lim.poly.springboot.web;

import com.lim.poly.springboot.domain.movie.Movie;
import com.lim.poly.springboot.service.movie.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
public class MovieController {

    private Logger log=Logger.getLogger(String.valueOf(this.getClass()));
    private final MovieService movieService;

    @GetMapping("/movie")
    public List<Movie> saveMovieInfo() throws Exception{
        log.info(this.getClass().getName()+".saveMovieInfo start!");
        log.info(this.getClass().getName()+".saveMovieInfo end!");
        return movieService.saveMovieInfo();
    }
}
