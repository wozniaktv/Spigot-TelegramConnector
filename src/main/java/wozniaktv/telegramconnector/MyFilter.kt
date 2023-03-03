package wozniaktv.telegramconnector

import java.util.logging.Filter
import java.util.logging.LogRecord

class MyFilter : Filter {
    override fun isLoggable(record: LogRecord?): Boolean {
        return false
    }
}