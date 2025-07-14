package Server;

import DM.Player;
import Dao.IDao;
import Dao.MyDMFileImpl;
import Service.PlayerService;

import javax.naming.AuthenticationException;

public class ServerDriver {
    public static void main(String[] args) throws AuthenticationException
    {
        IDao<Player> playerDao = new MyDMFileImpl(); // your implementation
        PlayerService playerService = new PlayerService(playerDao);
        Server server = new Server(12345, playerService);
        server.run();
    }
}
