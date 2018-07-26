package eu.spaj.gerritnotifier

import javafx.application.Platform
import javafx.stage.Stage
import tornadofx.App
import tornadofx.EventContext
import tornadofx.FX
import tornadofx.FXEventRegistration
import java.awt.TrayIcon

/**
 * @author erafaja
 * Created on 2018-07-25.
 */


class Main : App(MainView::class) {

    private lateinit var trayIcon: TrayIcon

    override fun start(stage: Stage) {
        super.start(stage)

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

            trayIcon = this
        }

        FX.eventbus.subscribe(event = GerritEvent::class, scope = scope,
                registration = FXEventRegistration(eventType = GerritEvent::class, owner = null, maxCount = null,
                        action = { trayIcon.displayMessage("test", "test", TrayIcon.MessageType.INFO) }))

    }

    fun test(event: EventContext): Unit {
        trayIcon.displayMessage("Test", "Test", TrayIcon.MessageType.INFO)
    }

    private fun showToFront() {
        FX.primaryStage.show()
        FX.primaryStage.toFront()
    }

}
