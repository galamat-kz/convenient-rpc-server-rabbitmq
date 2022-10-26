package kz.galamat.convenient.rpc.server.rabbitmq.servlet;

import lombok.*;
import org.apache.catalina.Globals;
import org.apache.tomcat.util.http.FastHttpDateFormat;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Yersin Mukay on 12.10.2022
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InternalHttpRequest implements HttpServletRequest {

    @NonNull
    private String path;
    @NonNull
    private String method;
    @NonNull
    private Map<String, String[]> queryParams;
    @Builder.Default
    private Map<String, List<String>> headers = new HashMap<>();
    @Builder.Default
    private Map<String, Object> attr = new HashMap<>();
    @NonNull
    private ServletInputStream servletInputStream;
    @NonNull
    private BufferedReader bufferedReader;

    public void init() {
        attr.put(Globals.DISPATCHER_TYPE_ATTR, DispatcherType.REQUEST);
    }

    @Override
    public String getAuthType() {
        return null;
    }

    @Override
    public Cookie[] getCookies() {
        return new Cookie[0];
    }

    @Override
    public long getDateHeader(String name) {
        String value = getHeader(name);
        if (value == null) {
            return -1;
        }
        long date = FastHttpDateFormat.parseDate(value);
        if (date == -1) {
            throw new IllegalArgumentException(value);
        }
        return date;
    }

    @Override
    public String getHeader(String name) {
        if (headers == null) {
            return null;
        }
        List<String> values = headers.get(name);
        if (values == null || values.isEmpty()) {
            return null;
        }
        return values.get(0);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        if (headers == null) {
            return Collections.enumeration(Collections.emptyList());
        }
        List<String> values = headers.get(name);
        if (values == null || values.isEmpty()) {
            return Collections.enumeration(Collections.emptyList());
        }
        return Collections.enumeration(values);
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        return Collections.enumeration(headers.keySet());
    }

    @Override
    public int getIntHeader(String name) {
        String value = getHeader(name);
        if (value == null) {
            return -1;
        }
        return Integer.parseInt(value);
    }

    @Override
    public String getMethod() {
        return method;
    }

    @Override
    public String getPathInfo() {
        return path;
    }

    @Override
    public String getPathTranslated() {
        return null;
    }

    @Override
    public String getContextPath() {
        return "";
    }

    @Override
    public String getQueryString() {
        return "";
    }

    @Override
    public String getRemoteUser() {
        return null;
    }

    @Override
    public boolean isUserInRole(String role) {
        return false;
    }

    @Override
    public Principal getUserPrincipal() {
        return null;
    }

    @Override
    public String getRequestedSessionId() {
        return null;
    }

    @Override
    public String getRequestURI() {
        return path;
    }

    @Override
    public StringBuffer getRequestURL() {
        return null;
    }

    @Override
    public String getServletPath() {
        return path;
    }

    @Override
    public HttpSession getSession(boolean create) {
        return null;
    }

    @Override
    public HttpSession getSession() {
        return null;
    }

    @Override
    public String changeSessionId() {
        return null;
    }

    @Override
    public boolean isRequestedSessionIdValid() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromCookie() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromURL() {
        return false;
    }

    @Override
    public boolean isRequestedSessionIdFromUrl() {
        return false;
    }

    @Override
    public boolean authenticate(HttpServletResponse response) {
        return false;
    }

    @Override
    public void login(String username, String password) {

    }

    @Override
    public void logout() {

    }

    @Override
    public Collection<Part> getParts() {
        return null;
    }

    @Override
    public Part getPart(String name) {
        return null;
    }

    @Override
    public <T extends HttpUpgradeHandler> T upgrade(Class<T> httpUpgradeHandlerClass) {
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        return attr.get(name);
    }

    public boolean hasException() {
        return attr.containsKey(DispatcherServlet.EXCEPTION_ATTRIBUTE);
    }

    public Throwable getException() {
        return (Throwable) attr.get(DispatcherServlet.EXCEPTION_ATTRIBUTE);
    }

    @Override
    public Enumeration<String> getAttributeNames() {
        return Collections.enumeration(attr.keySet());
    }

    @Override
    public String getCharacterEncoding() {
        return null;
    }

    @Override
    public void setCharacterEncoding(String env) {

    }

    @Override
    public int getContentLength() {
        return -1;
    }

    @Override
    public long getContentLengthLong() {
        return -1;
    }

    @Override
    public String getContentType() {
        return "application/json";
    }

    @Override
    public ServletInputStream getInputStream() {
        return servletInputStream;
    }

    @Override
    public String getParameter(String name) {
        return null;
    }

    @Override
    public Enumeration<String> getParameterNames() {
        return Collections.enumeration(queryParams.keySet());
    }

    @Override
    public String[] getParameterValues(String name) {
        return queryParams.get(name);
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return queryParams;
    }

    @Override
    public String getProtocol() {
        return "HTTP/1.1";
    }

    @Override
    public String getScheme() {
        return "http";
    }

    @Override
    public String getServerName() {
        return null;
    }

    @Override
    public int getServerPort() {
        return 0;
    }

    @Override
    public BufferedReader getReader() {
        return null;
    }

    @Override
    public String getRemoteAddr() {
        return null;
    }

    @Override
    public String getRemoteHost() {
        return null;
    }

    @Override
    public void setAttribute(String name, Object o) {
        attr.put(name, o);
    }

    @Override
    public void removeAttribute(String name) {
        attr.remove(name);
    }

    @Override
    public Locale getLocale() {
        return null;
    }

    @Override
    public Enumeration<Locale> getLocales() {
        return null;
    }

    @Override
    public boolean isSecure() {
        return false;
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String path) {
        return null;
    }

    @Override
    public String getRealPath(String path) {
        return null;
    }

    @Override
    public int getRemotePort() {
        return 0;
    }

    @Override
    public String getLocalName() {
        return null;
    }

    @Override
    public String getLocalAddr() {
        return null;
    }

    @Override
    public int getLocalPort() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public AsyncContext startAsync() throws IllegalStateException {
        return null;
    }

    @Override
    public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
        return null;
    }

    @Override
    public boolean isAsyncStarted() {
        return false;
    }

    @Override
    public boolean isAsyncSupported() {
        return false;
    }

    @Override
    public AsyncContext getAsyncContext() {
        return null;
    }

    @Override
    public DispatcherType getDispatcherType() {
        return DispatcherType.REQUEST;
    }

    @SuppressWarnings("unused")
    public static class InternalHttpRequestBuilder {
        private Map<String, String[]> queryParams = new HashMap<>();
        private ServletInputStream servletInputStream;
        private BufferedReader bufferedReader;


        public InternalHttpRequestBuilder servletInputStream(InputStream inputStream) {
            this.servletInputStream = new InternalServletInputStream(inputStream);
            return this;
        }

        public InternalHttpRequestBuilder bufferedReader(InputStream inputStream) {
            this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            return this;
        }

        public InternalHttpRequestBuilder queryParams(Map<String, List<String>> queryListParams) {
            this.queryParams = queryListParams.entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            e -> e.getValue().toArray(new String[0])
                    ));
            return this;
        }
    }
}
