package eu.spaj.gerritnotifier.gerrit.data.nested

import com.squareup.moshi.JsonClass
import java.net.URL

/**
 * @author erafaja
 * Created on 2018-07-26.
 */
@JsonClass(generateAdapter = true)
data class Change(
        val project: String,
        val branch: String,
        val number: Long,
        val url: URL
)
