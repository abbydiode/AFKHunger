package dev.glxy.afkhunger.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.glxy.afkhunger.Main;
import dev.glxy.afkhunger.listeners.MoveListener;

public class CheckAFKCommand implements CommandExecutor {
	private Main plugin;
	
	public CheckAFKCommand(Main plugin) {
		this.plugin = plugin;
		plugin.getCommand("checkafk").setExecutor(this);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender.hasPermission("afkhunger.check")) {
			if (args.length > 0) {
				Player target = Bukkit.getPlayer(args[0]);
				sender.sendMessage("target: " + target);
				if (target != null) {
					Integer afkTime = plugin.getConfig().getInt("afkTime") * 1000;
					Long lastMoveTime = MoveListener.lastMove.get(target);
					
					sender.sendMessage(target.getName() + " is currently " + 
					(lastMoveTime != null && lastMoveTime + afkTime < System.currentTimeMillis() ? "" : "not") + " marked AFK");
				} else {
					sender.sendMessage("User " + args[0] + "is currently not online.");
				}
			} else {
				sender.sendMessage("This command requires one argument.");
			}
		}
		return true;
	}
}
