package eu.spaj.gerritnotifier

import eu.spaj.gerritnotifier.gerrit.GerritEvent
import javafx.application.Platform
import javafx.stage.Stage
import tornadofx.*
import java.awt.TrayIcon

/**
 * @author erafaja
 * Created on 2018-07-25.
 */


class Main : App(MainView::class) {


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
            find(MainController::class).trayIcon = this
        }

    }

    private fun showToFront() {
        FX.primaryStage.show()
        FX.primaryStage.toFront()
    }

}
