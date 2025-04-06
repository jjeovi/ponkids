package com.meta.ponkids.domain.payment.dto.depth2;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 결제 확인 URL 정보
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties( ignoreUnknown = true )
public class Checkout {
    private String url;     // 결제 확인 URL
}
