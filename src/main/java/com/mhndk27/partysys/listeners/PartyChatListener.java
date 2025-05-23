package com.mhndk27.partysys.listeners;

import com.mhndk27.partysys.PartyManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PartyChatListener implements Listener {

    private final PartyManager partyManager;

    public PartyChatListener(PartyManager partyManager) {
        this.partyManager = partyManager;
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onChat(AsyncPlayerChatEvent event) {
        Player sender = event.getPlayer();
        boolean isEnabled = partyManager.isPartyChatEnabled(sender.getUniqueId());

        // ✅ إذا مفعل الشات لكن مو في بارتي → نلغيه مباشرة
        if (isEnabled && !partyManager.isInParty(sender.getUniqueId())) {
            partyManager.togglePartyChat(sender.getUniqueId()); // نلغي التفعيل
            sender.sendMessage("§cYou are no longer in a party. Party chat has been disabled.");
            return;
        }

        // ✅ إذا الشات مو مفعل ما نسوي شيء
        if (!isEnabled) return;

        event.setCancelled(true); // نوقف الشات العام
        String message = event.getMessage();
        partyManager.sendPartyMessage(sender, message);
    }
}
