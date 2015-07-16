package fr.mrazerty31.spongemessages.command;

import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import fr.mrazerty31.spongemessages.SpongeMessages;
import fr.mrazerty31.spongemessages.utils.SMConstants;
import fr.mrazerty31.spongemessages.utils.SMUtils;

public class SMCommandExecutor implements CommandExecutor {
	@SuppressWarnings("unused")
	private SpongeMessages plugin;
	private String cmd;

	public SMCommandExecutor(SpongeMessages plugin, String cmd) {
		this.plugin = plugin;
		this.cmd = cmd;
	}

	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if(cmd.equals("reload")) {
			SMUtils.reloadConfig();
			src.sendMessage(SMUtils.getColoredText("Configuration file reloaded succesfully !", TextColors.GREEN));
		} else if(cmd.equals("set")) {
			String type = args.<String>getOne("type").get(),
					message = args.<String>getOne("message").get().replaceAll("&", "§");
			if(!message.isEmpty()) {
				if(type.equalsIgnoreCase("join")) {
					if(message.contains(":player:")) {
						SMUtils.setJoinMessage(message);
						src.sendMessage(SMUtils.getColoredText("The join message has been set !", TextColors.GREEN));
					} else src.sendMessage(SMUtils.getColoredText("Warning: You didn't put \":player:\" in message !", TextColors.YELLOW));
				} else if(type.equalsIgnoreCase("quit")) {
					if(message.contains(":player:")) {
						SMUtils.setQuitMessage(message);
						src.sendMessage(SMUtils.getColoredText("The quit message has been set !", TextColors.GREEN));
					} else src.sendMessage(SMUtils.getColoredText("Warning: You didn't put \":player:\" in message !", TextColors.YELLOW));
				} else if(type.equalsIgnoreCase("chat")) {
					if(message.contains(":player:")) {
						if(message.contains(":message:")) {
							SMUtils.setChatFormat(message);
							src.sendMessage(SMUtils.getColoredText("The chat format has been set !", TextColors.GREEN));
						} else src.sendMessage(SMUtils.getColoredText("Warning: You didn't put \":message:\" in message !", TextColors.YELLOW));
					} else src.sendMessage(SMUtils.getColoredText("Warning: You didn't put \":player:\" in message !", TextColors.YELLOW));
				} else if(type.equalsIgnoreCase("motd")) {
					SMUtils.setServerMOTD(message);
					src.sendMessage(SMUtils.getColoredText("The server MOTD has been set !", TextColors.GREEN));
				}
			}
		} else if(cmd.equals("reset")) {
			String type = args.<String>getOne("type").get();
			switch(type) {
			case "join":
				SMUtils.setJoinMessage(SMConstants.joinMessage);
				src.sendMessage(SMUtils.getColoredText("The join message has been reset !", TextColors.GREEN));
				break;
			case "quit":
				SMUtils.setQuitMessage(SMConstants.quitMessage);
				src.sendMessage(SMUtils.getColoredText("The quit message has been reset !", TextColors.GREEN));
				break;
			case "chat":
				SMUtils.setChatFormat(SMConstants.chatFormat);
				src.sendMessage(SMUtils.getColoredText("The chat format has been reset !", TextColors.GREEN));
				break;
			case "motd":
				SMUtils.setServerMOTD(SMConstants.motd);
				src.sendMessage(SMUtils.getColoredText("The server MOTD has been reset !", TextColors.GREEN));
			}
		}
		return CommandResult.success();
	}
}
