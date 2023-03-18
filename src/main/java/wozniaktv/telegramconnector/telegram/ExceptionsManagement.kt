package wozniaktv.telegramconnector.telegram

import com.pengrad.telegrambot.ExceptionHandler
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.TelegramException
import org.bukkit.Bukkit
import wozniaktv.telegramconnector.Main

class ExceptionsManagement(main: Main, bot : TelegramBot) : ExceptionHandler {

    private var main : Main? = null
    private var bot : TelegramBot ?= null

    init{
        this.main = main
        this.bot = bot
    }

    override fun onException(p0: TelegramException?) {
        if(p0==null) return
        if(p0.response().isOk) return
        if(p0.response().errorCode() == 401){
            bot!!.removeGetUpdatesListener()
            bot!!.shutdown()
            main!!.logger.info("Error: Wrong Bot Token. Disabling plugin...")
            Bukkit.getScheduler().cancelTasks(main!!)
            Bukkit.getPluginManager().disablePlugin(main!!)
        }
        else{
            main!!.logger.info("Error during TG request response, error code: '${p0.response().errorCode()}', disabling plugin...")
            Bukkit.getScheduler().cancelTasks(main!!)
            Bukkit.getPluginManager().disablePlugin(main!!)
        }
    }


}