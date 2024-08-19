package com.messagingservice.notificationservice.controllers;


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
    public ResponseEntity<?> sendSms(@Valid @RequestBody SendSmsRequest sendSmsRequest) throws Exception {
        return ResponseEntity.ok(smsService.saveSmsRequest(sendSmsRequest));
    }

    @GetMapping("/v1/sms")
    public ResponseEntity<?> getSmsDetails(@RequestParam("request_id") Long id) throws Exception {
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
    public ResponseEntity<?> getSmsByPhoneNumberAndTimeRange(@Valid @RequestBody GetSmsSentBetweenRequest getSmsSentBetweenRequest) throws Exception {
        return ResponseEntity.ok(smsService.getSentBetweenDetails(getSmsSentBetweenRequest));
    }

    @PostMapping("v1/sms/get_sms_containing_text")
    public ResponseEntity<?> getSmsContainingText(@Valid @RequestBody GetSmsContainingTextRequest getSmsContainingTextRequest) throws Exception {
        return ResponseEntity.ok(smsService.getSmsContainingText(getSmsContainingTextRequest));
    }

}
