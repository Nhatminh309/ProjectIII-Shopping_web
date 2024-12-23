package org.demo.project.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInfo {
    private int total;
    private String orderInfo;
    private String returnUrl;

    public PaymentInfo(int total, String orderInfo, String returnUrl) {
        this.total = total;
        this.orderInfo = orderInfo;
        this.returnUrl = returnUrl;
    }
}
