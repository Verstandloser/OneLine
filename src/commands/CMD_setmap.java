package commands;

import me.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CMD_setmap implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {

        if(cs instanceof Player){
            Player p = (Player) cs;

            if(Main.setup.contains(p)){

                if(args.length==2){

                    File file = new File("plugins/OneLine/Maps", args[0]+".yml");
                    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                    Location loc = p.getLocation();
                    if(args[1].equalsIgnoreCase("1")){
                        cfg.set("world", p.getWorld().getName());
                        cfg.set("x1", loc.getX());
                        cfg.set("y1", loc.getY());
                        cfg.set("z1", loc.getZ());
                        cfg.set("yaw1", loc.getYaw());
                        cfg.set("pitch1", loc.getPitch());

                        p.sendMessage(Main.prefix+"§7Du hast den Spawn für Spieler §21 §7gesetzt");

                        try {
                            cfg.save(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(args[1].equalsIgnoreCase("2")){
                        cfg.set("x2", loc.getX());
                        cfg.set("y2", loc.getY());
                        cfg.set("z2", loc.getZ());
                        cfg.set("yaw2", loc.getYaw());
                        cfg.set("pitch2", loc.getPitch());

                        p.sendMessage(Main.prefix+"§7Du hast den Spawn für Spieler §22 §7gesetzt");

                        try {
                            cfg.save(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(args[1].equalsIgnoreCase("spec")){
                        cfg.set("x3", loc.getX());
                        cfg.set("y3", loc.getY());
                        cfg.set("z3", loc.getZ());
                        cfg.set("yaw3", loc.getYaw());
                        cfg.set("pitch3", loc.getPitch());

                        p.sendMessage(Main.prefix+"§7Du hast den Spawn für die §2Spectator §7gesetzt");

                        try {
                            cfg.save(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(args[1].equalsIgnoreCase("death")){

                        cfg.set("death", loc.getY()-1);

                        p.sendMessage(Main.prefix+"§7Du hast den §2Todespunkt §7gesetzt");

                        try {
                            cfg.save(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }else{
                    p.sendMessage(Main.prefix+"§cNutze: §a/setmap <Arenaname> 1/2/spec/death");
                }

            }else{
                p.sendMessage(Main.prefix+"§cDu bist nicht im Setup-Modus!");
            }

        }

        return true;
    }
}
