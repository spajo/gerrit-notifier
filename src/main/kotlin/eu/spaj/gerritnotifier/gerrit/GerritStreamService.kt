package eu.spaj.gerritnotifier.gerrit

import com.jcraft.jsch.ChannelExec
import com.jcraft.jsch.HostKey
import com.jcraft.jsch.HostKeyRepository
import com.jcraft.jsch.JSch
import com.jcraft.jsch.JSchException
import com.jcraft.jsch.Session
import com.jcraft.jsch.UserInfo
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import eu.spaj.gerritnotifier.Config
import eu.spaj.gerritnotifier.gerrit.data.Comment
import eu.spaj.gerritnotifier.gerrit.data.Merge
import eu.spaj.gerritnotifier.gerrit.data.Patchset
import tornadofx.FX
import java.io.BufferedReader
import java.net.UnknownHostException


/**
 * @author erafaja
 * Created on 2018-07-26.
 */

class GerritStreamService {

    private lateinit var session: Session
    private lateinit var channel: ChannelExec
    private lateinit var reader: BufferedReader

    private val jSch = JSch()
    private val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    init {
        jSch.hostKeyRepository = AcceptsAllHostKeyRepository()
    }


    private fun isConnected() = if (::session.isInitialized) session.isConnected else false

    @Throws(IllegalStateException::class)
    fun readGerritStream() {
        if (isConnected()) {
            channel = session.openChannel("exec") as ChannelExec
            channel.setCommand("gerrit stream-events -s comment-added -s patchset-created -s change-merged")
            reader = channel.inputStream.bufferedReader()
            channel.connect()


            reader.useLines {
                it.asSequence()
                        .filter { it.contains(Config.username()) }
                        .forEach {
                            if (channel.isConnected) {
                                val event = parseEvents(it)
                                fireEvent(event)
                            } else {
                                return@useLines
                            }

                        }
            }
            reader.close()
        } else {
            throw IllegalStateException("Not connected!")
        }
    }

    private fun fireEvent(event: GerritEvent) = FX.eventbus.fire(event)

    private fun parseEvents(event: String): GerritEvent {
        return when {
            event.contains("comment-added") -> {
                val adapter = moshi.adapter<Comment>(Comment::class.java)
                val comment = adapter.fromJson(event)
                GerritEvent(comment?.caption, comment?.message)
            }
            event.contains("patchset-created") -> {
                val adapter = moshi.adapter<Patchset>(Patchset::class.java)
                val patchset = adapter.fromJson(event)
                GerritEvent(patchset?.caption, patchset?.message)
            }
            event.contains("change-merged") -> {
                val adapter = moshi.adapter<Merge>(Merge::class.java)
                val merge = adapter.fromJson(event)
                GerritEvent(merge?.caption, merge?.message)
            }
            else -> kotlin.error("This should not happen lol")

        }
    }

    @Throws(UnknownHostException::class, IllegalStateException::class, JSchException::class)
    fun connect() {
        if (!isConnected()) {
            jSch.addIdentity(Config.publicKeyPath())
            session = jSch.getSession(Config.username(), Config.url(), Config.port())
            session.setConfig("PreferredAuthentications", "publickey")
            session.connect()
        } else {
            throw IllegalStateException("Service already connected")
        }

    }

    fun closeGerritStream() {
        if (::channel.isInitialized) {
            channel.disconnect()
        }
    }

    fun disconnect() {
        if (isConnected()) {
            closeGerritStream()
            session.disconnect()
        }
    }
}

internal class AcceptsAllHostKeyRepository : HostKeyRepository {

    override fun check(host: String, key: ByteArray): Int {
        return HostKeyRepository.OK
    }

    override fun add(hostkey: HostKey, ui: UserInfo) {}

    override fun remove(host: String, type: String) {}

    override fun remove(host: String, type: String, key: ByteArray) {}

    override fun getKnownHostsRepositoryID(): String {
        return ""
    }

    override fun getHostKey(): Array<HostKey> {
        return EMPTY_ARR
    }

    override fun getHostKey(host: String, type: String): Array<HostKey> {
        return EMPTY_ARR
    }

    companion object {
        private val EMPTY_ARR = arrayOf<HostKey>()
    }

}
