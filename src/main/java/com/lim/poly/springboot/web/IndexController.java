package com.lim.poly.springboot.web;

import com.lim.poly.springboot.service.GetSetService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class IndexController {

    private final GetSetService getSetService;

    @GetMapping("/")
    public String index(){
        getSetService.test();
        return getSetService.test();
    }

}
