package kz.galamat.convenient.rpc.server.rabbitmq.servlet;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

/**
 * Created by Yersin Mukay on 12.10.2022
 */
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InternalHttpResponse implements HttpServletResponse {
    private int status;
    private String statusMessage;
    @Builder.Default
    private Map<String, String> headers = new HashMap<>();
    private Long contentLength;
    private int bufferSize;
    @NonNull
    private ServletOutputStream servletOutputStream;
    @NonNull
    private PrintWriter printWriter;

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public ServletOutputStream getOutputStream() {
        return servletOutputStream;
    }

    @Override
    public PrintWriter getWriter() {
        return printWriter;
    }

    @Override
    public void setCharacterEncoding(String charset) {

    }

    @Override
    public void setContentLength(int len) {
        contentLength = (long) len;
    }

    @Override
    public void setContentLengthLong(long length) {
        contentLength = length;
    }

    @Override
    public void setContentType(String type) {

    }

    @Override
    public void setBufferSize(int size) {
        bufferSize = size;
    }

    @Override
    public int getBufferSize() {
        return bufferSize;
    }

    @Override
    public void flushBuffer() {

    }

    @Override
    public void resetBuffer() {

    }

    @Override
    public boolean isCommitted() {
        return false;
    }

    @Override
    public void reset() {

    }

    @Override
    public void setLocale(Locale loc) {

    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public void addCookie(Cookie cookie) {

    }

    @Override
    public boolean containsHeader(String name) {
        return headers.containsKey(name);
    }

    @Override
    public String encodeURL(String url) {
        return null;
    }

    @Override
    public String encodeRedirectURL(String url) {
        return null;
    }

    @Override
    public String encodeUrl(String url) {
        return null;
    }

    @Override
    public String encodeRedirectUrl(String url) {
        return null;
    }

    @Override
    public void sendError(int sc, String msg) {
        setStatus(sc, msg);
    }

    @Override
    public void sendError(int sc) {
        setStatus(sc);
    }

    @Override
    public void sendRedirect(String location) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDateHeader(String name, long date) {
        headers.put(name, String.valueOf(date));
    }

    @Override
    public void addDateHeader(String name, long date) {
        headers.put(name, String.valueOf(date));
    }

    @Override
    public void setHeader(String name, String value) {
        headers.put(name, value);
    }

    @Override
    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    @Override
    public void setIntHeader(String name, int value) {
        headers.put(name, String.valueOf(value));
    }

    @Override
    public void addIntHeader(String name, int value) {
        headers.put(name, String.valueOf(value));
    }

    @Override
    public void setStatus(int sc) {
        this.status = sc;
    }

    @Override
    public void setStatus(int sc, String sm) {
        this.status = sc;
        this.statusMessage = sm;
    }

    @Override
    public int getStatus() {
        return this.status;
    }

    @Override
    public String getHeader(String name) {
        if (headers == null) {
            return null;
        }
        return this.headers.get(name);
    }

    @Override
    public Collection<String> getHeaders(String name) {
        if (headers == null) {
            return Collections.emptyList();
        }
        String values = headers.get(name);
        if (values == null || values.isEmpty()) {
            return Collections.emptyList();
        }
        return List.of(values);
    }

    @Override
    public Collection<String> getHeaderNames() {
        if (headers == null) {
            return Collections.emptyList();
        }
        return headers.keySet();
    }

    @SuppressWarnings("unused")
    public static class InternalHttpResponseBuilder {
        private ServletOutputStream servletOutputStream;
        private PrintWriter printWriter;
        public InternalHttpResponseBuilder servletOutputStream(OutputStream outputStream) {
            this.servletOutputStream = new InternalServletOutputStream(outputStream);
            return this;
        }

        public InternalHttpResponseBuilder printWriter(OutputStream outputStream) {
            this.printWriter = new PrintWriter(outputStream);
            return this;
        }
    }
}
