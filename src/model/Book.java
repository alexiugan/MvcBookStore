package model;

import java.util.Observable;

@SuppressWarnings("deprecation")
public class Book  extends Observable {
    private int id;
    private String title;
    private String author;
    private String genre;
    private int year;
    private int price;
    private int stock;

    public Book(int id, String title, String author, String genre, int year, int price, int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.price = price;
        this.stock = stock;
    }

    public void updateBook(int id, String title, String author, String genre, int year, int price, int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.year = year;
        this.price = price;
        this.stock = stock;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
        setChanged();
        notifyObservers();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        setChanged();
        notifyObservers();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        setChanged();
        notifyObservers();
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
        setChanged();
        notifyObservers();
    }


    public int getPrice() {
        return price;
    }


    public void setPrice(int price) {
        this.price = price;
        setChanged();
        notifyObservers();
    }


    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock=stock;
        setChanged();
        notifyObservers();
    }

    public void setGenre(String genre)
    {
        this.genre = genre;
        setChanged();
        notifyObservers();
    }

    public String getGenre()
    {
        return genre;
    }

    public String toString()
    {
        return "id: " + id + " title: " + title + " author: " + author + " year: " + year + " price: " + price + " stock: " + stock;
    }

    public void updateBook(int id, String title, String author, int year, int price, int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
        this.stock = stock;
        setChanged();
        notifyObservers();
    }

    public boolean isDefault()
    {
        if(getTitle().equals("") || getAuthor().equals("") || getGenre().equals("") || getYear()==-1 || getPrice()==-1 || getStock()==-1)
            return true;
        return false;

    }
}

