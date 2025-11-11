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
import com.datawedge.utils.BarcodeUtil

class BarcodeModule(private val reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext), LifecycleEventListener {
    override fun getName() = "BarcodeModule"
    
    companion object {
        private const val LOG_TAG = "BarcodeModule"
    }
 
    init {
        reactContext.addLifecycleEventListener(this)
        Log.v(LOG_TAG, "Init")

        createProfile()

        cancelRead();
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

        val filter = IntentFilter()
        filter.addAction("com.symbol.datawedge.api.RESULT_ACTION")
        filter.addAction("com.symbol.datawedge.data_scan")
        filter.addCategory("android.intent.category.DEFAULT")
        reactContext.registerReceiver(broadcastReceiver, filter)

 

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

            // Handle scan data
            if (action == "com.symbol.datawedge.data_scan") {
                val scanData = intent.getStringExtra("com.symbol.datawedge.data_string")
                val scanLabelType = intent.getStringExtra("com.symbol.datawedge.label_type")
                val scanSource = intent.getStringExtra("com.symbol.datawedge.source")
                
                val labelTypeName = BarcodeUtil.LABEL_TYPE_MAP[scanLabelType] ?: scanLabelType
                
                val eventData = Arguments.createMap().apply {
                    putString("data", scanData)
                    putString("type", labelTypeName)
                }
  
                sendEvent("onBarcode", eventData)
                return
            }
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

    fun createProfile() {

        val profileConfig = Bundle().apply {
            putString("PROFILE_NAME", getAppName())
            putString("PROFILE_ENABLED", "true")
            putString("CONFIG_MODE", "CREATE_IF_NOT_EXIST")
        }

        val intentConfig = Bundle().apply {
            putString("PLUGIN_NAME", "INTENT")
            putString("RESET_CONFIG", "true")
        }

        val intentProps = Bundle().apply {
            putString("intent_output_enabled", "true")
            putString("intent_action", "com.symbol.datawedge.data_scan")
            putString("intent_delivery", "2")
        }

        intentConfig.putBundle("PARAM_LIST", intentProps)

        profileConfig.putBundle("PLUGIN_CONFIG", intentConfig)

        // Create APP_LIST bundle to associate app with profile
        val appConfig = Bundle().apply {
            putString("PACKAGE_NAME", getPackageName())
            putStringArray("ACTIVITY_LIST", arrayOf("*"))
        }
        
        profileConfig.putParcelableArray("APP_LIST", arrayOf(appConfig))

        Log.d(LOG_TAG, "Profile Config JSON: ${BundleJsonUtils.bundleToJsonString(profileConfig)}")

        val intent = Intent().apply {        
            this.action = "com.symbol.datawedge.api.ACTION"
            putExtra("com.symbol.datawedge.api.SET_CONFIG", profileConfig)
            putExtra("SEND_RESULT","true")
            putExtra("COMMAND_IDENTIFIER", "CREATE_PROFILE")
        }

        reactContext.sendBroadcast(intent)

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

    @ReactMethod()
    fun read(config: ReadableMap) {

        val typeArray = config.getArray("types")
        var wantedDecoders = arrayOf("QR", "Code 128", "Code 39", "EAN-13", "UPC-A", "PDF417") // Default

        if (typeArray != null && typeArray.size() > 0) {
            val decoderList = mutableListOf<String>()
            for (i in 0 until typeArray.size()) {
                val item = typeArray.getString(i)
                if (item != null) {
                    decoderList.add(item)
                }
            }
            wantedDecoders = decoderList.toTypedArray()
            Log.d(LOG_TAG, "Using decoders from config: ${wantedDecoders.joinToString(", ")}")
        } else {
            Log.d(LOG_TAG, "Using default decoders: ${wantedDecoders.joinToString(", ")}")
        }

        // Main bundle properties
        val profileConfig = Bundle().apply {
            putString("PROFILE_NAME", getAppName())
            putString("PROFILE_ENABLED", "true")
            putString("CONFIG_MODE", "UPDATE")
        }

        // PLUGIN_CONFIG bundle properties for barcode
        val barcodeConfig = Bundle().apply {
            putString("PLUGIN_NAME", "BARCODE")
            putString("RESET_CONFIG", "true")
        }

        // PARAM_LIST bundle properties for barcode
        val barcodeProps = Bundle().apply {
            putString("scanner_selection", "auto")
            putString("scanner_input_enabled", "true")
            putString("decode_haptic_feedback", "true")
        }

        val allDecoders = BarcodeUtil.DECODERS
        val enabledDecoders = BarcodeUtil.filterDecodersByNames(wantedDecoders)
        
        allDecoders.forEach { (apiName, shortName) ->
            val shouldEnable = enabledDecoders.containsKey(apiName)
            val value = if (shouldEnable) "true" else "false"
            barcodeProps.putString(apiName, value)
        }

        // Handle extra params from config
        if (config.hasKey("params")) {
            val paramsArray = config.getArray("params")
            if (paramsArray != null) {
                for (i in 0 until paramsArray.size()) {
                    val paramObject = paramsArray.getMap(i)
                    if (paramObject != null && paramObject.hasKey("key") && paramObject.hasKey("value")) {
                        val key = paramObject.getString("key")
                        val value = paramObject.getString("value")
                        if (key != null && value != null) {
                            barcodeProps.putString(key, value)
                            Log.d(LOG_TAG, "Added extra param: $key = $value")
                        }
                    }
                }
            }
        }

        barcodeConfig.putBundle("PARAM_LIST", barcodeProps)

        profileConfig.putBundle("PLUGIN_CONFIG", barcodeConfig)

        Log.d(LOG_TAG, "profileConfig: ${BundleJsonUtils.bundleToJsonString(profileConfig)}")

        val barcodeIntent = Intent().apply {        
            this.action = "com.symbol.datawedge.api.ACTION"
            putExtra("com.symbol.datawedge.api.SET_CONFIG", profileConfig)
            putExtra("SEND_RESULT","true")
        }

        reactContext.sendBroadcast(barcodeIntent)

        val intent = Intent().apply {        
            this.action = "com.symbol.datawedge.api.ACTION"
            putExtra("com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN", "RESUME_PLUGIN")
            putExtra("SEND_RESULT","true")
        }

        reactContext.sendBroadcast(intent)
    }

    @ReactMethod()
    fun cancelRead(){

        val intent = Intent().apply {        
            this.action = "com.symbol.datawedge.api.ACTION"
            putExtra("com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN", "SUSPEND_PLUGIN")
            putExtra("SEND_RESULT","true")
        }

        reactContext.sendBroadcast(intent)
  
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