/**
 * Copyright(2015-2019) yoai.com, all rights reserved. Author: LinHongwu (hongwu.lin@yoai.com)
 */

package com.lims.project.sendAPP.util;

import com.alibaba.fastjson.JSONObject;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class OpenApiTool {

    public static ViewBuilder getSignedUrl(String tok, Long time, String url, String body) {
        ViewBuilder vb = ViewBuilder.newInstance().fromUrl(url);
        String ts = Long.toString(time);
        String code = DigestUtils.md5Hex(RandomUtils.nextBytes(32));
        String msg = tok + ts + code + body;
        String sign = DigestUtils.md5Hex(msg).toUpperCase();
        vb.param("ts", ts);
        vb.param("code", code);
        vb.param("sign", sign);
        return vb;
    }

    public static boolean checkSignedUrl(String tok, Long time, String code, String body, String sign) {
        String ts = Long.toString(time);
        String msg = tok + ts + code + StringUtils.defaultString(body);
        String realSign = DigestUtils.md5Hex(msg).toUpperCase();
        return realSign.equals(sign);
    }

    public static boolean checkSignedUrl(HttpServletRequest request, String tok, String body) {
        Long time = Long.parseLong(request.getParameter("ts"));
        String code = request.getParameter("code");
        String sign = request.getParameter("sign");
        return checkSignedUrl(tok, time, code, StringUtils.defaultString(body), sign);
    }

    public static JSONObject postForJson(String token, String url, String body, boolean signBody, boolean useMilliSec,
        boolean throwException) {
        try {
            ResponseEntity<byte[]> data = post(token, url, body, signBody, useMilliSec);
            if (data.getStatusCode() == HttpStatus.OK) {
                return JSONObject.parseObject(data.getBody(), JSONObject.class);
            } else {
                return null;
            }
        } catch (Exception e) {
            if (throwException) {
                throw new RuntimeException(e);
            }
            return null;
        }
    }

    public static JSONObject postForJson(String token, String url, String body, boolean signBody, boolean useMilliSec) {
        ResponseEntity<byte[]> data = post(token, url, body, signBody, useMilliSec);
        if (data.getStatusCode() == HttpStatus.OK) {
            return JSONObject.parseObject(data.getBody(), JSONObject.class);
        } else {
            return null;
        }
    }

    public static ResponseEntity<byte[]> post(String token, String url, String body, boolean signBody,
        boolean useMilliSec) {
        RestTemplate tmpl = new RestTemplate();
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>(body, header);
        long ts = System.currentTimeMillis();
        ts = useMilliSec ? ts : ts / 1000;
        if (signBody) {
            url = getSignedUrl(token, ts, url, body).build();
        } else {
            url = getSignedUrl(token, ts, url, "").build();
        }
        return tmpl.postForEntity(url, request, byte[].class);
    }

    public static void main(String[] args) {
        System.out.println(OpenApiTool
            .getSignedUrl("b9ca7bc2d6149a27d2a3fc0cc09c0cab", System.currentTimeMillis() / 1000, "http://test.yoai.com",
                "").build());
    }
}
