package com.messagingservice.notificationservice.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.messagingservice.notificationservice.models.entity.elasticsearch.ESSmsDocument;
import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetSmsSentBetweenResponse {
    @JsonProperty("data")
    @NotNull
    private List<ESSmsDocument> smsList;
}
