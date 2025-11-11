package com.datawedge.utils

import android.os.Bundle
import android.util.Log
import org.json.JSONObject
import org.json.JSONArray
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.ReadableType
import com.facebook.react.bridge.ReadableArray

/**
 * Utility class for converting between Android Bundle and JSON string
 */
object BundleJsonUtils {
    private const val LOG_TAG = "BundleJsonUtils"

    /**
     * Converts a Bundle to JSON string for logging purposes
     */
    fun bundleToJsonString(bundle: Bundle?): String {
        if (bundle == null) return "null"
        
        return try {
            val json = JSONObject()
            for (key in bundle.keySet()) {
                val value = bundle.get(key)
   
                when (value) {
                    is String -> json.put(key, value)
                    is Int -> json.put(key, value)
                    is Boolean -> json.put(key, value)
                    is Long -> json.put(key, value)
                    is Double -> json.put(key, value)
                    is Float -> json.put(key, value)
                    is Bundle -> json.put(key, JSONObject(bundleToJsonString(value)))
                    is Array<*> -> {
                        val jsonArray = JSONArray()
                        value.forEach { item ->
                            when (item) {
                                is Bundle -> jsonArray.put(JSONObject(bundleToJsonString(item)))
                                is String -> jsonArray.put(item)
                                else -> jsonArray.put(item.toString())
                            }
                        }
                        json.put(key, jsonArray)
                    }
                    is ArrayList<*> -> {
                        val jsonArray = JSONArray()
                        value.forEach { item ->
                            when (item) {
                                is String -> jsonArray.put(item)
                                else -> jsonArray.put(item.toString())
                            }
                        }
                        json.put(key, jsonArray)
                    }
                    else -> json.put(key, value?.toString() ?: "null")
                }
            }
            json.toString(2) // Pretty print with 2 space indentation
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error converting bundle to JSON", e)
            "Error converting bundle to JSON: ${e.message}"
        }
    }

    /**
     * Converts a JSON string to Bundle for configuration purposes
     */
    fun jsonStringToBundle(jsonString: String?): Bundle? {
        if (jsonString.isNullOrEmpty()) return null
        
        return try {
            val json = JSONObject(jsonString)
            jsonObjectToBundle(json)
        } catch (e: Exception) {
            Log.e(LOG_TAG, "Error converting JSON to bundle", e)
            null
        }
    }

    /**
     * Helper function to recursively convert JSONObject to Bundle
     */
    private fun jsonObjectToBundle(jsonObject: JSONObject): Bundle {
        val bundle = Bundle()
        
        val keys = jsonObject.keys()
        while (keys.hasNext()) {
            val key = keys.next()
            val value = jsonObject.get(key)
            
            when (value) {
                is String -> bundle.putString(key, value)
                is Int -> bundle.putInt(key, value)
                is Boolean -> bundle.putBoolean(key, value)
                is Long -> bundle.putLong(key, value)
                is Double -> bundle.putDouble(key, value)
                is Float -> bundle.putFloat(key, value)
                is JSONObject -> bundle.putBundle(key, jsonObjectToBundle(value))
                is JSONArray -> {
                    // Handle arrays - check if it's an array of bundles or primitives
                    val arrayList = ArrayList<String>()
                    val bundleArray = mutableListOf<Bundle>()
                    var isBundleArray = false
                    
                    for (i in 0 until value.length()) {
                        val item = value.get(i)
                        when (item) {
                            is JSONObject -> {
                                isBundleArray = true
                                bundleArray.add(jsonObjectToBundle(item))
                            }
                            is String -> arrayList.add(item)
                            else -> arrayList.add(item.toString())
                        }
                    }
                    
                    if (isBundleArray) {
                        bundle.putParcelableArray(key, bundleArray.toTypedArray())
                    } else {
                        bundle.putStringArray(key, arrayList.toTypedArray())
                    }
                }
                JSONObject.NULL -> {
                    // Handle null values - skip them or set as empty string
                    bundle.putString(key, "")
                }
                else -> bundle.putString(key, value.toString())
            }
        }
        
        return bundle
    }

    /**
     * Converts a React Native ReadableMap to Android Bundle
     */
    fun readableMapToBundle(readableMap: ReadableMap?): Bundle? {
        if (readableMap == null) return null
        
        val bundle = Bundle()
        val iterator = readableMap.entryIterator
        
        while (iterator.hasNext()) {
            val pair = iterator.next()
            val key = pair.key
            val value = pair.value
            
            when (value) {
                is String -> bundle.putString(key, value)
                is Int -> bundle.putInt(key, value)
                is Double -> bundle.putDouble(key, value)
                is Boolean -> bundle.putBoolean(key, value)
                is ReadableMap -> bundle.putBundle(key, readableMapToBundle(value))
                is ReadableArray -> {
                    // Handle arrays - check if it contains objects or primitives
                    val bundleArray = mutableListOf<Bundle>()
                    val stringArray = mutableListOf<String>()
                    var containsObjects = false
                    
                    for (i in 0 until value.size()) {
                        when (value.getType(i)) {
                            ReadableType.Map -> {
                                containsObjects = true
                                val nestedMap = value.getMap(i)
                                bundleArray.add(readableMapToBundle(nestedMap) ?: Bundle())
                            }
                            ReadableType.String -> {
                                stringArray.add(value.getString(i) ?: "")
                            }
                            ReadableType.Number -> {
                                stringArray.add(value.getDouble(i).toString())
                            }
                            ReadableType.Boolean -> {
                                stringArray.add(value.getBoolean(i).toString())
                            }
                            else -> {
                                stringArray.add(value.getDynamic(i).toString())
                            }
                        }
                    }
                    
                    if (containsObjects) {
                        bundle.putParcelableArray(key, bundleArray.toTypedArray())
                    } else {
                        bundle.putStringArray(key, stringArray.toTypedArray())
                    }
                }
                null -> bundle.putString(key, "")
                else -> bundle.putString(key, value.toString())
            }
        }
        
        return bundle
    }
}