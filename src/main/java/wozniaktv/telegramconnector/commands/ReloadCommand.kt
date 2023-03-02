package wozniaktv.telegramconnector.commands

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.java.JavaPlugin
import wozniaktv.telegramconnector.Main

object ReloadCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, cmd: Command, label: String, arg: Array<out String>): Boolean {

        if(sender.hasPermission("tcp.reload")){
            sender.sendMessage("Config file reloaded!")
            JavaPlugin.getPlugin(Main::class.java).reloadConfig()
        }else{
            sender.sendMessage("You don't have the permission to do that!")
        }

        return true
    }

}