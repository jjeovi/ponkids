package com.meta.ponkids.domain.payment;

/**
 * 결제 상태를 나타내는 Enum 클래스.
 * 각 상태는 결제 프로세스의 특정 단계를 나타냅니다.
 */
public enum PaymentStatus {
    
    /**
     * PENDING: 결제 전 내부적으로 최초 생성된 상태입니다. 승인요청 이전의 상태입니다.
     */
    PENDING,// 결제 전 내부적으로 최초 생성된 상태입니다. 승인요청 이전의 상태입니다.
    
    /**
     * READY: 결제를 생성하면 가지게 되는 초기 상태입니다. 인증 전까지는 READY 상태를 유지합니다.
     */
    READY,                  // 결제를 생성하면 가지게 되는 초기 상태입니다. 인증 전까지는 READY 상태를 유지합니다.
    
    /**
     * IN_PROGRESS: 결제수단 정보와 해당 결제수단의 소유자가 맞는지 인증을 마친 상태입니다. 결제 승인 API를 호출하면 결제가 완료됩니다.
     */
    IN_PROGRESS,            // 결제수단 정보와 해당 결제수단의 소유자가 맞는지 인증을 마친 상태입니다. 결제 승인 API를 호출하면 결제가 완료됩니다.
    
    /**
     * WAITING_FOR_DEPOSIT: 가상계좌 결제 흐름에만 있는 상태입니다. 발급된 가상계좌에 구매자가 아직 입금하지 않은 상태입니다.
     */
    WAITING_FOR_DEPOSIT,    // 가상계좌 결제 흐름에만 있는 상태입니다. 발급된 가상계좌에 구매자가 아직 입금하지 않은 상태입니다.
    
    /**
     * DONE: 인증된 결제수단으로 요청한 결제가 승인된 상태입니다.
     */
    DONE,                   // 인증된 결제수단으로 요청한 결제가 승인된 상태입니다.
    
    /**
     * CANCELED: 승인된 결제가 취소된 상태입니다.
     */
    CANCELED,               // 승인된 결제가 취소된 상태입니다.
    
    /**
     * PARTIAL_CANCELED: 승인된 결제가 부분 취소된 상태입니다.
     */
    PARTIAL_CANCELED,       // 승인된 결제가 부분 취소된 상태입니다.
    
    /**
     * ABORTED: 결제 승인이 실패한 상태입니다.
     */
    ABORTED,                // 결제 승인이 실패한 상태입니다.
    
    /**
     * EXPIRED: 결제 유효 시간 30분이 지나 거래가 취소된 상태입니다. IN_PROGRESS 상태에서 결제 승인 API를 호출하지 않으면 EXPIRED가 됩니다.
     */
    EXPIRED;                 // 결제 유효 시간 30분이 지나 거래가 취소된 상태입니다. IN_PROGRESS 상태에서 결제 승인 API를 호출하지 않으면 EXPIRED가 됩니다.
    
    
    

    /**
     * 문자열 값을 Enum 으로 변환하는 메소드
     *
     * @param value Enum 으로 변환할 문자열 값
     * @return Enum 으로 변환된 값
     */
    public static PaymentStatus fromString( String value ) {
        try {
            return value != null ? PaymentStatus.valueOf( value ) : null;
        } catch ( IllegalArgumentException e ) {
            return null; // 잘못된 값이 들어오면 null 반환
        }
    }
    
    }