package com.yakamy.circuitbreaker.controller;

import com.yakamy.circuitbreaker.service.CallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * @author chenjunwei
 * @date 2020-04-10 16:56
 * @description
 */
@RestController
public class ApiController {

    @Autowired
    private CallService callService;


    @GetMapping("/exception")
    public String exception(){
        return callService.exception();
    }

    @GetMapping("/ignoreException")
    public String ignoreException(){
        return callService.ignoreException();
    }

    @GetMapping("/retry")
    public String retry(){
        return callService.retry();
    }

    @GetMapping("/ratelimit")
    public String ratelimit(){
        return callService.ratelimit();
    }

    @GetMapping("/timelimit")
    public CompletableFuture<String> timelimit(){

        CompletableFuture<String> timelimit = callService.timelimit();
        return timelimit;
    }

}
