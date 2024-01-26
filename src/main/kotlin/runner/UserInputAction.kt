package runner

import tdl.client.runner.ActionProvider
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class UserInputAction(private val args: Array<String>) : ActionProvider {
    override fun get(): String {
        var returnValue: String? = null
        try {
            returnValue = if (args.isNotEmpty()) args[0] else readInputFromConsole()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return returnValue ?: ""
    }

    @Throws(IOException::class)
    private fun readInputFromConsole(): String {
        val buffer = BufferedReader(InputStreamReader(System.`in`))
        return buffer.readLine()
    }
}
