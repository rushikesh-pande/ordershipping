package com.shipping.dto;

import com.shipping.model.GiftWrapType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftWrappingRequest {

    @NotBlank(message = "Order ID is required")
    private String orderId;

    @NotNull(message = "Gift wrap type is required")
    private GiftWrapType wrapType;

    @Size(max = 500, message = "Gift message cannot exceed 500 characters")
    private String giftMessage;

    @Size(max = 200, message = "Card message cannot exceed 200 characters")
    private String cardMessage;

    private String recipientName;

    private Boolean anonymousSender;
}

