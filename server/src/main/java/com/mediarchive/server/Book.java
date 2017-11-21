package com.mediarchive.server;

import javax.persistence.Entity;

@Entity
public class Book extends Media {

    private int currentPage;

    public Book(String id) {
        setId(id);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
}
