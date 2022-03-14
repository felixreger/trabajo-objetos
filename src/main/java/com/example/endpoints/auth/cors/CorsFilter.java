package com.example.endpoints.auth.cors;

import com.example.endpoints.utils.Utils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CORS", urlPatterns =  "/*")
public class CorsFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("CORSFilter HTTP Request: " + request.getMethod());

        // Authorize (allow) all domains to consume the content
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");

        HttpServletResponse resp =  response;
                if (request.getMethod().equals("OPTIONS")) {
                    resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                    return;
                }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
