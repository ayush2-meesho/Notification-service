package com.messagingservice.notificationservice.controllers;


import com.messagingservice.notificationservice.exception.InvalidAuthHeaderException;
import com.messagingservice.notificationservice.models.request.BlacklistRequest;
import com.messagingservice.notificationservice.services.BlacklistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
@Validated
public class BlacklistController {

    @Autowired
    private BlacklistService blacklistService;

    @PostMapping("/v1/blacklist")
    public ResponseEntity<?> addToBlackList(@RequestHeader(value = "Authorization", required = false) String authHeader, @Valid @RequestBody BlacklistRequest blacklistRequest) throws Exception{
        validateAuthHeader(authHeader);
        return ResponseEntity.ok(blacklistService.addToBlacklist(blacklistRequest));
    }

    @DeleteMapping("/v1/blacklist")
    public ResponseEntity<?> removeFromBlacklist(@RequestHeader(value = "Authorization", required = false) String authHeader, @Valid @RequestBody BlacklistRequest blacklistRequest) throws Exception{
        validateAuthHeader(authHeader);
        return ResponseEntity.ok(blacklistService.removeFromBlacklist(blacklistRequest));
    }

    @GetMapping("/v1/blacklist")
    public ResponseEntity<?> getBlacklistedPhoneNumbers(@RequestHeader(value = "Authorization", required = false) String authHeader) throws Exception {
        validateAuthHeader(authHeader);
        return ResponseEntity.ok(blacklistService.getFromBlacklist());
    }

    private void validateAuthHeader(String authHeader) throws InvalidAuthHeaderException {
        if(Objects.isNull(authHeader) || !authHeader.equals("dev")) {
            throw new InvalidAuthHeaderException("Invalid Authorization header");
        }
    }
}
