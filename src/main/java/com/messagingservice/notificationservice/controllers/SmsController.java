package com.messagingservice.notificationservice.controllers;


import com.messagingservice.notificationservice.exception.InvalidAuthHeaderException;
import com.messagingservice.notificationservice.models.request.GetSmsContainingTextRequest;
import com.messagingservice.notificationservice.models.request.GetSmsSentBetweenRequest;
import com.messagingservice.notificationservice.models.response.ErrorResponse;
import com.messagingservice.notificationservice.models.request.SendSmsRequest;
import com.messagingservice.notificationservice.models.response.GetSmsDetailsResponse;
import com.messagingservice.notificationservice.services.SmsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;


import java.util.Objects;

@RestController
@Validated
public class SmsController {

    @Autowired
    private SmsService smsService;
    @Autowired
    private View error;


    @PostMapping("/v1/sms/send")
    public ResponseEntity<?> sendSms(@RequestHeader(value = "Authorization", required = false) String authHeader, @Valid @RequestBody SendSmsRequest sendSmsRequest) throws Exception {
        validateAuthHeader(authHeader);
        return ResponseEntity.ok(smsService.saveSmsRequest(sendSmsRequest));
    }

    @GetMapping("/v1/sms")
    public ResponseEntity<?> getSmsDetails(@RequestHeader(value = "Authorization", required = false) String authHeader, @RequestParam("request_id") Long id) throws Exception {
        validateAuthHeader(authHeader);
        GetSmsDetailsResponse response = smsService.getRequestDetails(id);
        if(Objects.isNull(response.getSmsEntity())) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .error(ErrorResponse.Error.builder().code("INVALID_REQUEST").message("request_id not found").build())
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/v1/sms/get_sms_sent_between")
    public ResponseEntity<?> getSmsByPhoneNumberAndTimeRange(@RequestHeader(value = "Authorization", required = false) String authHeader, @Valid @RequestBody GetSmsSentBetweenRequest getSmsSentBetweenRequest) throws Exception {
        validateAuthHeader(authHeader);
        return ResponseEntity.ok(smsService.getSentBetweenDetails(getSmsSentBetweenRequest));
    }

    @PostMapping("v1/sms/get_sms_containing_text")
    public ResponseEntity<?> getSmsContainingText(@RequestHeader(value = "Authorization", required = false) String authHeader, @Valid @RequestBody GetSmsContainingTextRequest getSmsContainingTextRequest) throws Exception {
        validateAuthHeader(authHeader);
        return ResponseEntity.ok(smsService.getSmsContainingText(getSmsContainingTextRequest));
    }

    private void validateAuthHeader(String authHeader) throws InvalidAuthHeaderException {
        if(Objects.isNull(authHeader) || !authHeader.equals("dev")) {
            throw new InvalidAuthHeaderException("Invalid Authorization header");
        }
    }
}
