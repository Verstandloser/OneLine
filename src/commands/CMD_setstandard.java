package commands;

import me.main.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class CMD_setstandard implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {


        if(cs instanceof Player){
            Player p = (Player) cs;
            if(Main.setup.contains(p)){

                if(args.length == 1){

                    File file = new File("plugins/OneLine", "standard.yml");
                    FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                    File test = new File("plugins/OneLine/Maps", args[0]+".yml");
                    if(test.exists()){
                        cfg.set("Map", args[0]);

                        p.sendMessage(Main.prefix+"§7Die Standard Map wurde auf §a"+args[0]+" §7gesetzt!");

                        try {
                            cfg.save(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        p.sendMessage(Main.prefix+"§cDie Map wurde nicht gefunden!");
                    }

                }else{
                    p.sendMessage(Main.prefix+"§cNutze: §a/setstandard <Mapname>");
                }

            }else{
                p.sendMessage(Main.prefix+"§cDu bist nicht im Setup-Modus!");
            }
        }

        return true;
    }
}
