package com.tradeix.concord.tools.postman

import com.tradeix.concord.tools.postman.configuration.ERPPostmanConfiguration

class Application {
    companion object {
        private val configurations = mapOf(
                "erp" to ERPPostmanConfiguration()
        )

        @JvmStatic
        fun main(args: Array<String>) {
            if (args.isNotEmpty() && configurations.containsKey(args[0])) {
                configurations[args[0]]?.build()
            } else {
                println("Configuration not specified or not mapped.")
            }
        }
    }
}