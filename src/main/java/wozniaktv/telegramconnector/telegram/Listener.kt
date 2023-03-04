package wozniaktv.telegramconnector.telegram

import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.model.Update
import wozniaktv.telegramconnector.ConsoleExecuteCommand
import wozniaktv.telegramconnector.Main

class Listener(main : Main) : UpdatesListener {

    private var main : Main ? = null

    init{
        this.main = main
    }

    override fun process(updates: MutableList<Update>?) : Int {

        if(!main!!.enabledTgBot) return UpdatesListener.CONFIRMED_UPDATES_ALL

        if(updates==null) return UpdatesListener.CONFIRMED_UPDATES_ALL

        val chatIdList : List<Long> = main!!.config.getLongList("ChatIDs")
        val adminIdList : List<Long> = main!!.config.getLongList("AdminIDs")

        for(up in updates){

            if(up.message().text()==null) continue

            if(up.message().from().isBot) continue



            if(up.message().chat().id()!=main!!.config.getLong("ownerChatId") && main!!.config.getBoolean("findChatId")){
                main!!.server.logger.info("chatID: ${up.message().chat().id()}")
            }

            if(!up.message().text().startsWith(main!!.config.getString("serverIdentifier")!!)) continue

            if(up.message().chat().id()==main!!.config.getLong("ownerChatId")){
                if(up.message().text()!="/start") ConsoleExecuteCommand(up.message().text().replace("${main!!.config.getString("serverIdentifier")!!} ",""),up.message()).runTask(main!!)
                else main!!.tgManager!!.replyToMessage("Server is still online, don't worry!",up.message())
            }

            if(chatIdList.contains(up.message().chat().id())){

                if(adminIdList.contains(up.message().from().id()) || up.message().from().id() == main!!.config.getLong("ownerChatId")){
                    if(up.message().text()!="/start") ConsoleExecuteCommand(up.message().text().replace("${main!!.config.getString("serverIdentifier")!!} ",""),up.message()).runTask(main!!)
                    else main!!.tgManager!!.replyToMessage("Server is still online, don't worry!",up.message())
                }

            }

        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL
    }

}