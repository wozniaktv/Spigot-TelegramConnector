package wozniaktv.telegramconnector

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.AsyncPlayerChatEvent
import org.bukkit.event.player.PlayerAdvancementDoneEvent
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerKickEvent
import org.bukkit.event.player.PlayerQuitEvent

class Events(plugin : Main) : Listener {

    private var plugin : Main ? = null

    init{
        this.plugin = plugin
    }

    @EventHandler
    fun playerJoin(e: PlayerJoinEvent){
        var msg = plugin!!.config.getString("tg_messages.playerJoined")
        if(msg == "" || msg == null) return
        msg = msg.replace("%player%",e.player.name)
        msg = msg.replace("%server%",plugin!!.config.getString("serverIdentifier")!!)
        plugin!!.tgManager!!.sendMessageNotification(msg)
    }

    @EventHandler
    fun playerQuit(e: PlayerQuitEvent){
        var msg = plugin!!.config.getString("tg_messages.playerLeft")
        if(msg == "" || msg == null) return
        msg = msg.replace("%player%",e.player.name)
        msg = msg.replace("%server%",plugin!!.config.getString("serverIdentifier")!!)
        plugin!!.tgManager!!.sendMessageNotification(msg)
    }

    @EventHandler
    fun playerDeath(e: PlayerDeathEvent){
        var msg = plugin!!.config.getString("tg_messages.playerDeath")
        if(msg == "" || msg == null) return
        if(msg.contains("%deathMessage%") && e.deathMessage==null) {
            plugin!!.logger.warning("${e.entity.name}'s Death Event Message has been tried to use, but i couldn't find it. It may be that you're modifying your death messages in a strange way?")
            return
        }
        msg = msg.replace("%player%",e.entity.name)
        msg = msg.replace("%deathMessage%",e.deathMessage!!)
        msg = msg.replace("%server%",plugin!!.config.getString("serverIdentifier")!!)
        plugin!!.tgManager!!.sendMessageNotification(msg)

    }

    @EventHandler
    fun playerAdvancement(e: PlayerAdvancementDoneEvent){
        if(e.advancement.display?.title == null) return
        var msg = plugin!!.config.getString("tg_messages.playerAdvancementDoneEvent")
        if(msg=="" || msg == null) return
        msg = msg.replace("%player%",e.player.name)
        msg = msg.replace("%advancement%",e.advancement.display!!.title)
        msg = msg.replace("%server%",plugin!!.config.getString("serverIdentifier")!!)
        plugin!!.tgManager!!.sendMessageNotification(msg)
    }

    @EventHandler
    fun playerCommand(e: PlayerCommandPreprocessEvent){
        var msg = plugin!!.config.getString("tg_messages.playerCommand")
        if(msg == "" || msg == null) return
        msg = msg.replace("%player%",e.player.name)
        msg = msg.replace("%command%",e.message)
        msg = msg.replace("%server%",plugin!!.config.getString("serverIdentifier")!!)
        plugin!!.tgManager!!.sendMessageNotification(msg)
    }

    @EventHandler
    fun playerChatEvent(e: AsyncPlayerChatEvent){
        var msg = plugin!!.config.getString("tg_messages.playerChat")
        if(msg == "" || msg == null) return
        msg = msg.replace("%player%",e.player.name)
        msg = msg.replace("%message%",e.message)
        msg = msg.replace("%server%",plugin!!.config.getString("serverIdentifier")!!)
        plugin!!.tgManager!!.sendMessageNotification(msg)
    }
    @EventHandler
    fun playerKickEvent(e: PlayerKickEvent){
        var msg = plugin!!.config.getString("tg_messages.playerKick")
        if(msg == "" || msg == null) return
        msg = msg.replace("%player%",e.player.name)
        msg = msg.replace("%reason%",e.reason)
        msg = msg.replace("%server%",plugin!!.config.getString("serverIdentifier")!!)
        plugin!!.tgManager!!.sendMessageNotification(msg)
    }

}