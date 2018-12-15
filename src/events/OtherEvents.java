package events;

import me.main.GameManager;
import me.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class OtherEvents implements Listener {

    public OtherEvents(Main main) {
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player){
            if(e.getDamager() instanceof Player){
                Player t = (Player) e.getDamager();
                if(GameManager.INGAME){
                    if(!Main.spec.contains(t)){
                        ((Player) e.getEntity()).setHealth(20);
                        e.setDamage(0);
                    }else{
                        e.setCancelled(true);
                    }
                }else{
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(GameManager.INGAME){
            e.setDamage(0);
        }
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e){
        e.setCancelled(true);
    }

    @EventHandler
    public void onBuild(BlockPlaceEvent e){
        if(Main.setup.contains(e.getPlayer())){
            e.setCancelled(false);
        }else{

            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    e.getBlock().setType(Material.REDSTONE_BLOCK);
                }
            }, 3*20);

            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
                @Override
                public void run() {
                    e.getBlock().setType(Material.AIR);
                }
            }, 5*20);

        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if(Main.setup.contains(e.getPlayer())){
            e.setCancelled(false);
        }else{
            e.setCancelled(true);
        }
    }

}
