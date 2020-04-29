package com.lim.poly.springboot.web;

import com.lim.poly.springboot.domain.weather.Weather;
import com.lim.poly.springboot.service.CrawlingService;
import com.lim.poly.springboot.service.weather.WeatherService;
import com.lim.poly.springboot.util.CmmUtil;
import com.lim.poly.springboot.util.DateUtil;
import com.lim.poly.springboot.web.dto.WeatherDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@RequiredArgsConstructor
@RestController
public class WeatherController {

    private Logger log = Logger.getLogger(String.valueOf(this.getClass()));
    private final CrawlingService crawlingService;
    private final WeatherService weatherService;

    @PostMapping("/weather/info")
    public List<WeatherDto> weatherInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info(this.getClass().getName() + ".weatherInfo start!");

        List<WeatherDto> weatherDtoList=null;
        String send_msg = CmmUtil.nvl(request.getParameter("send_msg"));
        String day="";

        if ((send_msg.indexOf("오늘") > -1) || (send_msg.indexOf("오는") > -1)) {
            day = "TODAY";
        }else if((send_msg.indexOf("내일") > -1) || (send_msg.indexOf("레일") > -1)){
            day = "TOMORROW";
        }

        if (((send_msg.indexOf("날씨") > -1) || (send_msg.indexOf("날시") > -1)) && ((send_msg.indexOf("정보") > -1) || (send_msg.indexOf("전보") > -1))) {
            log.info("day="+day);
            WeatherDto weatherDto = new WeatherDto();
            weatherDto.setSearch_time(DateUtil.getDateTime("yyyyMMdd"));
            weatherDtoList = weatherService.getWeatherInfo(weatherDto, day);

            if (weatherDtoList == null) {
                weatherDtoList = new ArrayList<WeatherDto>();
            }
        }
        log.info(this.getClass().getName() + ".weatherInfo end!");
        return weatherDtoList;
    }

}
