package me.signifies.github.io;

import Utilities.SQL;
import Utilities.WPUtils;
import commands.WhitelistMsgCommand;
import commands.WhitelistPlusCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

/**
 * Created by Signifies on 12/27/2017.
 */
public class WhitelistPlus extends JavaPlugin
{
    public PluginDescriptionFile pdfFile = this.getDescription();
    public static boolean DEBUG = true;
    private SQL sql;
    public void onEnable()
    {
        configuration();
        if(SQLEnabled()){
            try {
                sql = new SQL(getConfig().getBoolean("Database.Enabled"), getConfig().getString("Database.host"), getConfig().getInt("Database.port"), getConfig().getString("Database.username"), getConfig().getString("Database.password"), getConfig().getString("Database.database"));
            }catch (SQLException e) {
                WPUtils.log(e.getMessage(),1);
            }
            sql.createTable(DEBUG,"CREATE TABLE IF NOT EXISTS WHITELIST (UUID varchar(45), USER varchar(50) SERVER varchar(25));");
        }else
        {
            WPUtils.log(SQLEnabledResponse(),1);
        }
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new WhitelistPlusEvent(this),this);
        getCommand("whitelistmsg").setExecutor(new WhitelistMsgCommand(this));
        getCommand("whitelistplus").setExecutor(new WhitelistPlusCommand(this));

    }

    public void configuration()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    public void onDisable()
    {

    }

    public boolean SQLEnabled() {
        return getConfig().getBoolean("Database.enabled");
    }

    public String SQLEnabledResponse() {
        return SQLEnabled() ? "Database is Enabled!" : ChatColor.RED+"[!] Warning! Database is Disabled! [!]";
    }

    public String getServerID() {
        return getConfig().getString("Settings.server-id");
    }

    public SQL getSQL() {
        return sql;
    }
}
