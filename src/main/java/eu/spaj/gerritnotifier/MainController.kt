package eu.spaj.gerritnotifier

import tornadofx.Controller

/**
 * @author erafaja
 * Created on 2018-07-25.
 */


class MainController : Controller() {

    init {
        subscribe<GerritEvent> {

        }
    }

}
