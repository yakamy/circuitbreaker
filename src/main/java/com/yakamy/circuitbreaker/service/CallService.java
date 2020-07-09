package com.yakamy.circuitbreaker.service;

import com.yakamy.circuitbreaker.exception.BusinessException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.vavr.control.Try;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;

import java.util.concurrent.CompletableFuture;

/**
 * @author chenjunwei
 * @date 2020-04-10 16:57
 * @description
 */
@Service
public class CallService {

    private static final String ORDERHUB = "orderhub";

    @CircuitBreaker(name = ORDERHUB)
    public String ignoreException(){
        throw new BusinessException("ignored");
    }

    @CircuitBreaker(name = ORDERHUB, fallbackMethod = "fallback")
    public String exception(){
        throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "internal exception");
    }

    private String fallback(Exception e){
        return "fallback, Exception: " + e.getMessage();
    }

    @Retry(name = ORDERHUB)
    public String retry() {
        throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "retry");
    }

    @RateLimiter(name = ORDERHUB, fallbackMethod = "ratelimitFallback")
    public String ratelimit() {
        return "ratelimit";
    }

    public String ratelimitFallback(){
        return "request limit, please try later";
    }

    @Bulkhead(name = ORDERHUB, type = Bulkhead.Type.THREADPOOL)
    @TimeLimiter(name = ORDERHUB)
    @CircuitBreaker(name = ORDERHUB, fallbackMethod = "timelimitFallback")
    public CompletableFuture<String> timelimit() {
        Try.run(() -> Thread.sleep(5000));
        return CompletableFuture.completedFuture("success");
    }

    private CompletableFuture<String> timelimitFallback(Exception e){
        return CompletableFuture.completedFuture("request timeout, please try later:" + e.getMessage());
    }
}
