	package roles;

import java.util.Scanner;

public abstract class User {
		
    private String id;
    private String username;
    private String password;
    private String name;

    // Constructor that is called by subclasses
    public User(String id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    // Common methods that subclasses can use or override
    public boolean login(String username, String password) {
        // Common login logic (can be overridden by subclasses if needed)
        return this.username.equals(username) && this.password.equals(password);
    }

    // Getters and setters for common attributes
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Abstract method that will be implemented by subclasses
    public abstract boolean displayMenu(Scanner scanner);

    // Other common methods...
}
