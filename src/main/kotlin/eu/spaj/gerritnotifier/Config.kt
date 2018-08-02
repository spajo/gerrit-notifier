package eu.spaj.gerritnotifier

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty

/**
 * @author erafaja
 * Created on 2018-07-31.
 */


object Config {
    const val GERRIT_DEFAULT_PORT = 29418

    val urlProperty = SimpleStringProperty()
    val usernameProperty = SimpleStringProperty()
    val portProperty = SimpleIntegerProperty(GERRIT_DEFAULT_PORT)
    val publicKeyPathProperty = SimpleStringProperty("${System.getProperty("user.home")}\\.ssh\\id_rsa")

    fun url(): String = urlProperty.get()
    fun username(): String = usernameProperty.get()
    fun port(): Int = portProperty.get()
    fun publicKeyPath(): String = publicKeyPathProperty.get()

    override fun toString(): String {
        return """|{
                  |  "url" : "${urlProperty.get()}",
                  |  "username" : "${usernameProperty.get()}"
                  |}""".trimMargin()
    }
}

