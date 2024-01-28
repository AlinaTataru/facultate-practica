package com.example.programare_examene.common.api;

import com.example.programare_examene.member.transport.StatusDto;
import com.example.programare_examene.common.util.Utils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AppResource {
    private final Utils utils;

    public AppResource(Utils utils) {
        this.utils = utils;
    }

    @GetMapping("/status")
    public StatusDto getStatus() {
        return new StatusDto(utils.getLoggedUser().getPrincipal().toString(), utils.getLoggedUser().getAuthorities());
    }
}
