package model;

public class User {

    private int role;
    private String username;
    private String password;

    public User(int role, String username, String password)
    {
        setRole(role);
        setUsername(username);
        setPassword(password);
    }

    public void setRole(int role)
    {
        this.role = role;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public int getRole()
    {
        return role;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }
}
