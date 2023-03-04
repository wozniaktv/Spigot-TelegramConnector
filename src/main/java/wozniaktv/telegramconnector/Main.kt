package wozniaktv.telegramconnector

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import wozniaktv.telegramconnector.telegram.TelegramManager


class Main : JavaPlugin() {

    var tgManager : TelegramManager ? = null

    var enabledTgBot = false


    override fun onEnable() {
        setupOrReloadConfig()
        printAsciiArt()
        setupListeners()
        logger.info("Listeners ready!")
        tgManager = TelegramManager(this)
        tgManager!!.sendMessageNotification("Server is <b>online</b>! =)")
        enabledTgBot = true
        //startCheckingLicenseTimer()

    }

    private fun printAsciiArt(){
        logger.info("\n  _______   _                                 _____                            _             \n" +
                " |__   __| | |                               / ____|                          | |            \n" +
                "    | | ___| | ___  __ _ _ __ __ _ _ __ ___ | |     ___  _ __  _ __   ___  ___| |_ ___  _ __ \n" +
                "    | |/ _ \\ |/ _ \\/ _` | '__/ _` | '_ ` _ \\| |    / _ \\| '_ \\| '_ \\ / _ \\/ __| __/ _ \\| '__|\n" +
                "    | |  __/ |  __/ (_| | | | (_| | | | | | | |___| (_) | | | | | | |  __/ (__| || (_) | |   \n" +
                "    |_|\\___|_|\\___|\\__, |_|  \\__,_|_| |_| |_|\\_____\\___/|_| |_|_| |_|\\___|\\___|\\__\\___/|_|   \n" +
                "                    __/ |                                                                    \n" +
                "                   |___/                                                                     ")
    }
    private fun setupOrReloadConfig(){
        saveDefaultConfig()
        reloadConfig()
    }
    private fun setupListeners(){
        server.pluginManager.registerEvents(Events(this),this)
    }

    override fun onDisable() {
        enabledTgBot = false
        Bukkit.getScheduler().cancelTasks(this)
        tgManager!!.sendMessageNotification("Server is <b>offline</b>! =/")
    }

    fun executeCommand(cmd : String){
        ConsoleExecuteCommand(cmd).runTask(this)
    }


}