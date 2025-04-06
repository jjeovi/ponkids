package com.meta.ponkids.global.util.generator;

import java.util.UUID;

// This class is a copy of OrderIdGenerator.java
// The only difference is the class name and the file path
// 취소 관련 링크 : https://docs.tosspayments.com/guides/v2/cancel-payment
// 멱등키 : 멱등성은 연산을 여러 번 하더라도 결과가 달라지지 않는 성질을 뜻합니다. API 요청에서 멱등성을 보장하면 같은 요청이 여러 번 일어나도 항상 첫 번째 요청과 같은 결과가 돌아옵니다.
//        -> 멱등키 관련 링크 : https://docs.tosspayments.com/reference/using-api/authorization#%EB%A9%B1%EB%93%B1%ED%82%A4-%ED%97%A4%EB%8D%94
// 멱등키는 처음 요청에 사용한 날부터 15일간 유효합니다. 처음 요청한 날부터 15일이 지났다면 새로운 멱등키로 요청하세요.
public class IdempotencyKeyGenerator {
    public static String generatorIdempotencyKey(){
        // [ CANCEL-XXXXXXXXXXXXXXXX ] : 총 20자리 [ ORD-16자리랜덤 ] (숫자+영문자 구성)
        return "CANCEL-" + UUID.randomUUID().toString().replace( "-", "" ).toUpperCase().substring( 0, 16);
    }
    
    // 취소에 대한 내용
    /*
    *   카드	                취소 기한은 없지만, 카드사 별로 결제 데이터 보관 기간이 달라서 1년을 초과하면 취소가 안될 수 있어요.	    결제가 매입되기 전에는 취소 직후 환불됩니다. 매입 이후 또는 부분 취소는 요청 후 영업일 기준 3~4일이 소요됩니다.
        계좌이체	            180일 이내의 거래만 취소 가능합니다.	                                                    실시간으로 환불됩니다.
        가상계좌	            상점마다 설정이 다를 수 있으나 보통 365일 동안 취소가 가능합니다.	                                영입일 기준 총 2일이 소요됩니다. 의심거래로 탐지된 결제는 최대 영업일 기준 9일이 소요될 수 있습니다.
        휴대폰	            통신사 정책으로 결제가 발생한 당월에만 취소가 가능합니다.	                                        당일 취소됩니다.
        해외 간편결제(PayPal)	180일 이내의 거래만 취소 가능합니다.	                                                    영업일 기준 최대 5일이 소요됩니다. 일부 환불은 카드 회사에 따라 최대 30일이 소요될 수 있습니다.
    *
    * */
}
