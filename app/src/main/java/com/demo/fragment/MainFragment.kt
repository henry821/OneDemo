package com.demo.fragment

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.StringBuilderPrinter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.demo.one.R
import java.util.*

/**
 * Description MainFragment
 * Author wanghengwei
 * Date   2020/4/30
 */
class MainFragment : BaseFragment() {

    private lateinit var tvPackageInfo: TextView
    private var packageInfo: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context?.packageManager?.getPackageInfo(context?.packageName,
                PackageManager.GET_META_DATA).also {
            packageInfo = parsePackageInfo(it)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val root = inflater.inflate(R.layout.fragment_main, container, false)

        tvPackageInfo = root.findViewById(R.id.tv_package_info)
        tvPackageInfo.run {
            text = packageInfo
            movementMethod = ScrollingMovementMethod.getInstance()
            setHorizontallyScrolling(true)
        }

        return root
    }

    /**
     * 解析PackageInfo
     */
    private fun parsePackageInfo(packageInfo: PackageInfo?): String? {
        if (packageInfo == null) {
            return "null"
        }
        val stringBuilder = StringBuilder()
        val stringBuilderPrinter = StringBuilderPrinter(stringBuilder)
        packageInfo.run {
            stringBuilder
                    .append("packageName：").append(packageName).append("\r\n")
                    .append("versionName：").append(versionName).append("\r\n")
                    .append("applicationInfo：").append("\r\n")
                    .append(applicationInfo.dump(stringBuilderPrinter, "  "))
                    .append("gids：").append(Arrays.toString(gids)).append("\r\n")
                    .append("sharedUserId：").append(sharedUserId).append("\r\n")
                    .append("activities：").append(Arrays.toString(activities)).append("\r\n")
                    .append("services: ").append(Arrays.toString(services)).append("\r\n")
                    .append("receivers: ").append(Arrays.toString(receivers)).append("\r\n")
                    .append("providers：").append(Arrays.toString(providers)).append("\r\n")
                    .append("permissions：").append(Arrays.toString(permissions)).append("\r\n")
                    .append("requestedPermissions：").append(Arrays.toString(requestedPermissions)).append("\r\n")
                    .append("firstInstallTime：").append(firstInstallTime).append("\r\n")
                    .append("lastUpdateTime：").append(lastUpdateTime).append("\r\n")
                    .append("instrumentation：").append(instrumentation).append("\r\n")
                    .append("configPreferences：").append(Arrays.toString(configPreferences)).append("\r\n")
                    .append("reqFeatures：").append(Arrays.toString(reqFeatures)).append("\r\n")
                    .append("sharedUserLabel：").append(sharedUserLabel).append("\r\n")
        }
        return stringBuilder.toString()
    }
}