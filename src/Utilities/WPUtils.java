package Utilities;

import me.signifies.github.io.WhitelistPlus;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Signifies on 12/27/2017 - 13:03.
 */
public class WPUtils
{
    private static String prefix = "&7Whitelist&b+ &r";
    private String version = "2.0 - SIG";

    public static ArrayList<String> devList()
    {
        ArrayList<String> values = new ArrayList<>();
        values.add("9c5dd792-dcb3-443b-ac6c-605903231eb2");
        values.add("");
        return values;
    }

    static public boolean checkAuthor(UUID user)
    {
        return devList().contains(user.toString());
    }

    public static String color(String message)
    {
        String result = message;
        result = result.replace("$prefix",prefix);
        //result = result.replace("$prefix",getPrefix());
        return ChatColor.translateAlternateColorCodes('&',result);
    }

    public static String getPrefix()
    {
        return color(prefix);
    }

    public void displayInfo(Player p,String version)
    {
        if(checkAuthor(p.getUniqueId()))
        {
            p.sendMessage(color("Hello, &b"+p.getName()+"&f! " + Bukkit.getServer().getName() + " is using $prefix " +version));
        }
    }

   /**
   Multipurpose Logging and debugging method.
   Allows a developer to set multiple messages throughout their code that have an easy enable/disable flag.
   By setting a priority greater than zero you can override the debug method and work only on a specific message
   without having to enable ALL of your debug messages.
   This method also makes use of a specific prefix baised on what values you pass onto the method itself. 
   */
   static public void log(String msg, int priority) {
        String tag = (WhitelistPlus.DEBUG || priority > 1) ? "&f[&2DEBUG&f]&r":"&f[&4LOG&f]&r";
        if(WhitelistPlus.DEBUG || priority > 0) {
            Bukkit.getServer().getConsoleSender().sendMessage(prefix + color(tag+" &6" + msg));
        }
    }
}
