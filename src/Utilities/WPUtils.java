package Utilities;

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
    private String version = "1.0 - SIG";

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

}
