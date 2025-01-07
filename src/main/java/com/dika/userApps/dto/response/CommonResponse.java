package com.dika.userApps.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponse<T> { //CommonResponse<T>  ini dibuat di controller saja biasanya
    private String message;
    private Integer statusCode;
    private T data;
}

