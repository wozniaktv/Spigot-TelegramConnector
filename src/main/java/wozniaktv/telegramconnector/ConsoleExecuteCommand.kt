package wozniaktv.telegramconnector

import com.pengrad.telegrambot.model.Message
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable

class ConsoleExecuteCommand(cmd : String, fromMessage : Message) : BukkitRunnable() {

    private val main : Main = JavaPlugin.getPlugin(Main::class.java)
    private val command : String = cmd
    private val fromMsg : Message = fromMessage
    override fun run() {

        if(command.startsWith(".")){

            var executed = false

            for (i in 0..50){
                if(main.config.getString("customCommands.$i.invocation").equals(command.replace(".","").split(" ")[0],true)){
                    executed = true
                    for(string in main.config.getStringList("customCommands.$i.commands")){
                        if(string.contains("%arg1%") && command.split(" ").size<2){
                            main.tgManager!!.replyToMessage("You didn't insert the 1st argument. Command execution canceled.",fromMsg)
                            return
                        }
                        if(string.contains("%arg2%") && command.split(" ").size<3){
                            main.tgManager!!.replyToMessage("You didn't insert the 2nd argument. Command execution canceled.",fromMsg)
                            return
                        }
                        if(string.contains("%arg3%") && command.split(" ").size<4){
                            main.tgManager!!.replyToMessage("You didn't insert the 3rd argument. Command execution canceled.",fromMsg)
                            return
                        }
                    }
                    for(cmd in main.config.getStringList("customCommands.$i.commands")){

                        var finalCmd = cmd
                        if (command.split(" ").size>1) finalCmd = finalCmd.replace("%arg1%",command.split(" ")[1])
                        if (command.split(" ").size>2) finalCmd = finalCmd.replace("%arg2%",command.split(" ")[2])
                        if(command.split(" ").size>3) finalCmd = finalCmd.replace("%arg3%",command.split(" ")[3])

                        main.server.dispatchCommand(main.server.consoleSender,finalCmd)

                    }
                    main.tgManager!!.replyToMessage("Custom config.yml command '<code>${main.config.getString("customCommands.$i.invocation")}</code>' has been executed.",fromMsg)
                    main.logger.info("The user ${fromMsg.from().username()} [${fromMsg.from().id()}] executed the custom config.yml command: \"${command}\"")

                }
            }

            if(!executed){
                main.tgManager!!.replyToMessage("Command '<code>${command.replace(".","").split(" ")[0]}</code>' not found in config.yml file",fromMsg)
            }

            return
        }

        val cmdRunner = MessageInterceptingCommandRunner(main.server.consoleSender)
        main.server.dispatchCommand(cmdRunner,command)
        main.tgManager!!.replyToMessage("<b>Command Executed</b>: <code>${command}</code>",fromMsg)
        main.tgManager!!.replyToMessage("<code>${cmdRunner.messageLogStripColor}</code>",fromMsg)
        main.logger.info("The user ${fromMsg.from().username()} [${fromMsg.from().id()}] executed the command: \"$command\"")
        cmdRunner.clearMessageLog()
    }

}