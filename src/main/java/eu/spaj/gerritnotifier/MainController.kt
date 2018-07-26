package eu.spaj.gerritnotifier

import eu.spaj.gerritnotifier.gerrit.GerritEvent
import tornadofx.Controller
import java.awt.TrayIcon

/**
 * @author erafaja
 * Created on 2018-07-25.
 */


class MainController : Controller() {

    lateinit var trayIcon: TrayIcon

    init {
        subscribe<GerritEvent> {
            trayIcon.displayMessage(it.caption,it.message, TrayIcon.MessageType.INFO)
        }
    }

}
