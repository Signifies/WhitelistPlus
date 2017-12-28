package me.signifies.birb;

import commands.WhitelistMsgCommand;
import commands.WhitelistPlusCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Signifies on 12/27/2017.
 */
public class WhitelistPlus extends JavaPlugin
{
    public PluginDescriptionFile pdfFile = this.getDescription();

    public void onEnable()
    {
        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new WhitelistPlusEvent(this),this);
        getCommand("whitelistmsg").setExecutor(new WhitelistMsgCommand(this));
        getCommand("whitelistplus").setExecutor(new WhitelistPlusCommand(this));
        //TODO potentially add /blacklist command...
        configuration();
    }

    public void onDisable()
    {

    }

    public void configuration()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }


}
