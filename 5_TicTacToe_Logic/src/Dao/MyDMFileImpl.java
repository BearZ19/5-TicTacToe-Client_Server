package Dao;

import Controllers.Config;
import DM.Player;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MyDMFileImpl implements IDao<Player>
{

    private final Gson gson = new Gson();

    @Override
    public void save(Player player)
    {
        List<Player> players = loadAll();
        players.add(player);
        writeToFile(players);
    }

    @Override
    public List<Player> loadAll()
    {
        File file = new File(Config.PLAYER_FILE_PATH);
        if (!file.exists()) return new ArrayList<>();

        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<List<Player>>() {}.getType();
            return gson.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

   // @Override
   // public void update(Player updatedPlayer) {
   //     List<Player> players = loadAll();
   //     for (int i = 0; i < players.size(); i++) {
   //         if (players.get(i).getUsername().equals(updatedPlayer.getUsername())) {
   //             players.set(i, updatedPlayer);
   //             break;
   //         }
   //     }
   //     writeToFile(players);
   // }

    @Override
    public void delete(Player playerToRemove)
    {
        List<Player> players = loadAll();
        players.removeIf(p -> p.getUsername().equals(playerToRemove.getUsername()));
        writeToFile(players);
    }

    private void writeToFile(List<Player> players)
    {
        try (Writer writer = new FileWriter(Config.PLAYER_FILE_PATH))
        {
            gson.toJson(players, writer);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
