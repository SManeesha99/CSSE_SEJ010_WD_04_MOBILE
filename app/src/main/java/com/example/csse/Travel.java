package com.example.csse;

public class Travel {

    private String email;
    private String id;
    private String source;

    public Travel(String email, String id, String source) {
        this.email = email;
        this.id = id;
        this.source = source;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
