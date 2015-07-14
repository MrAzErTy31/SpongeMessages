package fr.mrazerty31.spongemessages.listener;

import org.spongepowered.api.entity.player.Player;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.entity.player.PlayerChatEvent;
import org.spongepowered.api.event.entity.player.PlayerJoinEvent;
import org.spongepowered.api.event.entity.player.PlayerQuitEvent;
import org.spongepowered.api.event.server.StatusPingEvent;
import org.spongepowered.api.text.Texts;

import fr.mrazerty31.spongemessages.SpongeMessages;

public class SMListener {
	@SuppressWarnings("deprecation")
	@Subscribe
	public void onPlayerJoin(PlayerJoinEvent e) {
		String message = SpongeMessages.config.getNode("spongemessages", "event", "join", "message").getString()
				.replace(":player:", e.getEntity().getName());
		Texts.stripCodes(message);
		e.setNewMessage(Texts.legacy().fromUnchecked(message));
	}

	@SuppressWarnings("deprecation")
	@Subscribe
	public void onPlayerJoin(PlayerQuitEvent e) {
		String message = SpongeMessages.config.getNode("spongemessages", "event", "quit", "message").getString()
				.replace(":player:", e.getEntity().getName());
		Texts.stripCodes(message);
		e.setNewMessage(Texts.legacy().fromUnchecked(message));
	}

	@SuppressWarnings("deprecation")
	@Subscribe
	public void onPlayerChat(PlayerChatEvent e) {
		Player p = e.getEntity();
		String realMessage = Texts.toPlain(e.getMessage()).replace("<" + p.getName() + "> ", "");
		String message = SpongeMessages.config.getNode("spongemessages", "chat", "format", "message").getString()
				.replace(":player:", p.getName())
				.replace(":message:", realMessage);
		Texts.stripCodes(message);
		e.setNewMessage(Texts.legacy().fromUnchecked(message));
	}
	
	@SuppressWarnings("deprecation")
	@Subscribe
	public void onServerRequestInfo(StatusPingEvent e) {
		e.getResponse().setDescription(Texts.legacy().fromUnchecked(SpongeMessages.config.getNode("spongemessages", "server", "motd", "message").getString()));
	}
}
