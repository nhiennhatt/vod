package com.hiennhatt.vod.controllers;

import com.hiennhatt.vod.dtos.HistoryDTO;
import com.hiennhatt.vod.models.CustomUserDetails;
import com.hiennhatt.vod.services.HistoryService;
import com.hiennhatt.vod.validations.SaveHistoryValidation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    @PostMapping("/")
    @PreAuthorize("isAuthenticated()")
    public void saveHistory(@RequestBody @Valid SaveHistoryValidation body, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        historyService.saveHistory(body.getVideoId(), customUserDetails.getUser());
    }

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public List<HistoryDTO> getHistory(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return historyService.getPersonalHistories(customUserDetails.getUser());
    }
}
