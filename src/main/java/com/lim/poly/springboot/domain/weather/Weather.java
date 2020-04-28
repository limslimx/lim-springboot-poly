package com.lim.poly.springboot.domain.weather;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Weather {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weather_id")
    private Long id;

    private String today_weather;

    private String today_temperature;

    private String today_rain;

    private String tomorrow_weather;

    private String tomorrow_temperature;

    private String tomorrow_rain;

    @Builder
    public Weather(String today_weather, String today_temperature, String today_rain, String tomorrow_weather, String tomorrow_temperature, String tomorrow_rain){
        this.today_weather=today_weather;
        this.today_temperature=today_temperature;
        this.today_rain=today_rain;
        this.tomorrow_weather=tomorrow_weather;
        this.tomorrow_temperature=tomorrow_temperature;
        this.tomorrow_rain=tomorrow_rain;
    }
}
