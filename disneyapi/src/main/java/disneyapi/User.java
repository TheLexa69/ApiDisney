package disneyapi;

import java.io.Serializable;

public class User implements Serializable{
    private String username;
    private String role;
    private String id;

    public User(String id, String username, String role) {
        this.id = id;
        this.username = username;
        this.role = role;
    }
    
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}