package events;

import me.main.GameManager;
import me.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;

public class QuitEvents implements Listener {


    public QuitEvents(Main main) {
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if(GameManager.INGAME){
            if(!Main.spec.contains(p)){

                e.setQuitMessage(Main.prefix+"§7Der Spieler §c"+p.getName()+" §7hat das Spiel verlassen!");
                GameManager.INGAME=false;
                GameManager.ENDING=true;
                for(Player all : Bukkit.getOnlinePlayers()){

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

                        all.teleport(loc);

                    }else{
                        p.sendMessage(Main.prefix+"§cDer Spawn wurde noch nicht gesetzt");
                    }

                    all.sendMessage(Main.prefix+"§cDer Server startet in §910 §cSekunden neu!");
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                        @Override
                        public void run() {

                            all.kickPlayer("§cDer Server startet neu!");
                            Bukkit.shutdown();
                        }
                    }, 10*20);
                }

            }else{
                e.setQuitMessage("");
            }
        }else{
            e.setQuitMessage(Main.prefix+"§7Der Spieler §c"+p.getName()+" §7hat das Spiel verlassen!");
        }
    }

}
