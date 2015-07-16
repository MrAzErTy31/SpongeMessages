package fr.mrazerty31.spongemessages.utils;

import java.io.IOException;

import org.slf4j.Logger;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;

import fr.mrazerty31.spongemessages.SpongeMessages;
import ninja.leaping.configurate.ConfigurationNode;

public class SMUtils {
	private static ConfigurationNode config;
	private static Logger log;

	public static void init() {
		config = SpongeMessages.config;
		log = SpongeMessages.instance.getLogger();
	}

	// Text Utils

	public static Text getColoredText(String message, TextColor color) {
		return Texts.builder("[SpongeMessages] ").color(TextColors.GOLD).append(Texts.builder(message).color(color).build()).build();
	}

	public static String getUncoloredText(String message) {
		return "[SpongeMessages] " + message;
	}

	// Configuration File

	public static void reloadConfig() {
		try {
			SpongeMessages.config = SpongeMessages.instance.getConfigManager().load();
			log.info("[SpongeMessages] Reloaded file configuration !");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void setJoinMessage(String message) {
		try {
			config.getNode("spongemessages", "event", "join", "message").setValue(message);
			log.info(getUncoloredText("Join message has been modified !"));
			SpongeMessages.instance.getConfigManager().save(SpongeMessages.config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void setQuitMessage(String message) {
		try {
			config.getNode("spongemessages", "event", "quit", "message").setValue(message);
			log.info(getUncoloredText("Join message has been modified !"));
			SpongeMessages.instance.getConfigManager().save(SpongeMessages.config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void setChatFormat(String message) {
		try {
			config.getNode("spongemessages", "chat", "format", "message").setValue(message);
			log.info(getUncoloredText("Chat format has been modified !"));
			SpongeMessages.instance.getConfigManager().save(SpongeMessages.config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void setServerMOTD(String message) {
		try {
			config.getNode("spongemessages", "server", "motd", "message").setValue(message);
			log.info(getUncoloredText("Server MOTD has been modified !"));
			SpongeMessages.instance.getConfigManager().save(SpongeMessages.config);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
