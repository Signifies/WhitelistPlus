package commands;

import Utilities.CWPermissions;
import Utilities.WPUtils;
import me.signifies.github.io.WhitelistPlus;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;

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
                    sender.sendMessage(color("&7/whitelistmsg &b<message> &cPermission: &r"+CWPermissions.MSG_COMMAND));
                    sender.sendMessage(color("&7/whitelistplus &benforce &cPermission: &r"+ CWPermissions.ENFORCE_USE));
                    sender.sendMessage(color("&7/whitelistplus add <user> &cPermission: &r" +CWPermissions.ADD));
                    sender.sendMessage(color("&7/whitelistplus remove <user> &cPermission: &r" +CWPermissions.REM));
      /* For now I'm just going to make this a config option. sender.sendMessage(color("&7/whitelistplus setid &cPermission: &r" +CWPermissions.SET_SERVER)); */
                    sender.sendMessage(color("&7/whitelistplus convert &cPermission: [MUST BE OPERATOR!] &r" +CWPermissions.CONVERT_DATA));
                    sender.sendMessage(color("&7/whitelistplus &bstatus"));
                    sender.sendMessage(color("&7/whitelistplus &bhelp &a&l- &7Displays the menu you are seeing now..."));
                    sender.sendMessage(color("&7/whitelistplus &breload &cPermission: &r"+"" +CWPermissions.RELOAD));
                    sender.sendMessage(color("&7Other permissions: &b"+ CWPermissions.OVERRIDE +" "+CWPermissions.COMMAND_GENERAL + " " + CWPermissions.ENFORCE_BYPASS + " "+ CWPermissions.ENFORCE_USE + " "+ CWPermissions.NOTIFY));
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
                            if (!p.isWhitelisted() && !(p.hasPermission(CWPermissions.ENFORCE_BYPASS)))
                            {
                                p.kickPlayer(color(instance.getConfig().getString("Messages.enforce-kick")));
                            }

                            if(p.hasPermission(CWPermissions.NOTIFY))
                            {
                                String fix = instance.getConfig().getString("Messages.enforce").replace("{admin}",sender.getName());
                                p.sendMessage(color(fix));
                            }
                        }
                    }

                case "add":
                    if(!sender.hasPermission(CWPermissions.ADD)) {
                        sender.sendMessage(color(instance.getConfig().getString("Messages.adduser")));
                    }else {
                        if(instance.SQLEnabled()) {
                            //do sql things
                            if(args.length > 1) {
                                //DO shit
                                try{
                                    PreparedStatement statement = instance.getSQL().getConnection().prepareStatement("");

                                }catch (SQLException e) {

                                }

                            }else {
                                sender.sendMessage(color("&7/wl add <user>"));
                            }
                        }else {
                            sender.sendMessage(instance.SQLEnabledResponse());
                            //TODO or do offline result which we will need since so many people use this plugin.
                        }
                    }
                    break;
                case "remove":

                    if(!sender.hasPermission(CWPermissions.REM)) {
                        sender.sendMessage(color(instance.getConfig().getString("Messages.perm")));
                    }else {
                        if(instance.SQLEnabled()){
                            //do sql things
                            if(args.length > 1) {
                                //remove shit.
                                try{
                                    PreparedStatement statement = instance.getSQL().getConnection().prepareStatement("");

                                }catch (SQLException e) {

                                }

                            }else {
                                sender.sendMessage(color("&7/wl add <user>"));
                            }
                        }else {
                            sender.sendMessage(instance.SQLEnabledResponse());
                            //TODO or do offline result which we will need since so many people use this plugin.
                        }
                    }

                    break;
                case "rm":
                    break;

                case "convert":
                    if(!sender.hasPermission(CWPermissions.CONVERT_DATA) && !sender.isOp()) {
                        sender.sendMessage(color(instance.getConfig().getString("Messages.convert")));
                    }else {
                       if(instance.SQLEnabled()){
                           if(args.length > 1) {// do stuff
                               for (OfflinePlayer player : Bukkit.getServer().getWhitelistedPlayers()) {
                                   try{
                                       PreparedStatement statement = instance.getSQL().getConnection().prepareStatement("");

                                   }catch (SQLException e) {

                                   }
                               }
                           }else {
                               sender.sendMessage(color("&7/wl convert - &4This converts the standard text WHITELIST into the DATABASE SYSTEM. " +
                                       "Only use this if you know what your're doing."));
                           }
                       }else {
                           sender.sendMessage(instance.SQLEnabledResponse());
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
                    result = result.replace("{database}",instance.SQLEnabledResponse());
                    sender.sendMessage(result);
                    break;

                default:
                   sender.sendMessage(color(instance.getConfig().getString("Messages.argument-error")));
                break;
            }
        }
        return true;
    }
}
