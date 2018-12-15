package commands;

import me.main.Main;
import me.main.MySQL;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CMD_setlobby implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {

        if(cs instanceof Player){
            Player p = (Player) cs;
            if(Main.setup.contains(p)){

                File file = new File("plugins/OneLine", "spawn.yml");
                FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                Location loc = p.getLocation();
                cfg.set("welt", loc.getWorld().getName());
                cfg.set("x", loc.getX());
                cfg.set("y", loc.getY());
                cfg.set("z", loc.getZ());
                cfg.set("yaw", loc.getYaw());
                cfg.set("pitch", loc.getPitch());

                p.sendMessage(Main.prefix+"§7Du hast den Spawn gesetzt.");

                try {
                    cfg.save(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else{
                p.sendMessage(Main.prefix+"§cDu bist nicht im Setup-Modus!");
            }
        }

        return true;
    }
}
