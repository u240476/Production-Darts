package com.example.news.dto;

public class ArticleDto {
    private String title;
    private String url;
    private String publishedAt;
    private String description;

    // Constructors
    public ArticleDto() {}
    public ArticleDto(String title, String url, String publishedAt, String description) {
        this.title = title;
        this.url = url;
        this.publishedAt = publishedAt;
        this.description = description;
    }

    // Getters & Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getPublishedAt() { return publishedAt; }
    public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}