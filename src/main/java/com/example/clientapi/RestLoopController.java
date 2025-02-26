package com.example.clientapi;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loop")
public class RestLoopController {

    private final RestLoopService restLoopService;

    public RestLoopController(RestLoopService restLoopService) {
        this.restLoopService = restLoopService;
    }

    @GetMapping("/start")
    public String startLoop(@RequestParam String url,
                            @RequestParam(defaultValue = "5") int totalRequests,
                            @RequestParam(defaultValue = "1000") long delayMillis) {
        new Thread(() -> restLoopService.sendRequestsInLoop(url, totalRequests, delayMillis)).start();
        return "Loop started for " + totalRequests + " requests to " + url;
    }
}