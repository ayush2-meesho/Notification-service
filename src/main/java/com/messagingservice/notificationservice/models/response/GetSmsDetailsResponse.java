package com.messagingservice.notificationservice.models.response;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.messagingservice.notificationservice.models.entity.mysql.SmsEntity;
import lombok.Builder;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetSmsDetailsResponse {

    @JsonProperty("data")
    @NotNull
    private SmsEntity smsEntity;
}
