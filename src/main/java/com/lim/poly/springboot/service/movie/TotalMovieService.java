package com.lim.poly.springboot.service.movie;

import com.lim.poly.springboot.domain.movie.MovieRepository;
import com.lim.poly.springboot.repository.movie.RedisMovieRepository;
import com.lim.poly.springboot.util.DateUtil;
import com.lim.poly.springboot.web.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class TotalMovieService {

    private Logger log = Logger.getLogger(String.valueOf(this.getClass()));
    private final MovieCrawlingService movieCrawlingService;
    private final MovieRepository movieRepository;
    private final RedisMovieRepository redisMovieRepository;

    public List<MovieDto> getMovieInfo(MovieDto movieDto) throws Exception{
        log.info(this.getClass().getName() + ".getMovieInfo start!");

        List<MovieDto> movieDtoList=null;

        String key = "CGV_RANK_" + DateUtil.getDateTime("yyyyMMdd");

        if(redisMovieRepository.getExists(key)){
            movieDtoList=redisMovieRepository.getMovieRank(key);

            if(movieDtoList==null){
                movieDtoList = new ArrayList<MovieDto>();
            }
            redisMovieRepository.setTimeOutHour(key, 1);
        }else {
            movieDtoList=movieRepository.findMovieDto();

            if(movieDtoList==null){
                movieDtoList = new ArrayList<MovieDto>();
            }
            if (movieDtoList.size() == 0) {
                movieCrawlingService.getMovieInfoAndSave();
                movieDtoList = movieRepository.findMovieDto();

                if (movieDtoList == null) {
                    movieDtoList = new ArrayList<MovieDto>();
                }
            }
            if (movieDtoList.size() > 0) {
                redisMovieRepository.setMovieRank(key, movieDtoList);
                redisMovieRepository.setTimeOutHour(key, 1);
            }
        }
        log.info(this.getClass().getName() + ".getMovieInfo end!");
        return movieDtoList;
    }
}
