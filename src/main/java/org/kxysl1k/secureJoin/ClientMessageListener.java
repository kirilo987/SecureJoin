package org.kxysl1k.secureJoin;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientMessageListener implements PluginMessageListener {

    private final Connection dbConnection;

    public ClientMessageListener(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("securejoin:mods")) return;

        String json = new String(message, StandardCharsets.UTF_8);
        System.out.println("📦 Отримано дані від " + player.getName() + ": " + json);

        try (PreparedStatement stmt = dbConnection.prepareStatement(
                "INSERT INTO player_info (uuid, name, ip, mods_json) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, player.getUniqueId().toString());
            stmt.setString(2, player.getName());
            stmt.setString(3, player.getAddress().getAddress().getHostAddress());
            stmt.setString(4, json);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("❌ Помилка при збереженні в базу: " + e.getMessage());
        }
    }
}