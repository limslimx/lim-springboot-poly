package com.lim.poly.springboot.domain.weather;

import com.lim.poly.springboot.web.dto.WeatherDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WeatherRepository extends JpaRepository<Weather, Long> {

    @Query("select new com.lim.poly.springboot.web.dto.WeatherDto(w.weather, w.temperature, w.rain, w.search_time, w.msg) from Weather w where w.search_time=:search_time and w.msg=:msg")
    List<WeatherDto> getWeatherInfo(@Param("search_time") String searchTime, @Param("msg") String msg);
}
