package com.messagingservice.notificationservice.models.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class GetSmsContainingTextRequest {
    @JsonProperty("text")
    @NotNull(message = "text is mandatory")
    private String text;

    @JsonProperty("pageNumber")
    @NotNull(message = "number of page is mandatory")
    private int pageNumber;

    @JsonProperty("size")
    @NotNull(message = "size of page is mandatory")
    private int size;
}
