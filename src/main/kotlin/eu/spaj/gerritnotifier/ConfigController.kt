package eu.spaj.gerritnotifier

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import eu.spaj.gerritnotifier.gerrit.GerritEvent
import eu.spaj.gerritnotifier.gerrit.GerritStreamService
import tornadofx.Controller
import java.awt.TrayIcon
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author erafaja
 * Created on 2018-07-25.
 */


class ConfigController : Controller() {
    private val gerritStreamService = GerritStreamService()
    lateinit var trayIcon: TrayIcon

    init {
        subscribe<GerritEvent> {
            trayIcon.displayMessage(it.caption, it.message, TrayIcon.MessageType.INFO)
        }
    }

    fun saveConfig() {
        val configPath = Paths.get("config.json")
        Files.deleteIfExists(configPath)
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter<ConfigDTO>(ConfigDTO::class.java)
        val json = adapter.indent("    ").toJson(
                ConfigDTO(
                        Config.url(), Config.username(), Config.port(), Config.publicKeyPath()
                )
        )
        Files.write(configPath, json.toByteArray(Charset.forName("UTF-8")))
    }

    fun start() {
        tornadofx.runAsync {
            gerritStreamService.apply {
                connect()
                readGerritStream()
            }
        }
    }

    fun stop() {
        gerritStreamService.disconnect()
    }

}
