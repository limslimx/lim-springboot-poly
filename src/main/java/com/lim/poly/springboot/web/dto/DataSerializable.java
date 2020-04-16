package com.lim.poly.springboot.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;

@Getter @Setter
@NoArgsConstructor
public class DataSerializable implements Serializable {

    private static final long serialVersionUID=1L;
    private String sourceId;
    private String itemId;

    public DataSerializable(String sourceId, String itemId){
        super();
        this.sourceId=sourceId;
        this.itemId=itemId;
    }

    @Override
    public String toString(){
        return "DataType [sourceId="+sourceId +", itemId="+itemId+"]";
    }


}
