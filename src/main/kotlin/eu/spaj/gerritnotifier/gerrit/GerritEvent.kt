package eu.spaj.gerritnotifier.gerrit

import tornadofx.EventBus
import tornadofx.FXEvent

/**
 * @author erafaja
 * Created on 2018-07-25.
 */
class GerritEvent(val caption: String?, val message: String?) : FXEvent(EventBus.RunOn.BackgroundThread)
