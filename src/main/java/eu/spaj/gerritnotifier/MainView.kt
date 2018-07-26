package eu.spaj.gerritnotifier

import eu.spaj.gerritnotifier.gerrit.GerritEvent
import tornadofx.View
import tornadofx.action
import tornadofx.button
import tornadofx.vbox

/**
 * @author erafaja
 * Created on 2018-07-25.
 */
class MainView : View("Gerrit Notifier") {
    override val root = vbox {
        button("Press me!") {
            action {
                fire(GerritEvent("Title", "message"))
            }
        }
    }

    val controller: MainController by inject()
}
