package active.since93.open.port

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.DataOutputStream

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnOpenPort.setOnClickListener {
            runOpenPortShellScript()
        }
        btnGetDeviceIP.setOnClickListener {
            getDeviceIP()
        }
    }

    private fun getDeviceIP() {
        btnGetDeviceIP.text = getString(R.string.device_ip_value, Utils.getIPAddress(true))
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
        } catch (e: Exception) {
            AlertDialog.Builder(this)
                .setMessage(e.toString())
                .setPositiveButton(getString(R.string.okay), null)
                .show()
        }
    }
}
