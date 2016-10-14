package com.bintime.views;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Component("hello_pi")
public class Hello implements View {
    @Override
    public String getContentType() {
        return "text/html";
    }

    @Override
    public void render(Map<String, ?> map,
                       HttpServletRequest request, HttpServletResponse response) throws Exception {

        response.getWriter().format("Hello World from class 'Hello'!\npi = %s\n", map.get("pi"));
    }
}
