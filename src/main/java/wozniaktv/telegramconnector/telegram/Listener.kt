package wozniaktv.telegramconnector.telegram

import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.model.Update
import wozniaktv.telegramconnector.Main

class Listener(main : Main) : UpdatesListener {

    private var main : Main ? = null

    init{
        this.main = main
    }

    override fun process(updates: MutableList<Update>?): Int {

        if(!main!!.enabledTgBot) return UpdatesListener.CONFIRMED_UPDATES_ALL

        if(updates==null) return UpdatesListener.CONFIRMED_UPDATES_ALL

        for(up in updates){
            if(up.message().from().isBot) continue

            if(up.message().chat().id()!=main!!.config.getLong("authorizedChatId") && main!!.config.getBoolean("findChatId")){
                main!!.server.logger.info("chatID: ${up.message().chat().id()}")
            }
            if(up.message().chat().id()==main!!.config.getLong("authorizedChatId")){
                if(up.message().text()!="/start") main!!.executeCommand(up.message().text())
                else main!!.tgManager!!.sendMessageNotification("Server is still online, don't worry!")
            }
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL
    }

}