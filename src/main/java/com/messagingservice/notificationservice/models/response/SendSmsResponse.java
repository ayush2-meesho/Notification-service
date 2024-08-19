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
public class SendSmsResponse {
    @JsonProperty("data")
    private Data data;

    @lombok.Data
    @Builder
    public static class Data {
        @JsonProperty("requestId")
        private Long requestId;

        @JsonProperty("comments")
        private String comments;
    }
}
