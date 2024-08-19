package com.messagingservice.notificationservice.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SendSmsRequest {

    @JsonProperty("phoneNumber")
    @NotNull(message = "phone number is mandatory")
    private String phoneNumber;

    @JsonProperty("message")
    @NotNull(message = "message is mandatory")
    private String message;
}
