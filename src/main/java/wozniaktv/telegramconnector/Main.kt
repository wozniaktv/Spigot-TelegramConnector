package wozniaktv.telegramconnector

import org.bukkit.plugin.java.JavaPlugin
import wozniaktv.telegramconnector.telegram.TelegramManager


class Main : JavaPlugin() {

    var tgManager : TelegramManager ? = null

    //var advancedLicense : AdvancedLicense ? = null

    var enabledTgBot = false

    //var plEnabled = true

    override fun onEnable() {
        setupOrReloadConfig()
        //advancedLicense = AdvancedLicense(config.getString("licenseKey"), "http://mc-control.thefantasticboy.ovh/verify.php", this)
        /*if(!checkLicense()){
            logger.severe("Your license is not valid!")
            logger.warning("Contact @Lorenzo137 on Telegram to get one!")
            plEnabled = false
            disablePlugin()
            return
        }*/
        //licensedConfirmedMessage()
        printAsciiArt()
        setupListeners()
        logger.info("Listeners ready!")
        tgManager = TelegramManager(this)
        tgManager!!.sendMessageNotification("Server is <b>online</b>! =)")
        enabledTgBot = true
        //startCheckingLicenseTimer()

    }

    /*private fun licensedConfirmedMessage(){
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
    }*/

    /*private fun checkLicense() : Boolean{

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

    }*/

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
        /*if(!plEnabled){
            logger.info("License code error: ${advancedLicense!!.isValid()}")
            return
        }*/
        enabledTgBot = false
        tgManager!!.sendMessageNotification("Server is <b>offline</b>! =/")
    }

    fun executeCommand(cmd : String){
        ConsoleExecuteCommand(cmd).runTask(this)
    }


}