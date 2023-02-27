package wozniaktv.telegramconnector

import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class ConsoleExecuteCommand(cmd : String) : BukkitRunnable() {
    private val main : Main = JavaPlugin.getPlugin(Main::class.java)
    private val command : String = cmd
    override fun run() {
        val cmdRunner = MessageInterceptingCommandRunner(main.server.consoleSender)
        main.server.dispatchCommand(cmdRunner,command)
        main.tgManager!!.sendMessageNotification("<b>Command Executed</b>: <code>${command}</code>")
        main.tgManager!!.sendMessageNotification("<code>${cmdRunner.messageLogStripColor}</code>")
        cmdRunner.clearMessageLog()
    }

}