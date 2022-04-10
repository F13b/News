package com.example.news;

public class Item {
    String nameNews, textNews;

    public Item(String nameNews, String textNews) {
        this.nameNews = nameNews;
        this.textNews = textNews;
    }

    public String getNewsName() { return nameNews; }

    public String getNewsText() {
        return textNews;
    }
}
