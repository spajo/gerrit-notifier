package eu.spaj.gerritnotifier.view

import eu.spaj.gerritnotifier.gerrit.GerritEvent
import javafx.collections.FXCollections
import tornadofx.*

/**
 * @author erafaja
 * Created on 2018-08-07.
 */
class NotificationView : View("Gerrit Notifier") {
    override val root = vbox()

    private val notifications = FXCollections.observableArrayList(GerritEvent("test", "test"), GerritEvent("test", "test"), GerritEvent("test", "test"))

    init {
        with(root) {
            tableview(notifications) {
                readonlyColumn("Notification", GerritEvent::caption)
                readonlyColumn("Delete", GerritEvent::message).cellFormat {
                    graphic = hbox(spacing = 5) {
                        button("Delete").action {
                            notifications.removeAt(index)
                        }
                    }
                }
            }
        }
    }

}
