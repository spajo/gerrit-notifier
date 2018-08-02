package eu.spaj.gerritnotifier

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javafx.application.Platform
import javafx.stage.Stage
import tornadofx.App
import tornadofx.FX
import tornadofx.find
import java.awt.TrayIcon
import java.nio.file.Files
import java.nio.file.Paths

/**
 * @author erafaja
 * Created on 2018-07-25.
 */


class Main : App(ConfigView::class) {

    private val mainController = find(ConfigController::class)

    override fun start(stage: Stage) {
        super.start(stage)

        loadGerritConfig()

        trayicon(resources.stream("/icon.png"), "Gerrit Notifier", autoSize = true) {
            setOnMouseClicked(fxThread = true) {
                showToFront()
            }
            menu("Gerrit Notifier") {
                item("Gerrit Notifier") {
                    setOnAction {
                        displayMessage("Hello", "Hello", TrayIcon.MessageType.NONE)
                    }
                }
                addSeparator()
                item("Show..") {
                    setOnAction(fxThread = true) {
                        showToFront()
                    }
                }
                item("Exit") {
                    setOnAction(fxThread = true) {
                        Platform.exit()
                    }
                }
            }
            mainController.trayIcon = this
        }
    }

    override fun stop() {
        super.stop()
        mainController.stop()
    }

    private fun loadGerritConfig() {
        val configPath = Paths.get("config.json")

        if (Files.exists(configPath)) {
            val configString = Files.readAllLines(configPath).joinToString("").trimIndent()
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val adapter = moshi.adapter<ConfigDTO>(ConfigDTO::class.java)
            val configDTO = adapter.lenient().fromJson(configString)

            Config.usernameProperty.set(configDTO?.username)
            Config.urlProperty.set(configDTO?.url)
            Config.portProperty.set(configDTO?.port ?: Config.GERRIT_DEFAULT_PORT)
        }
    }

    private fun showToFront() {
        FX.primaryStage.show()
        FX.primaryStage.toFront()
    }

}
