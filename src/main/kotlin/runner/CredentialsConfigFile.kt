package runner

import java.io.FileInputStream
import java.io.IOException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.Optional
import java.util.Properties

object CredentialsConfigFile {
    @Throws(ConfigNotFoundException::class)
    fun readFromConfigFile(key: String): String {
        return Optional.ofNullable(readPropertiesFile().getProperty(key)).orElseThrow {
            ConfigNotFoundException("The \"credentials.config\" file does not contain key $key")
        }
    }

    fun readFromConfigFile(key: String, defaultValue: String): String {
        return try {
            Optional.ofNullable(readPropertiesFile().getProperty(key)).orElse(defaultValue)
        } catch (e: ConfigNotFoundException) {
            defaultValue
        }
    }

    private fun readPropertiesFile(): Properties {
        val properties = Properties()
        val config: Path = Paths.get("config", "credentials.config")
        try {
            properties.load(FileInputStream(config.toFile()))
        } catch (e: IOException) {
            throw ConfigNotFoundException("The \"credentials.config\" has not been found. " +
                    "Please download from challenge page. ( Reason: ${e.message} )")
        }
        return properties
    }
}
