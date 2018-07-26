package eu.spaj.gerritnotifier.gerrit.data

import com.squareup.moshi.JsonClass
import eu.spaj.gerritnotifier.gerrit.data.nested.Author
import eu.spaj.gerritnotifier.gerrit.data.nested.Change

/**
 * @author erafaja
 * Created on 2018-07-26.
 */
@JsonClass(generateAdapter = true)
data class Merge(
        val submitter: Author,
        val change: Change
) {
    val caption = "${submitter.username} merged ${change.number}"
    val message = "${change.project} on branch ${change.branch}"
}
