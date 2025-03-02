package com.meta.ponkids.global.util.generator;

import java.util.UUID;

public class OrderIdGenerator {
    public static String generatorOrderId(){
        // [ ORD-XXXXXXXXXXXXXXXX ] : 총 20자리 (숫자+영문자 구성)
        return "ORD-" + UUID.randomUUID().toString().replace( "-", "" ).toUpperCase().substring( 0, 16);
    }
}
