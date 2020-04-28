package com.lim.poly.springboot.web.dto;

import com.lim.poly.springboot.domain.weather.Weather;
import lombok.*;

@Data
public class WeatherDto {

    private String today_weather;

    private String today_temperature;

    private String today_rain;

    private String tomorrow_weather;

    private String tomorrow_temperature;

    private String tomorrow_rain;

    @Builder
    public WeatherDto(String today_weather, String today_temperature, String today_rain, String tomorrow_weather, String tomorrow_temperature, String tomorrow_rain){
        this.today_weather = today_weather;
        this.today_temperature = today_temperature;
        this.today_rain = today_rain;
        this.tomorrow_weather = tomorrow_weather;
        this.tomorrow_temperature = tomorrow_temperature;
        this.tomorrow_rain = tomorrow_rain;
    }


    public Weather toEntity(){
        return Weather.builder()
                .today_weather(today_weather)
                .today_temperature(today_temperature)
                .today_rain(today_rain)
                .tomorrow_weather(tomorrow_weather)
                .tomorrow_temperature(tomorrow_temperature)
                .tomorrow_rain(tomorrow_rain)
                .build();
    }

}
