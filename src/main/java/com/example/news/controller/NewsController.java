package com.example.news.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.example.news.dto.ArticleDto;
import com.example.news.dto.NewsApiResponse;

@RestController
public class NewsController {

    private final RestTemplate restTemplate;
    private final String newsApiKey;

    public NewsController(
            RestTemplate restTemplate,
            @Value("${newsapi.key}") String newsApiKey) {
        this.restTemplate = restTemplate;
        this.newsApiKey = newsApiKey;
    }

    @GetMapping("/api/darts-news")
    public ResponseEntity<?> fetchDartsNews() {

        String query = "(darts OR PDC OR Professional Darts Corporation OR PDC World Championship OR World Darts Championship)";

        String url;

        try {
            url = "https://newsapi.org/v2/everything"
                    + "?q=" + URLEncoder.encode(query, "UTF-8")
                    + "&sortBy=publishedAt"
                    + "&language=en"
                    + "&pageSize=100"
                    + "&apiKey=" + newsApiKey;

        } catch (UnsupportedEncodingException e) {
            return ResponseEntity.status(500)
                    .body("{\"error\":\"Encoding failed\"}");
        }

        try {

            NewsApiResponse apiResponse =
                    restTemplate.getForObject(url, NewsApiResponse.class);

            if (apiResponse == null
                    || !"ok".equals(apiResponse.getStatus())
                    || apiResponse.getArticles() == null) {

                return ResponseEntity.status(502)
                        .body("{\"error\":\"Invalid response from news API\"}");
            }

            // Words that clearly indicate an unrelated article
            List<String> blockedKeywords = Arrays.asList(
                    "politics",
                    "football",
                    "soccer",
                    "judiciary"
            );

            /*
             * Filter the articles first.
             *
             * We DO NOT limit to 5 here.
             * We want to get as many valid articles as possible,
             * remove duplicates, and THEN take the first 5.
             */
            List<ArticleDto> filteredTopArticles =
                    apiResponse.getArticles().stream()

                    // Must have a title
                    .filter(a -> a.getTitle() != null)

                    // Remove clearly unrelated articles
                    .filter(a -> blockedKeywords.stream()
                            .noneMatch(word ->
                                    a.getTitle()
                                            .toLowerCase()
                                            .contains(word.toLowerCase())
                                    ||
                                    (a.getDescription() != null
                                            && a.getDescription()
                                            .toLowerCase()
                                            .contains(word.toLowerCase()))
                            )
                    )

                    /*
                     * Remove duplicate stories based on their title.
                     *
                     * Converts titles such as:
                     *
                     * "Luke Littler wins PDC title!"
                     *
                     * "Luke Littler wins PDC title"
                     *
                     * into the same comparison value.
                     */
                    .collect(Collectors.collectingAndThen(

                            Collectors.toMap(

                                    a -> a.getTitle()
                                            .toLowerCase()
                                            .replaceAll("[^a-z0-9 ]", "")
                                            .replaceAll("\\s+", " ")
                                            .trim(),

                                    a -> a,

                                    // Keep the first version
                                    (existing, duplicate) -> existing
                            ),

                            /*
                             * ONLY take 5 AFTER duplicates have
                             * been removed.
                             */
                            map -> map.values()
                                    .stream()
                                    .limit(5)
                                    .collect(Collectors.toList())
                    ));

            return ResponseEntity.ok(filteredTopArticles);

        } catch (RestClientException e) {

            e.printStackTrace();

            return ResponseEntity.status(500)
                    .body("{\"error\":\"Failed to fetch news\"}");
        }
    }
}