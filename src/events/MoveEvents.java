package events;

import me.main.GameManager;
import me.main.Main;
import me.main.MySQL;
import me.main.StatsSystem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoveEvents implements Listener {

    public MoveEvents(Main main) {
    }

    @EventHandler
    public void onDeathHigh(PlayerMoveEvent e){

        Player p = e.getPlayer();

        if(GameManager.INGAME){
            if(Main.currentMap.equalsIgnoreCase("")){
                File stan = new File("plugins/OneLine", "standard.yml");
                FileConfiguration stancfg = YamlConfiguration.loadConfiguration(stan);

                File file = new File("plugins/OneLine/Maps", stancfg.getString("Map")+".yml");
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

                if(p.getLocation().getY() < cfg.getDouble("death")){

                    addDeath(p.getName());

                    if(Main.spieler1.contains(p)){

                        Main.rot = Main.rot+1;

                        ItemStack block = new ItemStack(Material.SANDSTONE);
                        ItemMeta bm = block.getItemMeta();
                        bm.setDisplayName("§7Blöcke");
                        block.setAmount(3);
                        block.setItemMeta(bm);

                        p.getInventory().setItem(1, block);

                        for(Player all : Bukkit.getOnlinePlayers()){
                            all.sendMessage(Main.prefix+"§cTeam Rot §7hat die Runde gewonnen!");
                            GameManager.tpMap(all);

                            if(!Main.spec.contains(all)){
                                if(p!=all){
                                    addKill(all.getName());
                                }
                            }
                        }
                    }else if(Main.spieler2.contains(p)){
                        Main.blau = Main.blau+1;

                        ItemStack block = new ItemStack(Material.SANDSTONE);
                        ItemMeta bm = block.getItemMeta();
                        bm.setDisplayName("§7Blöcke");
                        block.setAmount(3);
                        block.setItemMeta(bm);

                        p.getInventory().setItem(1, block);

                        for(Player all : Bukkit.getOnlinePlayers()){
                            all.sendMessage(Main.prefix+"§9Team Blau §7hat die Runde gewonnen!");
                            GameManager.tpMap(all);

                            if(!Main.spec.contains(all)){
                                if(p!=all){
                                    addKill(all.getName());
                                }
                            }
                        }
                    }else{
                        GameManager.tpMap(p);
                    }
                }
            }else{
                File file = new File("plugins/OneLine/Maps", Main.currentMap+".yml");
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

                if(p.getLocation().getY() < cfg.getDouble("death")){

                    addDeath(p.getName());

                    if(Main.spieler1.contains(p)){

                        Main.rot = Main.rot+1;

                        ItemStack block = new ItemStack(Material.SANDSTONE);
                        ItemMeta bm = block.getItemMeta();
                        bm.setDisplayName("§7Blöcke");
                        block.setAmount(3);
                        block.setItemMeta(bm);

                        p.getInventory().setItem(1, block);

                        for(Player all : Bukkit.getOnlinePlayers()){
                            all.sendMessage(Main.prefix+"§cTeam Rot §7hat die Runde gewonnen!");
                            GameManager.tpMap(all);

                            if(!Main.spec.contains(all)){
                                if(p!=all){
                                    addKill(all.getName());
                                }
                            }
                        }
                    }else if(Main.spieler2.contains(p)){
                        Main.blau = Main.blau+1;

                        ItemStack block = new ItemStack(Material.SANDSTONE);
                        ItemMeta bm = block.getItemMeta();
                        bm.setDisplayName("§7Blöcke");
                        block.setAmount(3);
                        block.setItemMeta(bm);

                        p.getInventory().setItem(1, block);

                        for(Player all : Bukkit.getOnlinePlayers()){
                            all.sendMessage(Main.prefix+"§9Team Blau §7hat die Runde gewonnen!");
                            GameManager.tpMap(all);

                            if(!Main.spec.contains(all)){
                                if(p!=all){
                                    addKill(all.getName());
                                }
                            }
                        }
                    }else{
                        GameManager.tpMap(p);
                    }
                }
            }
        }

    }

    private void addKill(String player) {
        if(isInTable(player)){
            int kills = StatsSystem.getKills(player);
            int deaths = StatsSystem.getDeaths(player);
            kills = kills+1;
            MySQL.update("DELETE FROM stats WHERE Spielername='"+player+"'");
            MySQL.update("INSERT INTO stats (Spielername, Kills, Deaths) VALUES ('"+player+"','"+kills+"','"
                    +deaths+"')");
        }else{
            int kills = StatsSystem.getKills(player);
            int deaths = StatsSystem.getDeaths(player);
            kills = kills+1;
            MySQL.update("INSERT INTO stats (Spielername, Kills, Deaths) VALUES ('"+player+"','"+kills+"','"
                    +deaths+"')");
        }
    }

    private void addDeath(String player) {
        if(isInTable(player)){
            int kills = StatsSystem.getKills(player);
            int deaths = StatsSystem.getDeaths(player);
            deaths = deaths+1;
            MySQL.update("DELETE FROM stats WHERE Spielername='"+player+"'");
            MySQL.update("INSERT INTO stats (Spielername, Kills, Deaths) VALUES ('"+player+"','"+kills+"','"
                    +deaths+"')");
        }else{
            int kills = StatsSystem.getKills(player);
            int deaths = StatsSystem.getDeaths(player);
            deaths = deaths+1;
            kills = kills+1;
            MySQL.update("INSERT INTO stats (Spielername, Kills, Deaths) VALUES ('"+player+"','"+kills+"','"
                    +deaths+"')");
        }
    }

    private boolean isInTable(String player){
        ResultSet rs = MySQL.getResult("SELECT * FROM stats WHERE Spielername='"+player+"'");
        try {
            while (rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
