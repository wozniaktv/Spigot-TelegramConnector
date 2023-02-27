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
        plugin!!.tgManager!!.sendMessageNotification("<b>+</b> ${e.player.name}")
    }

    @EventHandler
    fun playerQuit(e: PlayerQuitEvent){
        plugin!!.tgManager!!.sendMessageNotification("<b>-</b> ${e.player.name}")
    }

    @EventHandler
    fun playerDeath(e: PlayerDeathEvent){
        if(e.deathMessage == null){
            plugin!!.tgManager!!.sendMessageNotification("<b>${e.entity.name}</b> e' morto. [Messaggio di morte mancante]")
        }
        else{
            plugin!!.tgManager!!.sendMessageNotification("<b>${e.deathMessage!!}</b>")
        }
    }

    @EventHandler
    fun playerAdvancement(e: PlayerAdvancementDoneEvent){
        if(e.advancement.display?.title == null) return
        plugin!!.tgManager!!.sendMessageNotification("<b>${e.player.name}</b> ha completato l'Advancement [<b>${e.advancement.display?.title}</b>]")
    }

    @EventHandler
    fun playerCommand(e: PlayerCommandPreprocessEvent){
        plugin!!.tgManager!!.sendMessageNotification("<b>${e.player.name}</b> : <code>${e.message}</code>")
    }

    @EventHandler
    fun playerChatEvent(e: AsyncPlayerChatEvent){
        plugin!!.tgManager!!.sendMessageNotification("<b>${e.player.name}</b> : ${e.message}")
    }
    @EventHandler
    fun playerKickEvent(e: PlayerKickEvent){
        plugin!!.tgManager!!.sendMessageNotification("Il giocatore <b>${e.player.name}</b> e' stato espulso\n<b>Motivazione</b>: ${e.reason}")
    }

}