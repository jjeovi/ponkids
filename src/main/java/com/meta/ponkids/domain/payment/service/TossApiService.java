package com.meta.ponkids.domain.payment.service;

import com.meta.ponkids.domain.payment.dto.TossApprReqDto;
import com.meta.ponkids.domain.payment.dto.TossCancelReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Service
@RequiredArgsConstructor
public class TossApiService {
    
    @Value( "${key.tossPayments.baseUrl}" )
    private String BASE_URL;
    
    @Value( "${key.tossPayments.secretKey}" )
    private String SECRET_KEY;
    
    
    
    private String confirmUrlPath;
    /**
     * @PostConstruct를 사용하여 초기화
     */
    @PostConstruct
    public void confirmUrlInit() {
        this.confirmUrlPath = BASE_URL + "/v1/payments/confirm";   // + {paymentKey}/cancel
    }
    
    public String getConfirmUrlPath() {
        return confirmUrlPath;
    }
    
    
    private String cancelUrlPath;
    /**
     * @PostConstruct를 사용하여 초기화
     */
    
    @PostConstruct
    public void cancelUrlInit() {
        this.cancelUrlPath = BASE_URL + "/v1/payments/";   // + {paymentKey}/cancel
    }
    
    public String getCancelUrlPath() {
        return cancelUrlPath;
    }
    
