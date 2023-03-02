package wozniaktv.telegramconnector

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import wozniaktv.telegramconnector.commands.ReloadCommand
import wozniaktv.telegramconnector.database.DbManager
import wozniaktv.telegramconnector.telegram.TelegramManager


class Main : JavaPlugin() {

    var tgManager : TelegramManager ? = null

    var advancedLicense : AdvancedLicense ? = null

    var dbManager : DbManager ? = null

    var enabledTgBot = false

    private var plEnabled = true

    override fun onEnable() {
        setupOrReloadConfig()
        advancedLicense = AdvancedLicense(config.getString("licenseKey"), "http://mc-control.thefantasticboy.ovh/verify.php", this)
        if(!checkLicense()){
            logger.severe("Your license is not valid!")
            logger.warning("Contact @Lorenzo137 on Telegram to get one!")
            plEnabled = false
            disablePlugin()
            return
        }
        licensedConfirmedMessage()
        printAsciiArt()
        setupListeners()
        setupCommands()
        logger.info("Plugin started!")
        tgManager = TelegramManager(this)
        tgManager!!.sendMessageNotification(config.getString("tg_messages.serverStarted")!!)
        enabledTgBot = true
        dbManager = DbManager(this)
        startCheckingLicenseTimer()

    }

    private fun licensedConfirmedMessage(){
        logger.info("---------------------------------------------------------------")
        logger.info("")
        logger.info("Your license is VALID!")
        logger.info("")
        logger.info("If you face any issue, or have any question,")
        logger.info("feel free to contact @Lorenzo137 on Telegram!")
        logger.info("")
        logger.info("---------------------------------------------------------------")
    }

    private fun disablePlugin(){
        Bukkit.getScheduler().cancelTasks(this)
        Bukkit.getPluginManager().disablePlugin(this)
    }

    private fun checkLicense() : Boolean{

        if(!config.contains("licenseKey")) return false
        return advancedLicense!!.isValid()==AdvancedLicense.ValidationType.VALID


    }

    private fun startCheckingLicenseTimer(){

        object : BukkitRunnable(){

            val licensor = advancedLicense!!

            override fun run() {
                if(!checkLicense()){
                    logger.severe("Your license isn't valid.")
                    logger.severe("Code Error: "+{licensor.isValid().toString()})
                    if(licensor.isValid()==AdvancedLicense.ValidationType.NOT_VALID_IP){
                        logger.severe("There is a chance your License Key got Stolen; report this to @Lorenzo137 on TG")
                    }
                    disablePlugin()
                }
            }

        }.runTaskTimer(this,432000,432000) // Each 6 hours

    }

    private fun printAsciiArt(){
        logger.info("")
        logger.info("\n _____        ___                    ___                    _                 \n" +
                "/__   \\__ _  / __\\___  _ __  _ __   / _ \\_ __ ___ _ __ ___ (_)_   _ _ __ ___  \n" +
                "  / /\\/ _` |/ /  / _ \\| '_ \\| '_ \\ / /_)/ '__/ _ \\ '_ ` _ \\| | | | | '_ ` _ \\ \n" +
                " / / | (_| / /__| (_) | | | | | | / ___/| | |  __/ | | | | | | |_| | | | | | |\n" +
                " \\/   \\__, \\____/\\___/|_| |_|_| |_\\/    |_|  \\___|_| |_| |_|_|\\__,_|_| |_| |_|\n" +
                "      |___/                                                                   ")
        logger.info("")
    }
    private fun setupOrReloadConfig(){
        saveDefaultConfig()
        reloadConfig()
    }
    private fun setupListeners(){
        server.pluginManager.registerEvents(Events(this),this)
    }

    private fun setupCommands(){
        getCommand("tgpreload")?.setExecutor(ReloadCommand)
    }

    override fun onDisable() {
        Bukkit.getScheduler().cancelTasks(this)
        if(!plEnabled){
            logger.info("License code error: ${advancedLicense!!.isValid()}")
            return
        }
        enabledTgBot = false
        tgManager!!.sendMessageNotification(config.getString("tg_messages.serverStopped")!!)
    }


}