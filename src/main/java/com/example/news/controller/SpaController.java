package com.example.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Lets React Router handle routes like /news. Without this, refreshing
 * the page on /news would return a 404 from Spring Boot.
 * Paths with a dot (e.g. /assets/app.js) and everything under /api are not affected.
 */
@Controller
public class SpaController {

    @GetMapping("/{path:[^\\.]*}")
    public String forward() {
        return "forward:/index.html";
    }
}