    /**
     * 토스 결제 승인을 요청하는 메서드입니다.
     *
     * @param tossApprReqDto 결제 승인 요청에 필요한 데이터가 담긴 DTO
     * @return Map<String, Object> 요청 처리 결과 (응답 코드, 성공 여부, 응답 JSON 데이터)
     *          - "code"        : HTTP 응답 코드 (200이면 성공, 그 외의 코드는 오류)
     *          - "isSuccess"   : 요청 처리 성공 여부 (성공 시 true, 실패 시 false)
     *          - "resultJson"  : 토스 API 응답으로 받은 JSON 데이터 (결제 취소 성공 시 해당 데이터 반환)
     * @throws Exception 요청 처리 중 발생할 수 있는 예외
     */
    public Map<String, Object> requestTossPayment( TossApprReqDto tossApprReqDto ) throws Exception {
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 1 JSON 데이터 생성
            JSONParser parser = new JSONParser();
            JSONObject obj = new JSONObject();
            
            // 요청 데이터 setting
            obj.put( "orderId",     tossApprReqDto.getOrderId()     );
            obj.put( "amount",      tossApprReqDto.getAmount()      );
            obj.put( "paymentKey",  tossApprReqDto.getPaymentKey()  );
            
            // 2 토스페이먼츠 API 인증 헤더 생성
            // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다. 비밀번호❌가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
            // @docs https://docs.tosspayments.com/reference/using-api/authorization#%EC%9D%B8%EC%A6%9D
            Base64.Encoder encoder = Base64.getEncoder();
            byte[] encodedBytes = encoder.encode( ( SECRET_KEY + ":" ).getBytes( StandardCharsets.UTF_8 ) );
            String authorizations = "Basic " + new String( encodedBytes );
            
            // 3 HTTP 연결 설정
            // 결제 승인 API를 호출하세요. 결제를 승인하면 결제수단에서 금액이 차감돼요.
            // @docs https://docs.tosspayments.com/guides/payment-widget/integration#3-결제-승인하기
            URL url = new URL( getConfirmUrlPath() );   // getConfirmUrlPath() : https://api.tosspayments.com/v1/payments/confirm
            HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
            connection.setRequestProperty( "Authorization", authorizations );
            connection.setRequestProperty( "Content-Type", "application/json" );
            connection.setRequestMethod( "POST" );
            connection.setDoOutput( true );
            
            // 4 요청 데이터 전송
            OutputStream outputStream = connection.getOutputStream();
            outputStream.write( obj.toString().getBytes( "UTF-8" ) );
            outputStream.flush();  // 버퍼 비우기 (데이터 전송)
            outputStream.close();  // 스트림 닫기 (리소스 해제)
            
            // 5 응답 수신
            int code = connection.getResponseCode();
            boolean isSuccess = ( code == 200 );
            
            InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();
            Reader reader = new InputStreamReader( responseStream, StandardCharsets.UTF_8 );
            JSONObject resultJson = ( JSONObject ) parser.parse( reader );
            responseStream.close();
            
            result.put( "code", code );
            result.put( "isSuccess", isSuccess );
            result.put( "resultJson", resultJson );
            
            return result;
            
        } catch ( Exception e ) {
            // 결제 승인 요청 중 오류 발생 시 처리
            // ex) 네트워크 오류 (예: 인터넷 연결 문제),
            // 잘못된 API URL 또는 API 키 오류,
            // API 요청 데이터 오류 (400 Bad Request),
            // 서버 오류 (500 Internal Server Error),
            // JSON 응답 파싱 오류 (JsonProcessingException)
            System.err.println( "Payment approval failed: " + e.getMessage() );
            log.error( "Payment approval failed: " + e.getMessage() );
            // TODO
            
            return null;
        }
    }
    
    /**
     * 토스 결제 취소를 요청하는 메서드입니다.
     *
     * @param tossCancelReqDto 결제 취소 요청에 필요한 데이터가 담긴 DTO
     * @return Map<String, Object> 요청 처리 결과 (응답 코드, 성공 여부, 응답 JSON 데이터)
     *          - "code"        : HTTP 응답 코드 (200이면 성공, 그 외의 코드는 오류)
     *          - "isSuccess"   : 요청 처리 성공 여부 (성공 시 true, 실패 시 false)
     *          - "resultJson"  : 토스 API 응답으로 받은 JSON 데이터 (결제 취소 성공 시 해당 데이터 반환)
     * @throws Exception 요청 처리 중 발생할 수 있는 예외
     */
    public Map<String, Object> requestTossCancel( TossCancelReqDto tossCancelReqDto ) throws Exception {
        Map<String, Object> result = new HashMap<>();
        
        // 1 JSON 데이터 생성
        JSONParser parser = new JSONParser();
        JSONObject obj = new JSONObject();
        obj.put( "cancelReason", tossCancelReqDto.getCancelReason() );
        obj.put( "cancelAmount", tossCancelReqDto.getCancelAmount() );
        
        // 2 토스페이먼츠 API 인증 헤더 생성
        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다. 비밀번호❌가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        // @docs https://docs.tosspayments.com/reference/using-api/authorization#%EC%9D%B8%EC%A6%9D
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode( ( SECRET_KEY + ":" ).getBytes( StandardCharsets.UTF_8 ) );
        String authorizations = "Basic " + new String( encodedBytes );
        
        // 3 HTTP 연결 설정
        // 결제 취소 API를 호출하세요.
        // @docs https://docs.tosspayments.com/reference/test/v1/payments/%7BpaymentKey%7D/cancel/POST
        String requestUrl = getCancelUrlPath() + tossCancelReqDto.getPaymentKey() + "/cancel";
        URL url = new URL( requestUrl );   // getApproveUrlPath() : https://api.tosspayments.com/v1/payments/{paymentKey}/cancel
        HttpURLConnection connection = ( HttpURLConnection ) url.openConnection();
        connection.setRequestProperty( "Authorization", authorizations );
        connection.setRequestProperty( "Content-Type", "application/json" );
        connection.setRequestMethod( "POST" );
        connection.setDoOutput( true );
        
        // 4 요청 데이터 전송
        OutputStream outputStream = connection.getOutputStream();
        outputStream.write( obj.toString().getBytes( "UTF-8" ) );
        outputStream.flush();  // 버퍼 비우기 (데이터 전송)
        outputStream.close();  // 스트림 닫기 (리소스 해제)
        
        // 5 응답 수신
        int code = connection.getResponseCode();
        boolean isSuccess = ( code == 200 );
        
        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();
        Reader reader = new InputStreamReader( responseStream, StandardCharsets.UTF_8 );
        JSONObject resultJson = ( JSONObject ) parser.parse( reader );
        responseStream.close();
        
        result.put( "code", code );
        result.put( "isSuccess", isSuccess );
        result.put( "resultJson", resultJson );
        
        return result;
    }
    
    
    
}
