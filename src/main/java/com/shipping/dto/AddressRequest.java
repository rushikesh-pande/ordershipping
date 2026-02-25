package com.shipping.dto;

import com.shipping.model.AddressType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    @NotBlank(message = "Customer ID is required")
    private String customerId;

    @NotNull(message = "Address type is required")
    private AddressType addressType;

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Address line 1 is required")
    private String addressLine1;

    private String addressLine2;

    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;

    @NotBlank(message = "Zip code is required")
    @Pattern(regexp = "^[0-9]{5,10}$", message = "Invalid zip code format")
    private String zipCode;

    @NotBlank(message = "Country is required")
    private String country;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Invalid phone number format")
    private String phoneNumber;

    @Pattern(regexp = "^[0-9]{10,15}$", message = "Invalid alternate phone format")
    private String alternatePhone;

    private String landmark;

    private Boolean isDefault;
}

