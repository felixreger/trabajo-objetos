package com.example.endpoints.auth.cors;

import com.example.endpoints.utils.Utils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "CorsFilter", urlPatterns = "/*")
public class CorsFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {

        System.out.println("CORSFilter HTTP Request: " + servletRequest.getMethod());

        // Authorize (allow) all domains to consume the content
        servletResponse.addHeader("Access-Control-Allow-Origin", "*");
        servletResponse.addHeader("Access-Control-Allow-Methods","GET, OPTIONS, HEAD, PUT, POST, DELETE");

        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
        if (servletRequest.getMethod().equals("OPTIONS")) {
            servletResponse.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }

        // pass the request along the filter chain
        chain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
