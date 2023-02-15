package com.example.librarysystemmanagement;

public class Modeluser {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDateofbrith() {
        return dateofbrith;
    }

    public void setDateofbrith(String dateofbrith) {
        this.dateofbrith = dateofbrith;
    }

    public String getBooktoken() {
        return booktoken;
    }

    public void setBooktoken(String booktoken) {
        this.booktoken = booktoken;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Modeluser(String id, String name, String phone, String email,
                     String dateofbrith, String booktoken, String image) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.dateofbrith = dateofbrith;
        this.booktoken = booktoken;
        this.image = image;
    }

    String id , name , phone , email , dateofbrith , booktoken ,image;

    public Modeluser(){}
}
