package commands;

import Utilities.CWPermissions;
import Utilities.WPUtils;
import me.signifies.birb.WhitelistPlus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by Signifies on 12/27/2017 - 13:09.
 */
public class WhitelistMsgCommand extends WPUtils implements CommandExecutor
{
    private WhitelistPlus instance;
    public WhitelistMsgCommand(WhitelistPlus main)
    {
        instance = main;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String args[])
    {

        if(!sender.hasPermission(CWPermissions.MSG_COMMAND))
        {
            sender.sendMessage(color(instance.getConfig().getString("permission-messages.command")));
        }else if(args.length < 1)
        {
            sender.sendMessage(color("&f/whitelistmsg <message> || &cPermission: &r"+CWPermissions.MSG_COMMAND));
            sender.sendMessage(color("&7Please enter the message you want displayed!"));
            sender.sendMessage(color("&7Messages can contain functions such as &b{name}&7, &b{uuid}&7, and &b\"\n\"."));
        }else
        {
            StringBuilder stringBuilder = new StringBuilder();
            for(String arg : args)
            {
                stringBuilder.append(arg + " ");
            }
            String msg = stringBuilder.toString();
            msg = msg.replace("{admin}",sender.getName());
            instance.getConfig().set("Messages.kick-message",msg);
            instance.saveConfig();
            String newMsg = instance.getConfig().getString("Messages.kick-message");
            sender.sendMessage(color("Whitelist message has been set to "+newMsg));
        }
        return true;
    }
}
