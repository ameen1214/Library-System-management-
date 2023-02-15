package com.example.librarysystemmanagement;

public class Modelbook {

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Modelbook(String id) {
        this.id = id;
    }

    String id ;
    String namebook ;
    String authorbook ;
    String tokenby ;

    public String getNamebook() {
        return namebook;
    }

    public void setNamebook(String namebook) {
        this.namebook = namebook;
    }

    public String getAuthorbook() {
        return authorbook;
    }

    public void setAuthorbook(String authorbook) {
        this.authorbook = authorbook;
    }

    public String getTokenby() {
        return tokenby;
    }

    public void setTokenby(String tokenby) {
        this.tokenby = tokenby;
    }

    public String getImagebook() {
        return imagebook;
    }

    public void setImagebook(String imagebook) {
        this.imagebook = imagebook;
    }

    public Modelbook(String id, String namebook, String authorbook, String tokenby, String imagebook) {
        this.id = id;
        this.namebook = namebook;
        this.authorbook = authorbook;
        this.tokenby = tokenby;
        this.imagebook = imagebook;
    }

    String imagebook ;
    public Modelbook(){}
}
