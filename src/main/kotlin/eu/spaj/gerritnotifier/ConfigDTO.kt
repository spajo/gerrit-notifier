package eu.spaj.gerritnotifier

import com.squareup.moshi.JsonClass

/**
 * @author erafaja
 * Created on 2018-07-31.
 */

@JsonClass(generateAdapter = true)
data class ConfigDTO(val url: String?, val username: String?, val port: Int?, val publicKeyPath: String?)
