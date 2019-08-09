package active.since93.open.port

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import java.io.DataOutputStream
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnOpenPort.setOnClickListener {
            runOpenPortShellScript()
        }
    }

    /**
     * Command to open non-rooted device port
     */
    fun runOpenPortShellScript() {
        try {
            val su = Runtime.getRuntime().exec("su")
            val outputStream = DataOutputStream(su.outputStream)
            outputStream.writeBytes("setprop service.adb.tcp.port 5555\n")
            outputStream.flush()

            outputStream.writeBytes("stop adbd\n")
            outputStream.flush()

            outputStream.writeBytes("start adbd\n")
            outputStream.flush()

            outputStream.writeBytes("exit\n")
            outputStream.flush()

            su.waitFor()
        } catch (e: IOException) {
            throw Exception(e)
        } catch (e: InterruptedException) {
            throw Exception(e)
        }
    }
}
