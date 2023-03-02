package wozniaktv.telegramconnector.database

import wozniaktv.telegramconnector.Main
import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.SQLException

class DbManager(plugin : Main) {

    private var plugin : Main ? = null

    private var dbConnectionUrl : String ? = null

    init{
        this.plugin = plugin
        dbConnectionUrl = "jdbc:sqlite:/${plugin.dataFolder.absolutePath}/data.db"
        DriverManager.getConnection(dbConnectionUrl).close()
        plugin.logger.info("DB Connection Established successfully.")
        dbInit()
    }

    private fun dbInit(){
        var conn : Connection
        try{
            conn = DriverManager.getConnection(dbConnectionUrl)

            var statement = conn.createStatement()

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS Administrators (`id` INTEGER PRIMARY KEY, `chatid` INTEGER);")
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS subOwners (`id` INTEGER PRIMARY KEY, `chatid` INTEGER);")
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS chatGroups (`id` INTEGER PRIMARY KEY, `chatid` INTEGER);")


        }catch(e: SQLException){
            plugin!!.logger.severe("[Database was being initialized]")
            plugin!!.logger.severe("SQLException: $e")
        }
    }

}