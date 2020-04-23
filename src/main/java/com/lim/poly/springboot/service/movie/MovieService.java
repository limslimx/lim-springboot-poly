package com.lim.poly.springboot.service.movie;

import com.lim.poly.springboot.domain.movie.Movie;
import com.lim.poly.springboot.domain.movie.MovieRepository;
import com.lim.poly.springboot.util.CmmUtil;
import com.lim.poly.springboot.web.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Transactional
@Service
public class MovieService {

    private final Logger log=Logger.getLogger(String.valueOf(this.getClass()));
    private final MovieRepository movieRepository;

    public List<Movie> saveMovieInfo() throws Exception{
        log.info(this.getClass().getName()+".getMovieInfo start!");

        String url="http://www.cgv.co.kr/movies/";
        Document doc=null;
        doc= Jsoup.connect(url).get();
        Elements element=doc.select("div.sect-movie-chart");

        Iterator<Element> movie_rank=element.select("strong.rank").iterator();
        Iterator<Element> movie_name=element.select("strong.title").iterator();
        Iterator<Element> score=element.select("span.percent").iterator();
        Iterator<Element> open_day=element.select("span.txt-info").iterator();

        Movie movie=null;
        MovieDto movieDto=null;

        while(movie_rank.hasNext()){
            movieDto=new MovieDto();

            String rank= CmmUtil.nvl(movie_rank.next().text()).trim();

//            movie.builder()
//                    .movie_rank(rank.substring(3, rank.length()))
//                    .movie_name(CmmUtil.nvl((movie_name.next().text()).trim()))
//                    .score(CmmUtil.nvl(score.next().text()).trim())
//                    .open_day(CmmUtil.nvl(open_day.next().text()).trim().substring(0,10))
//                    .build();

            movieDto.setMovie_rank(rank.substring(3, rank.length()));
            movieDto.setMovie_name(CmmUtil.nvl((movie_name.next().text()).trim()));
            movieDto.setScore(CmmUtil.nvl(score.next().text()).trim());
            movieDto.setOpen_day(CmmUtil.nvl(open_day.next().text()).trim().substring(0,10));

            movie=movieDto.toEntity();
            movieRepository.save(movie);
        }
        log.info(this.getClass().getName()+".getMovieInfo end!");

        return movieRepository.findAll();
    }


}
