package eu.spaj.gerritnotifier

import javafx.beans.property.SimpleBooleanProperty
import tornadofx.Form
import tornadofx.View
import tornadofx.action
import tornadofx.bind
import tornadofx.button
import tornadofx.field
import tornadofx.fieldset
import tornadofx.textfield

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
        }
    }


}
