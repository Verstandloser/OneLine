package events;

import me.main.GameManager;
import me.main.Main;
import me.main.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JoinEvents implements Listener {

    public JoinEvents(Main main) {
    }

    @EventHandler
    public void ontpSpawn(PlayerJoinEvent e){

        Player p = e.getPlayer();
        if(GameManager.LOBBY){

            File file = new File("plugins/OneLine", "spawn.yml");
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            if(file.exists()){

                String world = cfg.getString("welt");

                Location loc = p.getLocation();
                loc.setWorld(Bukkit.getWorld(world));
                loc.setX(cfg.getDouble("x"));
                loc.setY(cfg.getDouble("y"));
                loc.setZ(cfg.getDouble("z"));
                loc.setYaw((float) cfg.getDouble("yaw"));
                loc.setPitch((float) cfg.getDouble("pitch"));

                p.teleport(loc);
                e.setJoinMessage(Main.prefix+"§7Der Spieler §a"+p.getName()+" §7hat das Spiel betreten!");

            }else{
                p.sendMessage(Main.prefix+"§cDer Spawn wurde noch nicht gesetzt");
            }
        }else if(GameManager.INGAME){

            Main.spec.add(p);
            Main.sb.getTeam("03Spec").addPlayer(p);
            p.setScoreboard(Main.sb);

            GameManager.tpMap(p);
            e.setJoinMessage("");

            p.setAllowFlight(true);
            p.setFlying(true);

            for(Player all : Bukkit.getOnlinePlayers()){
                all.hidePlayer(p);
            }

        }else{
            p.kickPlayer("§cDie Runde ist bereits vorbei!");
            e.setJoinMessage("");
        }
    }

    @EventHandler
    public void setDefaultPlayer(PlayerJoinEvent e){
        Player p = e.getPlayer();
        p.setGameMode(GameMode.SURVIVAL);
        p.setAllowFlight(false);
        p.setFlying(false);
        p.getInventory().clear();
        p.setFoodLevel(20);
        p.setHealth(20);
        if(Bukkit.getOnlinePlayers().size()==1){
            Main.spieler1.add(p);
            Main.sb.getTeam("01Blau").addPlayer(p);
            p.setScoreboard(Main.sb);
        }else if(Bukkit.getOnlinePlayers().size()==2){
            Main.spieler2.add(p);
            Main.sb.getTeam("02Rot").addPlayer(p);
            p.setScoreboard(Main.sb);
        }
    }

    @EventHandler
    public void onStartCountdown(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(GameManager.LOBBY){

            if(Bukkit.getOnlinePlayers().size()>=2){
                for (Player all : Bukkit.getOnlinePlayers()){
                    all.setLevel(15);
                    GameManager.startCountdown(all);
                }
            }

        }
    }

}
