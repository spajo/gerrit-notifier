package eu.spaj.gerritnotifier

import tornadofx.EventBus
import tornadofx.FXEvent

/**
 * @author erafaja
 * Created on 2018-07-25.
 */
class GerritEvent(val message: String): FXEvent(EventBus.RunOn.BackgroundThread)
