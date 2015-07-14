package fr.mrazerty31.spongemessages.command;

import java.io.IOException;

import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import fr.mrazerty31.spongemessages.SpongeMessages;

public class SMCommandExecutor implements CommandExecutor {
	private SpongeMessages plugin;
	private String cmd;
	
	public SMCommandExecutor(SpongeMessages plugin, String cmd) {
		this.plugin = plugin;
		this.cmd = cmd;
	}
	
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if(cmd.equals("reload")) {
			try {
				SpongeMessages.config = plugin.getConfigManager().load();
				src.sendMessage(Texts.builder("[SpongeMessages] ").color(TextColors.GOLD)
						.append(Texts.builder("Configuration reloaded succesfully !").color(TextColors.GREEN).build()).build());
			} catch (IOException e) {
				e.printStackTrace();
				src.sendMessage(Texts.builder("[SpongeMessages] ").color(TextColors.GOLD)
						.append(Texts.builder("Error while reloading configuration !").color(TextColors.RED).build()).build());
			}
		}
		return CommandResult.success();
	}
}
