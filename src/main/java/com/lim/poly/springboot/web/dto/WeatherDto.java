package com.lim.poly.springboot.web.dto;

import com.lim.poly.springboot.domain.weather.Weather;
import lombok.*;

@NoArgsConstructor
@Data
public class WeatherDto {

    private String weather;

    private String temperature;

    private String rain;

    private String search_time;

    private String msg;

    @Builder
    public WeatherDto(String weather, String temperature, String rain, String search_time, String msg){
        this.weather = weather;
        this.temperature = temperature;
        this.rain = rain;
        this.search_time = search_time;
        this.msg = msg;
    }


    public Weather toEntity(){
        return Weather.builder()
                .weather(weather)
                .temperature(temperature)
                .rain(rain)
                .search_time(search_time)
                .msg(msg)
                .build();
    }

}
