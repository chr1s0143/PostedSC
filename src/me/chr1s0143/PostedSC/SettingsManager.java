package me.chr1s0143.PostedSC;

/**
 * Created by chris on 11/01/2015.
 */

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class SettingsManager {

    private SettingsManager() { }

    static SettingsManager instance = new SettingsManager();

    public static SettingsManager getInstance() {
        return instance;
    }

    Plugin plugin;

    FileConfiguration namesmcf;
    File mcffile;

    FileConfiguration namespmc;
    File pmcfile;

    public void setupNamespmcFile(Plugin plugin) {

        pmcfile = new File(plugin.getDataFolder(), "NamesPMC.yml");

        if (!pmcfile.exists()) {
            try {
                pmcfile.createNewFile();
            }
            catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create NamesPMC.yml!");
            }
        }

        namespmc = YamlConfiguration.loadConfiguration(pmcfile);
    }

    public FileConfiguration getNamespmcFile() {
        return namespmc;
    }

    public void saveDefaultNamespmcFile() {
        if (namespmc == null) {
            pmcfile = new File(plugin.getDataFolder(), "NamesPMC.yml");
        }
        if (!pmcfile.exists()) {
            plugin.saveResource("NamesPMC.yml", false);
        }
    }

    public void saveNamespmcFile() {
        try {
            namespmc.save(pmcfile);
        }
        catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save NamesPMC.yml!");
        }
    }

    public void reloadNamespmcFile() {
        namespmc = YamlConfiguration.loadConfiguration(pmcfile);
    }

    //LOCATIONS FILE

    public void setupNamesmcfFile(Plugin plugin) {

        mcffile = new File(plugin.getDataFolder(), "NamesMCF.yml");

        if (!mcffile.exists()) {
            try {
                mcffile.createNewFile();
            }
            catch (IOException e) {
                Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create NamesMCF.yml!");
            }
        }

        namesmcf = YamlConfiguration.loadConfiguration(mcffile);
    }

    public FileConfiguration getNamesmcfFile() {
        return namesmcf;
    }

    public void saveDefaultNamesmcfFile() {
        if (namesmcf == null) {
            mcffile = new File(plugin.getDataFolder(), "NamesMCF.yml");
        }
        if (!mcffile.exists()) {
            plugin.saveResource("NamesMCF.yml", false);
        }
    }

    public void saveNamesmcfFile() {
        try {
            namesmcf.save(mcffile);
        }
        catch (IOException e) {
            Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save NamesMCF.yml!");
        }
    }

    public void reloadNamesmcfFile() {
        namesmcf = YamlConfiguration.loadConfiguration(mcffile);
    }
}

