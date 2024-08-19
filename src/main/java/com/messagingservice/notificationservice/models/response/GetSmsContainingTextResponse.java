package com.messagingservice.notificationservice.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.messagingservice.notificationservice.models.entity.elasticsearch.ESSmsDocument;
import lombok.Builder;
import lombok.Data;

import java.util.List;


@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetSmsContainingTextResponse {
    @JsonProperty("data")
    private List<ESSmsDocument> data;
}
