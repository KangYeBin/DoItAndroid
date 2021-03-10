package org.techtown.doitmission22;

public class BookInfo {
    String title;
    String author;
    String contents;

    public BookInfo(String title, String author, String contents) {
        this.title = title;
        this.author = author;
        this.contents = contents;
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String toString() {
        return "title : " + title + ", author : " + author + ", contents : " + contents;
    }
}
