package me.chr1s0143.PostedSC;

/**
 * Created by chris on 14/01/2015.
 */
//TO DO LIST:
//- BROADCAST A POSTS NOTIF' TO PLAYERS WHO HAVEN'T POSTED ON PMC, MCF OR BOTH.

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import java.util.List;
import java.util.logging.Logger;

public class posted extends JavaPlugin {

    SettingsManager settings = SettingsManager.getInstance();
    Logger log;

    public void onEnable() {
        settings.setupNamespmcFile(this);
        settings.getNamespmcFile().options().copyDefaults(true);
        settings.saveDefaultNamespmcFile();
        settings.setupNamesmcfFile(this);
        settings.getNamespmcFile().options().copyDefaults(true);
        settings.saveDefaultNamesmcfFile();
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        log = Logger.getLogger("Minecraft");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
                    if (cmd.getName().equalsIgnoreCase("postedpmc")) {
                        if (player.hasPermission("posted.credit.pmc")) {
                            if (args.length == 0)
                                player.sendMessage(ChatColor.DARK_GREEN + "try doing " + ChatColor.GOLD + "/postedpmc {player name} ");
                    else if (args.length == 1) {
                                OfflinePlayer oplayer = Bukkit.getOfflinePlayer(args[0]);
                                if(oplayer.hasPlayedBefore()) {
                            if (settings.getNamespmcFile().getStringList("Names").contains(args[0])) {
                                player.sendMessage(ChatColor.RED + args[0] + " has already received the rewards for posting on PMC");
                            } else {
                                getServer().dispatchCommand(getServer().getConsoleSender(), "eco give " + args[0] + " " + getConfig().getInt("Payment"));
                                getServer().dispatchCommand(getServer().getConsoleSender(), "enjin addpoints " + args[0] + " " + getConfig().getInt("Points"));
                                List<String> stringpmc = settings.getNamespmcFile().getStringList("Names");
                                stringpmc.add(args[0]);
                                settings.getNamespmcFile().set("Names", stringpmc);
                                player.sendMessage(ChatColor.DARK_GREEN + args[0] + " has been given" + ChatColor.GOLD + " $" + getConfig().getInt("Payment") + ChatColor.DARK_GREEN + " and " + ChatColor.GOLD + getConfig().getInt("Points") + " Enjin points");
                                Bukkit.broadcastMessage(ChatColor.GREEN + "[" + ChatColor.AQUA + "PSC" + ChatColor.GREEN + "] Player " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " has posted on Planet Minecraft! They have received $" + getConfig().getInt("Payment") + " and " + getConfig().getInt("Points") + " Enjin points!");
                                settings.saveNamespmcFile();
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + args[0] + " has never been on the server! Check the player name again, carefully.");
                        }
                    }
                } else player.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command!");

            } else if (cmd.getName().equalsIgnoreCase("postedmcf")) {
                        if (player.hasPermission("posted.credit.mcf")) {
                            if (args.length == 0)
                                player.sendMessage(ChatColor.DARK_GREEN + "try doing " + ChatColor.GOLD + "/postedmcf {player name} ");
                            else if (args.length == 1) {
                                OfflinePlayer oplayer = Bukkit.getOfflinePlayer(args[0]);
                                if (oplayer.hasPlayedBefore()) {
                                    if (settings.getNamesmcfFile().getStringList("Names").contains(args[0])) {
                                        player.sendMessage(ChatColor.RED + args[0] + " has already received the rewards for posting on MCF");
                                    } else {
                                        getServer().dispatchCommand(getServer().getConsoleSender(), "eco give " + args[0] + " " + getConfig().getInt("Payment"));
                                        getServer().dispatchCommand(getServer().getConsoleSender(), "enjin addpoints " + args[0] + " " + getConfig().getInt("Points"));
                                        List<String> stringmcf = settings.getNamesmcfFile().getStringList("Names");
                                        stringmcf.add(args[0]);
                                        settings.getNamesmcfFile().set("Names", stringmcf);
                                        player.sendMessage(ChatColor.DARK_GREEN + args[0] + " has been given" + ChatColor.GOLD + " $" + getConfig().getInt("Payment") + ChatColor.DARK_GREEN + " and " + ChatColor.GOLD + getConfig().getInt("Points") + " Enjin points");
                                        Bukkit.broadcastMessage(ChatColor.GREEN + "[" + ChatColor.AQUA + "PSC" + ChatColor.GREEN + "] Player " + ChatColor.GOLD + args[0] + ChatColor.GREEN + " has posted on Minecraft Forums!. They have received $" + getConfig().getInt("Payment") + " and " + getConfig().getInt("Points") + " Enjin points!");
                                        settings.saveNamesmcfFile();
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + args[0] + " has never been on the server! Check the player name again, carefully.");
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command!");
                        }
                    }
             else if (cmd.getName().equalsIgnoreCase("postedr")) {
                if (player.hasPermission("posted.reload")) {
                    settings.reloadNamespmcFile();
                    settings.reloadNamesmcfFile();
                    reloadConfig();
                    player.sendMessage(ChatColor.DARK_GREEN + "All Config Files Have Been Reloaded!");
                } else {
                    player.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command!");
                }
            } else if (cmd.getName().equalsIgnoreCase("posted")) {
                if (player.hasPermission("posted.use")) {
                    if (args.length == 0) {
                        player.sendMessage(ChatColor.YELLOW + "PostedSC Staff Commands:");
                        player.sendMessage(ChatColor.GOLD + "/postedpmc (player name)" + ChatColor.GRAY + ChatColor.BOLD + " - " + ChatColor.RESET
                                + ChatColor.GREEN + "Reward the player for posting on PMC");
                        player.sendMessage(ChatColor.GOLD + "/postedmcf (player name)" + ChatColor.GRAY + ChatColor.BOLD + " - " + ChatColor.RESET
                                + ChatColor.GREEN + "Reward the player for posting on MCF");
                    }
                } else {
                    player.sendMessage(ChatColor.DARK_RED + "You do not have permission to use this command!");
                }
                if (player.isOp()) {
                    player.sendMessage(ChatColor.GOLD + "postedr" + ChatColor.GRAY + ChatColor.BOLD + " - " + ChatColor.RESET
                            + ChatColor.GREEN + "Reload all config files");
                }
            }
        }
        return true;
    }
}