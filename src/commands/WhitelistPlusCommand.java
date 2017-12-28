package commands;

import Utilities.CWPermissions;
import Utilities.WPUtils;
import me.signifies.birb.WhitelistPlus;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;


/**
 * Created by Signifies on 12/27/2017 - 13:12.
 */
public class WhitelistPlusCommand extends WPUtils implements CommandExecutor
{
    private WhitelistPlus instance;
    public WhitelistPlusCommand(WhitelistPlus instance)
    {
        this.instance = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String args[])
    {
        if(!sender.hasPermission(CWPermissions.COMMAND_GENERAL))
        {
            sender.sendMessage(color(instance.getConfig().getString("permission-messages.command")));
        }else if(args.length <1)
        {
            sender.sendMessage(color(instance.getConfig().getString("permission-messages.argument-error")));
        }else
        {
            switch (args[0].toLowerCase())
            {
                case "reload":
                case "rl":
                    if(!sender.hasPermission(CWPermissions.RELOAD))
                    {
                        sender.sendMessage(color(instance.getConfig().getString("permission-messages.command")));
                    }else
                    {
                        instance.reloadConfig();
                        sender.sendMessage(color(instance.getConfig().getString("permission-messages.config-reload")));
                    }
                    break;
                case "help":
                case "?":
                    sender.sendMessage(color("&a===============&b[Help&b]&a==============="));
                    sender.sendMessage(color("&b/whitelistmsg <message> &cPermission: &r"+CWPermissions.MSG_COMMAND));
                    sender.sendMessage(color("&b/whitelistplus enforce &cPermission: &r"+ CWPermissions.ENFORCE_USE));
                    sender.sendMessage(color("&b/whitelistplus status"));
                    sender.sendMessage(color("&b/whitelistplus help &a&l- &7Displays the menu you are seeing now..."));
                    sender.sendMessage(color("&b/whitelistplus reload &cPermission: &r"+CWPermissions.RELOAD));
                    sender.sendMessage(color("&fOther permissions: &b"+CWPermissions.COMMAND_GENERAL + " " + CWPermissions.ENFORCE_BYPASS + " "+ CWPermissions.ENFORCE_USE + " "+ CWPermissions.NOTIFY));
                    sender.sendMessage(color("&7Replacement Tags:&f {name} {uuid} {admin} {whitelist_status} {whitelist_count}"));
                    break;
                case "enforce":
                    if(!sender.hasPermission(CWPermissions.ENFORCE_USE))
                    {
                        sender.sendMessage(color(instance.getConfig().getString("permission-messages.command")));
                    }else
                    {
                        for(Player p : Bukkit.getServer().getOnlinePlayers())
                        {
                            //TextComponent wlist = new TextComponent("[players]");
                            if (!p.isWhitelisted() && !(p.hasPermission(CWPermissions.ENFORCE_BYPASS)))
                            {

                                //wlist.setColor(ChatColor.BLUE);
                                //wlist.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("-> " +p.getName()).create()));
                                p.kickPlayer(color(instance.getConfig().getString("Messages.enforce-kick")));
                            }

                            if(p.hasPermission(CWPermissions.NOTIFY))
                            {
                                String fix = instance.getConfig().getString("Messages.enforce").replace("{admin}",sender.getName());

                                //fix = fix.replace("{removed_players}",wlist.toString());
                                p.sendMessage(color(fix));
                               // p.spigot().sendMessage(wlist);
                            }
                        }
                    }
                    break;
                case "status":
                    boolean wl = Bukkit.getServer().hasWhitelist();
                    int wlAmt = Bukkit.getServer().getWhitelistedPlayers().size();
                    String value = wl ? color(instance.getConfig().getString("Messages.whitelist")): color(instance.getConfig().getString("Messages.no-whitelist"));
                    String result = color(instance.getConfig().getString("Messages.status"));
                    result = result.replace("{whitelist_status}",value);
                    result = result.replace("{whitelist_count}",""+wlAmt);
                    sender.sendMessage(result);
            }
        }
        return true;
    }
}
