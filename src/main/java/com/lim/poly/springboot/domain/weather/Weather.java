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

    private String weather;

    private String temperature;

    private String rain;

    private String search_time;

    private String msg;

    @Builder
    public Weather(String weather, String temperature, String rain, String search_time, String msg){
        this.weather = weather;
        this.temperature = temperature;
        this.rain = rain;
        this.search_time = search_time;
        this.msg = msg;
    }
}
