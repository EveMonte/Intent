package by.bstu.fit.savelev.intent;

import java.io.Serializable;

public class Book implements Serializable {
    private String title;
    private String author;
    private int year;
    private String genre;
    private int price;
    private String description;

    public boolean isAudible() {
        return audible;
    }

    public void setAudible(boolean audible) {
        this.audible = audible;
    }

    private int pages;
    private boolean audible;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Book() {
    }
}
