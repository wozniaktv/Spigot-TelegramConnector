package wozniaktv.telegramconnector.telegram

import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.request.ParseMode
import com.pengrad.telegrambot.request.SendMessage
import org.bukkit.scheduler.BukkitRunnable
import wozniaktv.telegramconnector.Main


class TelegramManager(plugin : Main) {

    private var plugin : Main ? = null
    private var tgNotificationBot : TelegramBot ? = null
    private var ownerId : Long ? = null
    private var insertedToken = false
    init{
        this.plugin = plugin
        if(plugin.config.getString("botToken")!="") insertedToken = true
        if(insertedToken){
            ownerId = plugin.config.getLong("ownerChatId")
            tgNotificationBot = TelegramBot(plugin.config.getString("botToken"))
            plugin.logger.info("Notifications Bot Initialized.")
            tgNotificationBot!!.setUpdatesListener(Listener(plugin))
        }else{
            plugin.logger.warning("")
            plugin.logger.warning("")
            plugin.logger.warning("Error: Insert the token in the config.yml file!")
            plugin.logger.warning("")
            plugin.logger.warning("")
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
        if(!insertedToken) return
        val msg = stringFilterDeprecatedColors(message)
        val chatIdList : List<Long> = plugin!!.config.getLongList("ChatIDs")
        if(!plugin!!.enabledTgBot){
            for(id in chatIdList){
                tgNotificationBot!!.execute(SendMessage(id,msg).parseMode(ParseMode.HTML))
            }
            if(!chatIdList.contains(ownerId)) tgNotificationBot!!.execute(SendMessage(ownerId,msg).parseMode(ParseMode.HTML))
            return
        }
        object : BukkitRunnable(){
            override fun run() {
                for(id in chatIdList){
                    if(id == 0.toLong()) continue
                    tgNotificationBot!!.execute(SendMessage(id,msg).parseMode(ParseMode.HTML))
                }
                if(!chatIdList.contains(ownerId)) tgNotificationBot!!.execute(SendMessage(ownerId,msg).parseMode(ParseMode.HTML))
            }
        }.runTaskAsynchronously(plugin!!)
    }

    fun replyToMessage(textMessage: String, msgToReply: Message){

        object : BukkitRunnable(){

            override fun run() {
                tgNotificationBot!!.execute(SendMessage(msgToReply.chat().id(),textMessage).replyToMessageId(msgToReply.messageId()).parseMode(ParseMode.HTML))
            }

        }.runTaskAsynchronously(plugin!!)

    }


}