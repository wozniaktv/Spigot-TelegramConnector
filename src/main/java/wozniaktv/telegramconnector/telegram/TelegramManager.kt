package wozniaktv.telegramconnector.telegram

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.request.ParseMode
import com.pengrad.telegrambot.request.SendMessage
import org.bukkit.scheduler.BukkitRunnable
import wozniaktv.telegramconnector.Main


class TelegramManager(plugin : Main) {

    private var plugin : Main ? = null
    private var tgNotificationBot : TelegramBot ? = null
    private var chatId : Long ? = null

    init{
        this.plugin = plugin
        chatId = plugin.config.getLong("authorizedChatId")
        if(plugin.config.getString("notificationsBotToken")!=""){
            tgNotificationBot = TelegramBot(plugin.config.getString("notificationsBotToken"))
            plugin.logger.info("Notifications Bot Initialized Correctly!")
            tgNotificationBot!!.setUpdatesListener(Listener(plugin))
        }
    }

    /*fun sendMessageConsole(message: String){
        tgConsoleBot!!.execute(SendMessage(chatId,message))
    }*/

    private fun stringRemoveDeprecatedColors(text: String) : String{
        var msg = text
        msg = msg.replace("§0","")
        msg = msg.replace("§1","")
        msg = msg.replace("§2","")
        msg = msg.replace("§3","")
        msg = msg.replace("§4","")
        msg = msg.replace("§5","")
        msg = msg.replace("§6","")
        msg = msg.replace("§7","")
        msg = msg.replace("§8","")
        msg = msg.replace("§9","")
        msg = msg.replace("§a","")
        msg = msg.replace("§b","")
        msg = msg.replace("§c","")
        msg = msg.replace("§d","")
        msg = msg.replace("§e","")
        msg = msg.replace("§f","")
        msg = msg.replace("§r","")
        return msg
    }

    private fun stringContainsDeprecatedColors(text: String) : Boolean{

        return text.contains("§")

    }

    private fun stringFilterDeprecatedColors(text: String) : String{
        var txt = text
        if(stringContainsDeprecatedColors(txt)){
            txt = stringRemoveDeprecatedColors(txt)
        }
        return txt
    }

    fun sendMessageNotification(message: String){
        val msg = stringFilterDeprecatedColors(message)
        if(!plugin!!.enabledTgBot){
            tgNotificationBot!!.execute(SendMessage(chatId,msg).parseMode(ParseMode.HTML))
            return
        }
        object : BukkitRunnable(){
            override fun run() {
                tgNotificationBot!!.execute(SendMessage(chatId,msg).parseMode(ParseMode.HTML))
            }
        }.runTaskAsynchronously(plugin!!)
    }


}