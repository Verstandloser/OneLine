package commands;

import me.main.GameManager;
import me.main.Main;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_setup implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {

        if(cs instanceof Player){
            Player p = (Player) cs;
            if(p.hasPermission("system.start")){

                if(GameManager.LOBBY){

                    if(!Main.setup.contains(p)){

                        Main.setup.add(p);
                        Main.SETUP=true;
                        p.sendMessage(Main.prefix+"§7Der Setup Modus wurde §aaktiviert§7!");
                        p.setAllowFlight(true);
                        p.setGameMode(GameMode.CREATIVE);

                    }else{
                        Main.setup.remove(p);
                        Main.SETUP=false;
                        p.sendMessage(Main.prefix+"§7Der Setup Modus wurde §cdeaktiviert§7!");
                        p.setAllowFlight(false);
                        p.setGameMode(GameMode.SURVIVAL);
                    }

                }else{
                    p.sendMessage(Main.prefix+"§cDas Spiel hat bereits gestartet!");
                }

            }else{
                cs.sendMessage(Main.prefix+"§cDu hast nicht ausreichend Rechte, um diesen Befehl zu verwenden!");
            }
        }

        return true;
    }

}
