/**
 * Copyright(2015) yoai.com, all rights reserved. Author: LinHongwu (hongwu.lin@yoai.com)
 */

package com.lims.project.sendAPP.util;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 用于构造视图名称的类<br/> 这里的视图可以使mvc中的视图名称，也可以是一个重定向url<br/> 常见用法: new BaseView.as(Method.NORMAL).view("account",
 * "login").toString() new BaseView.as(Method.REDIRECT).fromRequest(request) 普通视图和重定向构造一般不同时使用
 */
public class ViewBuilder {

    public enum Method {
        REDIRECT, FORWARD, NORMAL
    }

    private UriComponentsBuilder builder = UriComponentsBuilder.newInstance();
    private Method method = Method.NORMAL;

    public static ViewBuilder newInstance() {
        return new ViewBuilder().as(Method.NORMAL);
    }

    /**
     * 为视图url添加参数
     *
     * @param key
     * @param values
     * @return
     */
    public ViewBuilder param(String key, Object... values) {
        builder.queryParam(key, values);
        return this;
    }

    /**
     * 替换现有的参数
     *
     * @param key
     * @param values
     * @return
     */
    public ViewBuilder replaceParam(String key, Object... values) {
        builder.replaceQueryParam(key, values);
        return this;
    }

    public ViewBuilder param(HttpServletRequest request) {
        LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        Map<String, String[]> oldParams = request.getParameterMap();
        for (Map.Entry<String, String[]> kv : oldParams.entrySet()) {
            params.put(kv.getKey(), Arrays.asList(kv.getValue()));
        }
        builder.queryParams(params);
        return this;
    }

    /**
     * 指定使用何种方法，NORMAL为普通视图，REDIRECT为重定向视图，FORWARD为前递视图
     *
     * @param m
     * @return
     */
    public ViewBuilder as(Method m) {
        this.method = m;
        return this;
    }

    /**
     * 清除所有的请求参数
     *
     * @return
     */
    public ViewBuilder clearParams() {
        this.builder.replaceQuery(null);
        return this;
    }

    /**
     * 清除路径和参数，仅保留头部(schema://host[:port if not 80])
     *
     * @return
     */
    public ViewBuilder clearPathAndParams() {
        this.builder.replaceQuery(null);
        this.builder.replacePath(null);
        return this;
    }

    /**
     * 指定uri路径，多层结构就填多个层次的名称，不要包含'/'字符(任意字符都当做 当前层级的名称，因此'/'会被转码而不是单做路径层级分割符号)。<br> 注意空字符串参数""不会产生一个路径层级，相当于没有这个参数，
     * 因此使用view函数不可能产生出"/"这个路径。<br> 什么都不传-> "" <br> "","abc"->"/abc"  <br> "abc"->"/abc" <br>
     * "abc","","","def"->"/abc/def" <br>
     *
     * @param path
     * @return
     */
    public ViewBuilder view(String... path) {
        builder.pathSegment(path);
        return this;
    }

    /**
     * 多层路径
     *
     * @param path
     * @return
     */
    public ViewBuilder path(String path) {
        builder.pathSegment(path.split("/"));
        return this;
    }

    /**
     * 更换视图路径
     *
     * @param path
     * @return
     */
    public ViewBuilder replaceView(String... path) {
        builder.replacePath(null);
        if (path != null) {
            builder.pathSegment(path);
        }
        return this;
    }

    public ViewBuilder port(int port) {
        this.builder.port(port);
        return this;
    }

    /**
     * 从url构造
     *
     * @param url 经过urlencode之后的url，uri部分不能有中文， queryString中可以有已经编码的中文
     */
    public ViewBuilder fromUrl(String url) {
        int pos = url.indexOf('?');
        if (pos < 0) {
            builder = UriComponentsBuilder.fromUriString(url);
        } else {
            String uri = url.substring(0, pos);
            String queryString = url.substring(pos + 1);
            String[] params = queryString.split("&");
            builder = UriComponentsBuilder.fromUriString(uri);
            for (String param : params) {
                String[] keyVal = param.split("=");
                if (keyVal.length == 1) {
                    builder.queryParam(keyVal[0]);
                } else {
                    try {
                        builder.queryParam(keyVal[0], URLDecoder.decode(keyVal[1], "utf8"));
                    } catch (UnsupportedEncodingException e) {
                        builder.queryParam(keyVal[0], keyVal[1]);
                    }
                }
            }
        }
        return this;
    }

    public ViewBuilder head(String scheme, String host, String port) {
        builder.scheme(scheme);
        builder.host(host);
        builder.port(port);
        return this;
    }

    public ViewBuilder scheme(String scheme) {
        builder.scheme(scheme);
        return this;
    }

    public ViewBuilder head(String urlStr) {
        try {
            URL url = new URL(urlStr);
            builder.scheme(url.getProtocol());
            builder.host(url.getHost());
            if (url.getDefaultPort() != url.getPort()) {
                builder.port(url.getPort());
            }
            return this;
        } catch (MalformedURLException e) {
        }
        return this;
    }

    /**
     * 如果端口为80，则在输出的url中忽略端口
     *
     * @return
     */
    public ViewBuilder ignorePortIf80() {
        UriComponents uc = builder.build();
        if (uc.getPort() == 80) {
            builder.port(null);
        }
        return this;
    }

    /**
     * 生成视图名称字符串
     *
     * @return
     */
    public String build() {
        String res = null;
        if (method == Method.NORMAL) {
            res = builder.toUriString();
        } else if (method == Method.REDIRECT) {
            res = "redirect:" + builder.toUriString();
        } else if (method == Method.FORWARD) {
            res = "forward:" + builder.toUriString();
        } else {
            return null;
        }
        return res;
    }

}
