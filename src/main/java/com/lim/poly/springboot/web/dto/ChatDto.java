package com.lim.poly.springboot.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatDto {

    private String roomKey="";
    private String userNm="";
    private String msg="";
    private String dateTime="";

}
