package DM;

public class Player
{
    private String username;
    private String password;

    // Constructor
    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Player{" +
                "username='" + username + '\'' +
                '\'' +
                '}';
    }
}
