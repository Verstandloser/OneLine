package me.main;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

public class GameManager {

    public static boolean LOBBY;
    public static boolean INGAME;
    public static boolean ENDING;

    public static void startCountdown(Player p){


        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                if(p.getLevel()==0){
                    if(Bukkit.getOnlinePlayers().size()==2){
                        tpMap(p);
                        GameManager.sendActionbar(p);
                        LOBBY=false;
                        INGAME=true;

                        ItemStack stick = new ItemStack(Material.STICK);
                        ItemMeta sm = stick.getItemMeta();
                        sm.addEnchant(Enchantment.KNOCKBACK, 1, true);
                        sm.setDisplayName("§7Stick");
                        stick.setItemMeta(sm);

                        ItemStack block = new ItemStack(Material.SANDSTONE);
                        ItemMeta bm = block.getItemMeta();
                        bm.setDisplayName("§7Blöcke");
                        block.setAmount(3);
                        block.setItemMeta(bm);

                        p.getInventory().setItem(0, stick);
                        p.getInventory().setItem(1, block);

                    }else{
                        p.sendMessage(Main.prefix+"§cDer Countdown wurde abgebrochen");
                    }
                }else{
                    p.setLevel(p.getLevel()-1);
                    startCountdown(p);
                }
            }
        }, 20);

    }

    public static void tpMap(Player p){

        if(Main.currentMap.equalsIgnoreCase("")){

            File stan = new File("plugins/OneLine", "standard.yml");
            FileConfiguration stancfg = YamlConfiguration.loadConfiguration(stan);
            File file = new File("plugins/OneLine/Maps", stancfg.getString("Map")+".yml");
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

            if(Main.spieler1.contains(p)){
                String world = cfg.getString("world");

                Location loc = p.getLocation();
                loc.setWorld(Bukkit.getWorld(world));
                loc.setX(cfg.getDouble("x1"));
                loc.setY(cfg.getDouble("y1"));
                loc.setZ(cfg.getDouble("z1"));
                loc.setYaw((float) cfg.getDouble("yaw1"));
                loc.setPitch((float) cfg.getDouble("pitch1"));

                p.teleport(loc);
            }else if(Main.spieler2.contains(p)){
                String world = cfg.getString("world");

                Location loc = p.getLocation();
                loc.setWorld(Bukkit.getWorld(world));
                loc.setX(cfg.getDouble("x2"));
                loc.setY(cfg.getDouble("y2"));
                loc.setZ(cfg.getDouble("z2"));
                loc.setYaw((float) cfg.getDouble("yaw2"));
                loc.setPitch((float) cfg.getDouble("pitch2"));

                p.teleport(loc);
            }else if(Main.spec.contains(p)){
                String world = cfg.getString("world");

                Location loc = p.getLocation();
                loc.setWorld(Bukkit.getWorld(world));
                loc.setX(cfg.getDouble("x3"));
                loc.setY(cfg.getDouble("y3"));
                loc.setZ(cfg.getDouble("z3"));
                loc.setYaw((float) cfg.getDouble("yaw3"));
                loc.setPitch((float) cfg.getDouble("pitch3"));

                p.teleport(loc);
            }

        }else{

            File file = new File("plugins/OneLine/Maps", Main.currentMap+".yml");
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            if(file.exists()){

                if(Main.spieler1.contains(p)){
                    String world = cfg.getString("world");

                    Location loc = p.getLocation();
                    loc.setWorld(Bukkit.getWorld(world));
                    loc.setX(cfg.getDouble("x1"));
                    loc.setY(cfg.getDouble("y1"));
                    loc.setZ(cfg.getDouble("z1"));
                    loc.setYaw((float) cfg.getDouble("yaw1"));
                    loc.setPitch((float) cfg.getDouble("pitch1"));

                    p.teleport(loc);
                    p.sendMessage(Main.prefix+"§7Das Spiel startet");
                }else if(Main.spieler2.contains(p)){
                    String world = cfg.getString("world");

                    Location loc = p.getLocation();
                    loc.setWorld(Bukkit.getWorld(world));
                    loc.setX(cfg.getDouble("x2"));
                    loc.setY(cfg.getDouble("y2"));
                    loc.setZ(cfg.getDouble("z2"));
                    loc.setYaw((float) cfg.getDouble("yaw2"));
                    loc.setPitch((float) cfg.getDouble("pitch2"));

                    p.teleport(loc);
                    p.sendMessage(Main.prefix+"§7Das Spiel startet");
                }else if(Main.spec.contains(p)){
                    String world = cfg.getString("world");

                    Location loc = p.getLocation();
                    loc.setWorld(Bukkit.getWorld(world));
                    loc.setX(cfg.getDouble("x3"));
                    loc.setY(cfg.getDouble("y3"));
                    loc.setZ(cfg.getDouble("z3"));
                    loc.setYaw((float) cfg.getDouble("yaw3"));
                    loc.setPitch((float) cfg.getDouble("pitch3"));

                    p.teleport(loc);
                }

            }else{
                p.sendMessage(Main.prefix+"§cDer Spawn für Map wurde noch nicht gesetzt");
            }

        }

    }

    public static void sendActionbar(Player p){
        IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"\"}")
                .a("§9Team Blau §f» §7"+Main.blau+" §8│ §7"+Main.rot+" §f« §cTeam Rot");
        PacketPlayOutChat packet = new PacketPlayOutChat(iChatBaseComponent, (byte) 2);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);

        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
            @Override
            public void run() {
                sendActionbar(p);
            }
        }, 30);
    }

}
