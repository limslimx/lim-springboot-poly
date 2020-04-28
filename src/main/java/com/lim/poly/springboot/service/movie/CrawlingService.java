package com.lim.poly.springboot.service.movie;

import com.lim.poly.springboot.domain.movie.Movie;
import com.lim.poly.springboot.domain.movie.MovieRepository;
import com.lim.poly.springboot.domain.weather.Weather;
import com.lim.poly.springboot.domain.weather.WeatherRepository;
import com.lim.poly.springboot.util.CmmUtil;
import com.lim.poly.springboot.web.dto.MovieDto;
import com.lim.poly.springboot.web.dto.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Transactional
@Service
public class CrawlingService {

    private final Logger log=Logger.getLogger(String.valueOf(this.getClass()));
    private final MovieRepository movieRepository;
    private final WeatherRepository weatherRepository;

    private String url;
    private Document doc;


    public List<Movie> getMovieInfoAndSave() throws Exception {
        log.info(this.getClass().getName() + ".getMovieInfo start!");

        url = "http://www.cgv.co.kr/movies/";
        doc = Jsoup.connect(url).get();
        Elements element = doc.select("div.sect-movie-chart");

        Iterator<Element> movie_rank = element.select("strong.rank").iterator();
        Iterator<Element> movie_name = element.select("strong.title").iterator();
        Iterator<Element> score = element.select("span.percent").iterator();
        Iterator<Element> open_day = element.select("span.txt-info").iterator();

        Movie movie = null;
        MovieDto movieDto = null;

        while (movie_rank.hasNext()) {
            movieDto = new MovieDto();

            String rank = CmmUtil.nvl(movie_rank.next().text()).trim();

            movieDto.setMovie_rank(rank.substring(3, rank.length()));
            movieDto.setMovie_name(CmmUtil.nvl((movie_name.next().text()).trim()));
            movieDto.setScore(CmmUtil.nvl(score.next().text()).trim());
            movieDto.setOpen_day(CmmUtil.nvl(open_day.next().text()).trim().substring(0, 10));

            movie = movieDto.toEntity();
            movieRepository.save(movie);
        }
        log.info(this.getClass().getName() + ".getMovieInfo end!");

        return movieRepository.findAll();
    }

    public List<Weather> getWeatherInfoAndSave() throws Exception {
        log.info(this.getClass().getName() + ".getWeatherInfoAndSave start!");

        url = "https://weather.naver.com/";
        doc = Jsoup.connect(url).get();
        Elements todayElements = doc.select("div.m_zone1 table.m_tbl tr:nth-child(3)");
        Elements today = todayElements.select("td.info");
        String today_weather = today.text().substring(0, 2);
        String today_temparature=today.text().substring(6, 11);
        String today_rain=today.text().substring(17, today.text().length());

        Elements tomorrowElements = doc.select("div.m_zone1 table.m_tbl tr:nth-child(5)");
        Elements tomorrow = tomorrowElements.select("td.info");
        String tomorrow_weather = tomorrow.text().substring(0, 4);
        String tomorrow_temperature = tomorrow.text().substring(8, 13);
        String tomorrow_rain = tomorrow.text().substring(19, tomorrow.text().length());

        WeatherDto weatherDto=WeatherDto.builder()
                .today_weather(today_weather)
                .today_temperature(today_temparature)
                .today_rain(today_rain)
                .tomorrow_weather(tomorrow_weather)
                .tomorrow_temperature(tomorrow_temperature)
                .tomorrow_rain(tomorrow_rain)
                .build();

        Weather weather = weatherDto.toEntity();
        weatherRepository.save(weather);
        log.info(this.getClass().getName() + ".getWeatherInfoAndSave end!");

        return weatherRepository.findAll();
    }
}
