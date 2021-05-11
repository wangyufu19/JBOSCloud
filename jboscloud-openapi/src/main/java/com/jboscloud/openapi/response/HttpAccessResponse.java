package com.jboscloud.openapi.response;

import com.google.gson.Gson;
import com.jboscloud.openapi.Charset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * HttpAccessResponse
 * @author youfu.wang
 * @date 2021-04-30
 */
public class HttpAccessResponse {
    public static final String APPLICATION_TEXT_HTML = "text/html";
    public static final String APPLICATION_XML = "application/xml";
    public static final String APPLICATION_JSON = "application/json ";
    public static final String APPLICATION_IMAGE_GIF = "image/gif";
    public static final String APPLICATION_IMAGE_JPEG = "image/jpeg";
    public static final String APPLICATION_IMAGE_PNG = "image/png";
    private String contentType;
    private String charset;
    private HttpServletRequest request;
    private HttpServletResponse response;

    /**
     * 构造方法
     *
     * @param request
     * @param response
     */
    public HttpAccessResponse(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.contentType = HttpAccessResponse.APPLICATION_JSON;
        this.charset = Charset.UTF8;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public void doResponseBody(Return ret) throws IOException {
        Gson gson = new Gson();
        this.doResponseBody(gson.toJson(ret));
    }

    public void doResponseBody(String s) throws IOException {
        response.setContentType(contentType + ";charset=\"" + charset + "\"");
        PrintWriter out = response.getWriter();
        out.write(s);
        out.flush();
    }
}
