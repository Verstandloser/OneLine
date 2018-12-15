package events;

import me.main.GameManager;
import me.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.ArrayList;
import java.util.Random;

public class LoginEvents implements Listener {

    public LoginEvents(Main main) {
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e){

        Player p = e.getPlayer();
        if(Bukkit.getOnlinePlayers().size() >= 2){

            if(GameManager.INGAME){

                if(p.hasPermission("system.staff")){
                    e.allow();
                }else{
                    e.disallow(null, "§cDas Spiel hat bereits begonnen!");
                }

            }else{
                e.disallow(null, "§cDie Runde ist bereits voll!");
            }

        }

    }

}
