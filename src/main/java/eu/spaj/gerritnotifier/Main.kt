package eu.spaj.gerritnotifier

import javafx.application.Platform
import javafx.stage.Stage
import tornadofx.App
import tornadofx.FX

/**
 * @author erafaja
 * Created on 2018-07-25.
 */


class Main : App(MainView::class) {

    override fun start(stage: Stage) {
        super.start(stage)

        val byteArray = ByteArray(80000)
        resources.stream("/icon.png").read(byteArray)
        println(String(byteArray))

        trayicon(resources.stream("/icon.png")) {
            toolTip = "Gerrit Notifier"

            setOnMouseClicked(fxThread = true) {
                showToFront()
            }

            menu("Gerrit Notifier") {
                item("Gerrit Notifier") { }
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
        }
    }

    private fun showToFront() {
        FX.primaryStage.show()
        FX.primaryStage.toFront()
    }

}
