package com.datawedge

import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.ReadableType
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.LifecycleEventListener

import com.facebook.react.modules.core.DeviceEventManagerModule

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import org.json.JSONException
import com.datawedge.utils.BundleJsonUtils
import com.datawedge.utils.DataWedgeLabelTypes

class DataWedgeModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), LifecycleEventListener {
    override fun getName() = "DataWedgeModule"

    var hasReceiver: Boolean = false
    var actionName: String? = null

    companion object {
        private const val LOG_TAG = "DataWedgeModule"
    }
 
    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            val extras = intent.extras

            Log.v(LOG_TAG, "Received intent with action: $action")

            // Remove arrays (fb converter cannot cope with byte arrays)
            for (key in ArrayList(extras!!.keySet())) {
                val extraValue = extras[key]
                if (extraValue is ByteArray || extraValue is ArrayList<*> || extraValue is ArrayList<*>) {
                    extras.remove(key)
                }
            }

            sendEvent("onBroadcastReceiver", Arguments.fromBundle(extras))

        }
    }

    private fun sendEvent(eventName: String, params: WritableMap?) {
        try {
            reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter::class.java)
                .emit(eventName, params)
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error sending event $eventName", e)
        }
    }

    init {
        reactContext.addLifecycleEventListener(this)
        Log.v(LOG_TAG, "init")
    }

    override fun onHostPause() {
        Log.v(LOG_TAG, "Host Pause")
        try {
            reactContext.unregisterReceiver(broadcastReceiver)
        } catch (e: IllegalArgumentException) {
            //  Expected behaviour if there was not a previously registered receiver.
        }
    }

    override fun onHostDestroy() {
        Log.v(LOG_TAG, "Host Destroy")
        try {
            reactContext.unregisterReceiver(broadcastReceiver)
        } catch (e: IllegalArgumentException) {
            //  Expected behaviour if there was not a previously registered receiver.
        }

    }

    override fun onHostResume() {
        Log.v(LOG_TAG, "Host Resume")

        if (hasReceiver) {
            val filter = IntentFilter()
            filter.addAction("com.symbol.datawedge.api.RESULT_ACTION")
            filter.addAction(actionName)
            filter.addCategory("android.intent.category.DEFAULT")
            reactContext.registerReceiver(broadcastReceiver, filter)
        }


    }

    @ReactMethod
    fun registerReceiver(action: String) {
        actionName = action
        hasReceiver = true
        
        val filter = IntentFilter()
        filter.addAction("com.symbol.datawedge.api.RESULT_ACTION")
        filter.addAction(actionName)
        filter.addCategory("android.intent.category.DEFAULT")

        reactContext.registerReceiver(broadcastReceiver, filter)
    }

    @ReactMethod
    fun broadcastAction(map: ReadableMap) {
        try {
            Log.v(LOG_TAG, "broadcastAction")

            val intent = Intent().apply {
                action = "com.symbol.datawedge.api.ACTION"
                putExtra("SEND_RESULT", "true")
            }

            val key = if (map.hasKey("key")) map.getString("key") else null
            
            Log.d(LOG_TAG, "Action key: $key")

            if (map.getType("data") === ReadableType.String) {
                val data = if (map.hasKey("data")) map.getString("data") else null
                Log.d(LOG_TAG, "Action string: $data")
                intent.putExtra(key, data)
            } else {
                val data = if (map.hasKey("data")) map.getMap("data") else null
                val bundle = BundleJsonUtils.readableMapToBundle(data)

                Log.d(LOG_TAG, "Action bundle: ${BundleJsonUtils.bundleToJsonString(bundle)}")

                intent.putExtra(key, bundle)
            }

            //reactContext.sendBroadcast(intent)
            reactContext.sendOrderedBroadcast(intent, null)
                
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error sending broadcast with extras", e)
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getPackageName(): String? {
        return try {
            reactContext.packageName
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error getting package name", e)
            null
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getAppName(): String? {
        return try {
            val applicationInfo = reactContext.applicationInfo
            val stringId = applicationInfo.labelRes
            if (stringId == 0) {
                applicationInfo.nonLocalizedLabel.toString()
            } else {
                reactContext.getString(stringId)
            }
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error getting app name", e)
            null
        }
    }

}