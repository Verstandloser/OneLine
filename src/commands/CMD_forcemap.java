package commands;

import me.main.GameManager;
import me.main.Main;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class CMD_forcemap implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {

        if(cs instanceof Player){
            Player p = (Player) cs;
            if(p.hasPermission("system.forcemap")){

                if(args.length == 1){

                    if(GameManager.LOBBY){

                        File file = new File("plugins/OneLine/Maps", args[0]+".yml");
                        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                        Location loc = p.getLocation();
                        if(file.exists()){

                            if(Main.currentMap.equalsIgnoreCase("")){

                                Main.currentMap = args[0];
                                p.sendMessage(Main.prefix+"§aDie nächste Map wird §7"+args[0]+" §asein!");

                            }else{
                                p.sendMessage(Main.prefix+"§cEs wurde bereits eine Map festgelegt!");
                            }

                        }else{
                            p.sendMessage(Main.prefix+"§cDiese Map gibt es nicht!");
                        }

                    }else{
                        p.sendMessage(Main.prefix+"§cDas Spiel hat bereits gestartet!");
                    }

                }else{
                    p.sendMessage(Main.prefix+"§cNutze: §a/forcemap <Mapname>");
                }

            }else{
                cs.sendMessage(Main.prefix+"§cDu hast nicht ausreichend Rechte, um diesen Befehl zu verwenden!");
            }
        }

        return true;
    }

}
