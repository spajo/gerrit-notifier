package eu.spaj.gerritnotifier.view

import eu.spaj.gerritnotifier.Config
import eu.spaj.gerritnotifier.ConfigController
import javafx.beans.property.SimpleBooleanProperty
import tornadofx.*

/**
 * @author erafaja
 * Created on 2018-07-25.
 */
class ConfigView : View("Gerrit Notifier") {
    override val root = Form()

    private val controller: ConfigController by inject()

    private var isStarted = SimpleBooleanProperty(false)

    init {
        with(root) {
            fieldset("Gerrit Config") {
                field("Gerrit URL") {
                    textfield().bind(Config.urlProperty)
                }

                field("Gerrit Port") {
                    textfield().bind(Config.portProperty)
                }

                field("Gerrit Username") {
                    textfield().bind(Config.usernameProperty)
                }

                field("Public Key Path") {
                    textfield().bind(Config.publicKeyPathProperty)
                }
            }

            button("Save Config") {
                action {
                    controller.saveConfig()
                }
                shortcut("Ctrl+S")
            }

            button("Start") {
                visibleProperty().bind(isStarted.not())
                action {
                    controller.start()
                    isStarted.set(true)
                }
            }

            button("Stop") {
                visibleProperty().bind(isStarted)
                action {
                    controller.stop()
                    isStarted.set(false)
                }
            }

            button("test") {
                action {
                    openInternalWindow<NotificationView>()
                }
            }
        }
    }


}
