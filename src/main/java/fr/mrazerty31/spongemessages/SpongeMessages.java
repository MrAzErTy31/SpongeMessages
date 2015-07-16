package fr.mrazerty31.spongemessages;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.spongepowered.api.Game;
import org.spongepowered.api.event.Subscribe;
import org.spongepowered.api.event.state.InitializationEvent;
import org.spongepowered.api.event.state.ServerStartedEvent;
import org.spongepowered.api.event.state.ServerStartingEvent;
import org.spongepowered.api.event.state.ServerStoppedEvent;
import org.spongepowered.api.event.state.ServerStoppingEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.service.config.DefaultConfig;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.util.command.args.GenericArguments;
import org.spongepowered.api.util.command.spec.CommandSpec;

import com.google.inject.Inject;

import fr.mrazerty31.spongemessages.command.SMCommandExecutor;
import fr.mrazerty31.spongemessages.listener.SMListener;
import fr.mrazerty31.spongemessages.utils.SMConstants;
import fr.mrazerty31.spongemessages.utils.SMUtils;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.loader.ConfigurationLoader;

@Plugin(id = SMConstants.ID, name = SMConstants.NAME, version = SMConstants.VERSION)
public class SpongeMessages {

	public static SpongeMessages instance;

	@Inject
	private Logger log;

	@Inject
	@DefaultConfig(sharedRoot = true)
	private File defaultConfig;

	@Inject
	@DefaultConfig(sharedRoot = true)
	private ConfigurationLoader<CommentedConfigurationNode> cfgManager;

	private Game game;
	public static ConfigurationNode config;

	@Subscribe
	public void onServerStarting(ServerStartingEvent sse) {
		instance = this;
		game = sse.getGame();

		try {
			if (!defaultConfig.exists()) { // Default configuration file
				defaultConfig.createNewFile();
				config = cfgManager.load();
				config.getNode("spongemessages", "chat", "format", "message").setValue(SMConstants.chatFormat);
				config.getNode("spongemessages", "event", "join", "message").setValue(SMConstants.joinMessage);
				config.getNode("spongemessages", "event", "quit", "message").setValue(SMConstants.quitMessage);
				config.getNode("spongemessages", "server", "motd", "message").setValue(SMConstants.motd);
				cfgManager.save(config); 
			}
			config = cfgManager.load();

		} catch (IOException exception) {
			log.error("The default configuration could not be loaded or created!");
		}
		
		SMUtils.init();
		sse.getGame().getEventManager().register(this, new SMListener());
	}

	@Subscribe
	public void onInitialization(InitializationEvent ie) {

		// Subcommands

		CommandSpec smreload = CommandSpec.builder()
				.description(Texts.of("Reload the configuration file"))
				.permission("spongemessages.cmd.spongemessages.reload")
				.executor(new SMCommandExecutor(this, "reload"))
				.build();
		
		CommandSpec smset = CommandSpec.builder()
				.description(Texts.of("Set messages."))
				.permission("spongemessages.cmd.spongemessages.set")
				.executor(new SMCommandExecutor(this, "set"))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("type"))), 
						GenericArguments.remainingJoinedStrings(Texts.of("message")))
				.build();
		
		CommandSpec smreset = CommandSpec.builder()
				.description(Texts.of("Reset messages."))
				.permission("spongemessages.cmd.spongemessages.reset")
				.executor(new SMCommandExecutor(this, "reset"))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Texts.of("type"))))
				.build();
		
		// Main Command
		
		CommandSpec spongemessages = CommandSpec.builder()
				.description(Texts.of("Main command of the plugin"))
				.permission("spongemessages.cmd.spongemessages")
				.child(smreload, "reload")
				.child(smset, "set")
				.child(smreset, "reset")
				.build();

		ie.getGame().getCommandDispatcher().register(this, spongemessages, "spongemessages", "spongemsg");
	}

	@Subscribe
	public void onServerStopping(ServerStoppingEvent sse) {
		instance = null;
	}

	@Subscribe
	public void onServerStarted(ServerStartedEvent sse) {
		log.info("----------------------------------");
		log.info("| SpongeMessages v" + SMConstants.VERSION + " loaded ! |");
		log.info("----------------------------------");
	}

	@Subscribe
	public void onServerStopped(ServerStoppedEvent sse) {
		log.info("------------------------------------");
		log.info("| SpongeMessages v" + SMConstants.VERSION + " unloaded ! |");
		log.info("------------------------------------");
	}

	// Getters
	
	public Game getGame() {
		return game;
	}

	public Logger getLogger() {
		return log;
	}

	public ConfigurationLoader<CommentedConfigurationNode> getConfigManager() {
		return cfgManager;
	}
}
