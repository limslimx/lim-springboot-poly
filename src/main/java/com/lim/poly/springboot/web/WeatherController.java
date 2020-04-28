package com.lim.poly.springboot.web;

import com.lim.poly.springboot.domain.weather.Weather;
import com.lim.poly.springboot.service.movie.CrawlingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class WeatherController {

    private final CrawlingService crawlingService;

    @GetMapping("/weather")
    public List<Weather> tomorrowWeather() throws Exception{
        return crawlingService.getWeatherInfoAndSave();
    }

}
