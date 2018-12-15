package me.main;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatsSystem {

    public static Integer getKills(String player){
        ResultSet rs = MySQL.getResult("SELECT * FROM stats WHERE Spielername='"+player+"'");
        try {
            while (rs.next()){
                return rs.getInt("Kills");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Integer getDeaths(String player){
        ResultSet rs = MySQL.getResult("SELECT * FROM stats WHERE Spielername='"+player+"'");
        try {
            while (rs.next()){
                return rs.getInt("Deaths");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }



}
