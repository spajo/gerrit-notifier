package eu.spaj.gerritnotifier.gerrit.data.nested

import com.squareup.moshi.JsonClass

/**
 * @author erafaja
 * Created on 2018-07-26.
 */
@JsonClass(generateAdapter = true)
data class  Approval(
        val type: String,
        val value: String
)
