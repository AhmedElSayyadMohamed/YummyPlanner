package com.example.yummyplanner.data.auth.model;

public class User {
    private String name;
    private String email;
    private String password;
    private long createdAt;
    private String avatarUrl;

    public User() { }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.createdAt = System.currentTimeMillis();
    }

    public User(String name, String email, String avatarUrl) {
        this.name = name;
        this.email = email;
        this.avatarUrl = avatarUrl;
    }


    public String getAvatarUrl() { return avatarUrl; }

    public void setPassword(){
        this.password = password;
    }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public long getCreatedAt() { return createdAt; }
    public void setCreatedAt(long createdAt) { this.createdAt = createdAt; }
}
