package me.main;

import commands.*;
import events.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends JavaPlugin {

    public static String prefix = "§f[§3OneLine§f] ";
    public static ArrayList<Player> setup = new ArrayList<>();
    public static boolean SETUP;
    public static String currentMap = "";

    public static ArrayList<Player> spieler1 = new ArrayList<>();
    public static ArrayList<Player> spieler2 = new ArrayList<>();
    public static ArrayList<Player> spec = new ArrayList<>();

    public static int blau;
    public static int rot;

    public static Main main;

    public static Scoreboard sb;

    @Override
    public void onEnable() {

        sb = Bukkit.getScoreboardManager().getNewScoreboard();
        sb.registerNewTeam("01Blau").setPrefix("§9");
        sb.registerNewTeam("02Rot").setPrefix("§c");
        sb.registerNewTeam("03Spec").setPrefix("§7");

        createFile();
        registerListeners();
        registerCommands();
        MySQL.connect();
        SETUP=true;
        GameManager.LOBBY=true;
        GameManager.INGAME=false;
        GameManager.ENDING=false;

        System.out.print("[OneLine] aktiviert!");

        main = this;
    }

    public static Main getPlugin(){
        return main;
    }

    private void createFile(){
        File file = new File("plugins/OneLine", "config.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if(!file.exists()){
            cfg.set("host", "localhost");
            cfg.set("database", "Datenbank");
            cfg.set("user", "root");
            cfg.set("password", "PASSWORT");
            try {
                cfg.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void registerCommands(){
        getCommand("setup").setExecutor(new CMD_setup());
        getCommand("forcemap").setExecutor(new CMD_forcemap());
        getCommand("setlobby").setExecutor(new CMD_setlobby());
        getCommand("setmap").setExecutor(new CMD_setmap());
        getCommand("setstandard").setExecutor(new CMD_setstandard());
        getCommand("stats").setExecutor(new CMD_stats());
    }

    private void registerListeners(){
        Bukkit.getPluginManager().registerEvents(new LoginEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new JoinEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new MoveEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new QuitEvents(this), this);
        Bukkit.getPluginManager().registerEvents(new OtherEvents(this), this);
    }
}
