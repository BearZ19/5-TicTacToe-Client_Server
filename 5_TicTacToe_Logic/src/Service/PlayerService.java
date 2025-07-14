package Service;

import DM.Player;
import Dao.IDao;

import javax.naming.AuthenticationException;
import java.util.List;

public class PlayerService
{
    private final IDao<Player> playerDao;

    public PlayerService(IDao<Player> playerDao) {
        this.playerDao = playerDao;
    }

    // Register player with username, email, and password
    public void registerPlayer(String username, String password) throws IllegalArgumentException
    {

        if (username == null || username.isBlank())
            throw new IllegalArgumentException("Username cannot be empty");

        if (password == null || password.length() < 6)
            throw new IllegalArgumentException("Password must be at least 6 characters");

        Player newPlayer = new Player(username, password);

        List<Player> existingPlayers = playerDao.loadAll();
        for (Player p : existingPlayers)
        {
            if (p.getUsername().equalsIgnoreCase(username))
                throw new IllegalArgumentException("Username already exists");
        }

        playerDao.save(newPlayer);
    }

    // Login method throws an exception if credentials don't match
    public Player login(String username, String password) throws AuthenticationException {
        List<Player> players = playerDao.loadAll();
        for (Player p : players) {
            if (p.getUsername().equals(username) && p.getPassword().equals(password)) {
                return p;
            }
        }
        throw new AuthenticationException("Invalid username or password");
    }

    // Remove player
    public void removePlayer(Player player) {
        playerDao.delete(player);
    }
}
