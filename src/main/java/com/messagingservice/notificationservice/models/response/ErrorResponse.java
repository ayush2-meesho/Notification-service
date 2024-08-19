package com.messagingservice.notificationservice.models.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    @JsonProperty("error")
    private Error error;

    @Data
    @Builder
    public  static class Error {
        @JsonProperty("code")
        private String code;

        @JsonProperty("message")
        private String message;
    }
}
