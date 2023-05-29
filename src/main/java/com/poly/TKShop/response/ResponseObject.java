package com.poly.TKShop.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseObject {
    private String status;
    private String message;
    private Object data;
}
