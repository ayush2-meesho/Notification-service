package com.messagingservice.notificationservice.models.entity.elasticsearch;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Document(indexName = "sms")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class ESSmsDocument {

    @Id
    @JsonProperty("id")
    @Field(type = FieldType.Integer)
    private Long id;

    @Field(type = FieldType.Text)
    @JsonProperty("phoneNumber")
    private String phoneNumber;

    @Field(type = FieldType.Text)
    @JsonProperty("message")
    private String message;

    @Field(type = FieldType.Long)
    @JsonProperty("sentTime")
    private Long sentTime;
}
