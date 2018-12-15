package commands;

import me.main.StatsSystem;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMD_stats implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender cs, Command command, String s, String[] args) {

        if(cs instanceof Player){
            Player p = (Player) cs;
            if(args.length == 0){
                p.sendMessage("§a§m------------------");
                p.sendMessage("§aDeine Stats: §3");
                p.sendMessage("§7Kills: §c"+StatsSystem.getKills(p.getName()));
                p.sendMessage("§7Deaths: §c"+StatsSystem.getDeaths(p.getName()));
                p.sendMessage("§a§m------------------");
            }else{
                p.sendMessage("§a§m------------------");
                p.sendMessage("§aStats von: §3"+args[0]);
                p.sendMessage("§7Kills: §c"+StatsSystem.getKills(args[0]));
                p.sendMessage("§7Deaths: §c"+StatsSystem.getDeaths(args[0]));
                p.sendMessage("§a§m------------------");

            }
        }

        return true;
    }
}
