package com.messagingservice.notificationservice.controllers;


import com.messagingservice.notificationservice.models.request.BlacklistRequest;
import com.messagingservice.notificationservice.services.BlacklistService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@Validated
public class BlacklistController {

    @Autowired
    private BlacklistService blacklistService;

    @PostMapping("/v1/blacklist")
    public ResponseEntity<?> addToBlackList(@Valid @RequestBody BlacklistRequest blacklistRequest) throws Exception{
        return ResponseEntity.ok(blacklistService.addToBlacklist(blacklistRequest));
    }

    @DeleteMapping("/v1/blacklist")
    public ResponseEntity<?> removeFromBlacklist(@Valid @RequestBody BlacklistRequest blacklistRequest) throws Exception{
        return ResponseEntity.ok(blacklistService.removeFromBlacklist(blacklistRequest));
    }

    @GetMapping("/v1/blacklist")
    public ResponseEntity<?> getBlacklistedPhoneNumbers() throws Exception {
        return ResponseEntity.ok(blacklistService.getFromBlacklist());
    }
}
