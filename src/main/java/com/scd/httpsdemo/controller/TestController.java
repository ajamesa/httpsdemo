package com.scd.httpsdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author James Chen
 * @date 02/11/19
 */
@RestController
@RequestMapping(value = "/test/ssl/req")
public class TestController {

    @Autowired
    @Qualifier(value = "mainRestTemplate")
    private RestTemplate restTemplate;

    @GetMapping
    public String testGet() {
        return "get ok";
    }

    @PostMapping
    public String testPost() {
        return "post ok";
    }

    @PutMapping
    public String testPut() {
        return "put ok";
    }

    @DeleteMapping
    public String testDelete() {
        return "delete ok";
    }

    @GetMapping(value = "/rest/test")
    public String testRestTemplate() {
        String url = "https://free-api.heweather.com/v5/forecast?city=CN101080101&key=5c043b56de9f4371b0c7f8bee8f5b75e";
//        Map<String, Object> params = new HashMap<>();
//        params.put("start_time", "20180824");
//        params.put("end_time", "20180827");
//        HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<>(params, null);
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);
        if (responseEntity.getStatusCodeValue() >= 200) {
            return responseEntity.getBody();
        }
        return "request error";
    }
}
