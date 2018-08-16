package eu.spaj.gerritnotifier

import javafx.beans.property.SimpleIntegerProperty
import javafx.beans.property.SimpleStringProperty

/**
 * Config object for storing config of this app.
 * @author erafaja
 * Created on 2018-07-31.
 */
object Config {
    /**
     * Default Gerrit API port.
     */
    const val GERRIT_DEFAULT_PORT = 29418

    /**
     * Gerrit URL
     */
    val urlProperty = SimpleStringProperty()
    /**
     * Gerrit username to follow.
     */
    val usernameProperty = SimpleStringProperty()
    /**
     * Gerrit port
     */
    val portProperty = SimpleIntegerProperty(GERRIT_DEFAULT_PORT)
    /**
     * Public key path
     */
    val publicKeyPathProperty = SimpleStringProperty("${System.getProperty("user.home")}\\.ssh\\id_rsa")

    /**
     * Getter for URL
     */
    fun url(): String = urlProperty.get()

    /**
     * Getter for username
     */
    fun username(): String = usernameProperty.get()

    /**
     * Getter for Port
     */
    fun port(): Int = portProperty.get()

    /**
     * Getter for public key path
     */
    fun publicKeyPath(): String = publicKeyPathProperty.get()

    override fun toString(): String {
        return """|{
                  |  "url" : "${urlProperty.get()}",
                  |  "username" : "${usernameProperty.get()}"
                  |}""".trimMargin()
    }
}

