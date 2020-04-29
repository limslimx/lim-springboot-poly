package com.lim.poly.springboot.service.weather;

import com.lim.poly.springboot.domain.weather.WeatherRepository;
import com.lim.poly.springboot.repository.weather.RedisWeatherRepository;
import com.lim.poly.springboot.service.CrawlingService;
import com.lim.poly.springboot.util.DateUtil;
import com.lim.poly.springboot.web.dto.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@Transactional
@Service
public class WeatherService {

    private Logger log = Logger.getLogger(String.valueOf(this.getClass()));
    private final WeatherRepository weatherRepository;
    private final RedisWeatherRepository redisWeatherRepository;
    private final CrawlingService crawlingService;

    public List<WeatherDto> getWeatherInfo(WeatherDto weatherDto, String day) throws Exception {
        log.info(this.getClass().getName() + ".getWeatherInfo start!");

        List<WeatherDto> weatherDtoList=null;
        String msg="";

        if (day == "TODAY") {
            msg = "today";
        } else if (day == "TOMORROW") {
            msg = "tomorrow";
        }

        String key = day+"_WEATHER_INFO_" + DateUtil.getDateTime("yyyyMMdd");

        if (redisWeatherRepository.getExists(key)) {
            weatherDtoList = redisWeatherRepository.getWeatherFromRedis(key);

            if (weatherDtoList == null) {
                weatherDtoList = new ArrayList<WeatherDto>();
            }
            redisWeatherRepository.setTimeOutHour(key, 1);
        }else{
            weatherDtoList = weatherRepository.getWeatherInfo(weatherDto.getSearch_time(), msg);

            if (weatherDtoList == null) {
                weatherDtoList = new ArrayList<WeatherDto>();
            }
            if (weatherDtoList.size() == 0) {
                crawlingService.getWeatherInfoAndSave(day);
                weatherDtoList = weatherRepository.getWeatherInfo(weatherDto.getSearch_time(), msg);

                if (weatherDtoList == null) {
                    weatherDtoList = new ArrayList<WeatherDto>();
                }
            }
            if (weatherDtoList.size() > 0) {
                redisWeatherRepository.setWeatherToRedis(key, weatherDtoList);
                redisWeatherRepository.setTimeOutHour(key, 1);
            }
        }
        log.info(this.getClass().getName() + ".getWeatherInfo end!");
        return weatherDtoList;
    }
}
