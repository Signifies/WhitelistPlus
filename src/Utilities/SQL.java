package Utilities;

import org.bukkit.ChatColor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {
    public Connection connection;
    private String host,user,pass,database;
    private int port;

    /**
     *
     * @param enabled Checks to see if MySQL is enabled via configuration file...
     * @param address Host address of the database.
     * @param port Port of the database.
     * @param username Username for the current database.
     * @param password Password for the current database.
     * @param database Specific database we want to connect to.
     */
    public SQL(boolean enabled,String address,int port, String username, String password, String database) throws SQLException {
        if(enabled)
        {
            this.host = address;
            this.port = port;
            this.user = username;
            this.pass = password;
            this.database = database;

            try {
                Class.forName("com.mysql.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + this.host +":"+this.port+ "/" + this.database + "?user=" + this.user + "&password=" + this.pass);
                //Debug.log("&4Connection successful.",1);
                System.out.println(ChatColor.GREEN +""+ChatColor.ITALIC+ "Database connection successful!");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(ChatColor.RED +"Error when connecting to the database [!] Check Error:" +e.getMessage());
            }
        }else
        {
            System.out.println(ChatColor.RED + "Database has been disabled in Config.yml!");
//            Debug.log(Debug.LOG+"&4",1);
        }
    }

    /**
     *
     * @return Gets the Host address.
     */
    public String getHost() {
        return host;
    }

    /**
     *
     * @return Gets the port.
     */
    public int getPort()
    {
        return this.port;
    }

    /**
     *
     * @return Gets the database User
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @return Gets the database Password.
     */
    public String getPassword() {
        return pass;
    }

    /**
     *
     * @return Gets the specific database we want to connect to.
     */
    public String getDatabase() {
        return database;
    }


   public Connection getConnection() {
        return connection;
    }

    public boolean isConnected()
    {
        return connection == null;
    }

    public void closeCurrentConnection() throws SQLException
    {
        this.connection.close();
    }

    /**
     * Create an SQL table on the fly.
     * @param sql Takes sql parameters in the form of a string and executes them.
     */
    public void createTable(String sql)
    {
        try
        {
            getConnection().prepareStatement(sql).executeUpdate();
            System.out.println(ChatColor.RED+ "Created table!");
            System.out.println(ChatColor.AQUA+"Query ran: "+sql);
        }catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println(ChatColor.RED +"Error when creating tables [!] Check Error:" +e.getMessage());
        }
    }

    /**
     *
     * @param log Checks whether or not the user wants console log displayed...
     * @param sql Takes SQL parameters in the form of a string and executes them.
     */
    public void createTable(boolean log,String sql)
    {
        try
        {
            getConnection().prepareStatement(sql).executeUpdate();
            if(log)
            {
                System.out.println(ChatColor.RED+ "Created table!");
                System.out.println(ChatColor.AQUA+"Query ran: "+sql);
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println(ChatColor.RED +"Error when creating tables [!] Check Error:" +e.getMessage());
        }
    }

}