package com.messagingservice.notificationservice.models.request;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSmsSentBetweenRequest {

    @JsonProperty("phoneNumber")
    @NotNull(message = "phone number is mandatory")
    private String phoneNumber;

    @JsonProperty("startTime")
    @NotNull(message = "start time is mandatory")
    private LocalDateTime startTime;

    @JsonProperty("endTime")
    @NotNull(message = "end time is mandatory")
    private LocalDateTime endTime;

    @JsonProperty("pageNumber")
    @NotNull(message = "number of pages is mandatory")
    private Integer pageNumber;

    @JsonProperty("size")
    @NotNull(message = "size of page is mandatory")
    private Integer size;
}
