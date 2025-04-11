package org.kxysl1k.secureJoin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;

public class SecureJoinPlugin extends JavaPlugin {

    private Connection dbConnection;

    @Override
    public void onEnable() {
        getLogger().info("🔐 SecureJoin enabled!");

        // Підключення до бази даних
        try {
            this.dbConnection = DbUtils.connectToDatabase();
            getLogger().info("✅ Підключення до бази успішне.");
        } catch (SQLException e) {
            getLogger().severe("❌ Не вдалося підключитися до бази: " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        // Реєстрація плагін-каналу
        getServer().getMessenger().registerIncomingPluginChannel(this, "securejoin:mods", new ClientMessageListener(dbConnection));
        getServer().getMessenger().registerOutgoingPluginChannel(this, "securejoin:mods");
    }

    @Override
    public void onDisable() {
        getLogger().info("🔒 SecureJoin disabled.");
        try {
            if (dbConnection != null && !dbConnection.isClosed()) {
                dbConnection.close();
            }
        } catch (SQLException e) {
            getLogger().severe("❌ Помилка при закритті бази: " + e.getMessage());
        }
    }
}