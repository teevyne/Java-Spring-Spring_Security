package ro.auth.authorizationserver.api.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.RequestFacade;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.web.savedrequest.Enumerator;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Order(value = Integer.MIN_VALUE)
@Slf4j
public class JsonToUrlEncodedAuthenticationFilter implements Filter {

    public static final String OAUTH_TOKEN_ENDPOINT = "/oauth/token";
    private final ObjectMapper mapper;

    public JsonToUrlEncodedAuthenticationFilter(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public void init(final FilterConfig filterConfig) {
    }

    @Override
    @SneakyThrows
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) {
        if (!(request instanceof RequestFacade)) {
            chain.doFilter(request, response);
            return;
        }

        final Field f = request.getClass().getDeclaredField("request");
        f.setAccessible(true);
        final Request realRequest = (Request) f.get(request);
        if (!OAUTH_TOKEN_ENDPOINT.equals(realRequest.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }
        //Request content type without spaces (inner spaces matter)
        //trim deletes spaces only at the beginning and at the end of the string
        final String contentType = realRequest.getContentType().toLowerCase().chars()
                .mapToObj(c -> String.valueOf((char) c))
                .filter(x -> !x.equals(" "))
                .collect(Collectors.joining());

        if ((contentType.equals(MediaType.APPLICATION_JSON_UTF8_VALUE.toLowerCase()) ||
                contentType.equals(MediaType.APPLICATION_JSON_VALUE.toLowerCase()))
                && Objects.equals((realRequest).getServletPath(), OAUTH_TOKEN_ENDPOINT)) {

            final InputStream is = realRequest.getInputStream();
            try (final BufferedReader br = new BufferedReader(new InputStreamReader(is), 16384)) {
                final String json = br.lines()
                        .collect(Collectors.joining(System.lineSeparator()));
                final HashMap<String, String> result = mapper.readValue(json, HashMap.class);
                final HashMap<String, String[]> r = new HashMap<>();

                for (final String key : result.keySet()) {
                    final String[] val = new String[1];
                    val[0] = result.get(key);
                    r.put(key, val);
                }
                final String[] val = new String[1];
                val[0] = (realRequest).getMethod();
                r.put("_method", val);

                final HttpServletRequest s = new MyServletRequestWrapper(((HttpServletRequest) request), r);
                chain.doFilter(s, response);
            }

        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    class MyServletRequestWrapper extends HttpServletRequestWrapper {
        private final HashMap<String, String[]> params;

        MyServletRequestWrapper(final HttpServletRequest request, final HashMap<String, String[]> params) {
            super(request);
            this.params = params;
        }

        @Override
        public String getParameter(final String name) {
            if (params.containsKey(name)) {
                return params.get(name)[0];
            }
            return "";
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return params;
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return new Enumerator<>(params.keySet());
        }

        @Override
        public String[] getParameterValues(final String name) {
            return params.get(name);
        }
    }
}