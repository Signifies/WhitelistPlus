package me.signifies.github.io;

import Utilities.CWPermissions;
import Utilities.WPUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.UUID;

/**
 * Created by Signifies on 12/27/2017 - 13:07.
 */
public class WhitelistPlusEvent extends WPUtils implements Listener
{
    private WhitelistPlus instance;

    public WhitelistPlusEvent(WhitelistPlus instance)
    {
        this.instance = instance;
    }

    boolean checkWhitelist()
    {
        return Bukkit.getServer().hasWhitelist();
    }

    @EventHandler
    public void join(PlayerJoinEvent event)
    {
        displayInfo(event.getPlayer(),instance.pdfFile.getVersion());
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event) {
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        String config = instance.getConfig().getString("Messages.kick-message");
        config = config.replace("{name}", p.getName());
        config = config.replace("{uuid}", "" + uuid);
        config = config.replace("\n", "\n");
        String alert = instance.getConfig().getString("Messages.whitelist-alert");
        alert = alert.replace("{name}", p.getName());
        alert = alert.replace("{uuid}", "" + uuid);

        // IF WL = TRUE; THEN DO ALLOWANCE; THEN DO DISALLOWANCE.
        // IF WL = TRUE; AND P HAS PERM; THEN ALLOW

        if(instance.SQLEnabled()) { //Might still use this system. 
            //TODO Do SQL stuff
        }else{
            if (checkWhitelist()) {
            /*This logic is important, because we want them to join regardless if they are whitelisted if permission specific control is enabled.*/
                if (checkWhitelist() && p.hasPermission(CWPermissions.OVERRIDE) || (instance.getConfig().getBoolean("Settings.use-permission") && p.hasPermission(instance.getConfig().getString("Settings.permission")))) {
                    event.setResult(PlayerLoginEvent.Result.ALLOWED);
                }else if(!p.isWhitelisted()) {
                    event.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', config));
                    Bukkit.getServer().getConsoleSender().sendMessage(color(alert));
                    for (Player staff : Bukkit.getServer().getOnlinePlayers()) {
                        if (staff.hasPermission(CWPermissions.NOTIFY)) {
                            if (p.isWhitelisted()) {
                                return;
                            }
                            staff.sendMessage(color(alert));
                        }
                    }
                }
            }
        }
    }
}
