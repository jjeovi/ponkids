package com.meta.ponkids.global.util.generator;

import java.util.UUID;

public class CustomerKeyGenerator {
    public static String generatorCustomerKey(){
        // [ CST-XXXXXXXXXXXXXXXX ] : 총 20자리 (숫자+영문자 구성)
        return "CST-" + UUID.randomUUID().toString().replace( "-", "" ).toUpperCase().substring( 0, 16);
    }
}
