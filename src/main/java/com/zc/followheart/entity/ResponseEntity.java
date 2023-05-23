package com.zc.followheart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseEntity<T> {
    private int code;

    private String message;

    private T data;


    public static <T> ResponseEntity<T> success(T data){
        return new ResponseEntity<>(20000,"success",  data);
    }

    public static <T> ResponseEntity<T> err(String msg){
        return new ResponseEntity(9999,msg,  "");
    }
}
