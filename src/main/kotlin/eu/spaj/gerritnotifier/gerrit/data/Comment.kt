package eu.spaj.gerritnotifier.gerrit.data

import com.squareup.moshi.JsonClass
import eu.spaj.gerritnotifier.gerrit.data.nested.Approval
import eu.spaj.gerritnotifier.gerrit.data.nested.Author
import eu.spaj.gerritnotifier.gerrit.data.nested.Change

/**
 * @author erafaja
 * Created on 2018-07-26.
 */
@JsonClass(generateAdapter = true)
data class Comment(
        val author: Author?,
        val approvals: List<Approval>?,
        val change: Change
) {
    val codeReview = approvals?.asSequence()?.find {
        it.type == "Code-Review"
    }

    val caption = "${author?.username} commented on ${change.number} | CR: ${codeReview?.value}"
    val message = "${change.project} on branch ${change.branch}"
}
