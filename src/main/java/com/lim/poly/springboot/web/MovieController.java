package com.lim.poly.springboot.web;

import com.lim.poly.springboot.service.movie.CrawlingService;
import com.lim.poly.springboot.service.movie.TotalMovieService;
import com.lim.poly.springboot.util.CmmUtil;
import com.lim.poly.springboot.web.dto.MovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
public class MovieController {

    private Logger log = Logger.getLogger(String.valueOf(this.getClass()));
    private final TotalMovieService totalMovieService;
    private final CrawlingService crawlingService;

    @PostMapping("/rank/movie")
    public List<MovieDto> findMovieInfo(HttpServletRequest request, HttpServletResponse response) throws Exception{
        log.info(this.getClass().getName() + ".findMovieInfo start!");

        List<MovieDto> movieDtoList = null;
        String send_msg = CmmUtil.nvl(request.getParameter("send_msg"));
        log.info(send_msg);
        if (((send_msg.indexOf("영화") > -1) || (send_msg.indexOf("영하") > -1) || (send_msg.indexOf("연하") > -1) || (send_msg.indexOf("연화") > -1))  && ((send_msg.indexOf("순위") > -1) || (send_msg.indexOf("순이") > -1))) {
            MovieDto movieDto = new MovieDto();
            movieDtoList = totalMovieService.getMovieInfo(movieDto);
            log.info("movieDtoList.rank_check_time:"+movieDtoList.get(0).getRank_check_time());

            if(movieDtoList==null){
                movieDtoList = new ArrayList<MovieDto>();
            }
        }
        log.info(this.getClass().getName() + ".findMovieInfo end!");
        log.info("movieDtoList: "+movieDtoList);
        return movieDtoList;
    }
}
