package com.datawedge.utils

/**
 * DataWedge Label Types Mapping
 * 
 * This class provides a mapping of DataWedge label types to their corresponding
 * human-readable names and descriptions.
 * 
 * Reference: DataWedge API documentation for com.symbol.datawedge.label_type values
 */
object DataWedgeLabelTypes {
    
    /**
     * Map of label type values to their descriptive names
     */
    val LABEL_TYPE_MAP = mapOf(
        "LABEL-TYPE-NONE" to "None",
        "LABEL-TYPE-UCCEAN128" to "UCC/EAN 128",
        "LABEL-TYPE-CODE128" to "Code 128",
        "LABEL-TYPE-CODE39" to "Code 39",
        "LABEL-TYPE-CODE93" to "Code 93",
        "LABEL-TYPE-CODABAR" to "Codabar",
        "LABEL-TYPE-UPCA" to "UPC-A",
        "LABEL-TYPE-UPCE0" to "UPC-E0",
        "LABEL-TYPE-UPCE1" to "UPC-E1",
        "LABEL-TYPE-EAN8" to "EAN-8",
        "LABEL-TYPE-EAN13" to "EAN-13",
        "LABEL-TYPE-MSI" to "MSI",
        "LABEL-TYPE-EAN128" to "EAN-128",
        "LABEL-TYPE-TRIOPTIC39" to "Trioptic Code 39",
        "LABEL-TYPE-BOOKLAND" to "Bookland EAN",
        "LABEL-TYPE-COUPON" to "Coupon Code",
        "LABEL-TYPE-DATABAR14" to "GS1 DataBar-14",
        "LABEL-TYPE-DATABAR_LTD" to "GS1 DataBar Limited",
        "LABEL-TYPE-DATABAR_EXP" to "GS1 DataBar Expanded",
        "LABEL-TYPE-USPOSTNET" to "US Postnet",
        "LABEL-TYPE-USPLANET" to "US Planet",
        "LABEL-TYPE-UKPOSTAL" to "UK Postal",
        "LABEL-TYPE-JAPPOSTAL" to "Japan Postal",
        "LABEL-TYPE-AUSPOSTAL" to "Australian Postal",
        "LABEL-TYPE-DUTCHPOSTAL" to "Dutch Postal",
        "LABEL-TYPE-FINNISHPOSTAL" to "Finnish Postal",
        "LABEL-TYPE-CANPOSTAL" to "Canadian Postal",
        "LABEL-TYPE-CHINESE25" to "Chinese 2 of 5",
        "LABEL-TYPE-KOREAN35" to "Korean 3 of 5",
        "LABEL-TYPE-CODE11" to "Code 11",
        "LABEL-TYPE-IATA25" to "IATA 2 of 5",
        "LABEL-TYPE-MATRIX25" to "Matrix 2 of 5",
        "LABEL-TYPE-AIRLINE25" to "Airline 2 of 5",
        "LABEL-TYPE-INDUSTRIAL25" to "Industrial 2 of 5",
        "LABEL-TYPE-STANDARD25" to "Standard 2 of 5",
        "LABEL-TYPE-DATAMATRIX" to "Data Matrix",
        "LABEL-TYPE-QRCODE" to "QR Code",
        "LABEL-TYPE-MAXICODE" to "MaxiCode",
        "LABEL-TYPE-PDF417" to "PDF417",
        "LABEL-TYPE-MICROPDF" to "MicroPDF417",
        "LABEL-TYPE-AZTEC" to "Aztec",
        "LABEL-TYPE-COMPOSITE_CC_A" to "Composite CC-A",
        "LABEL-TYPE-COMPOSITE_CC_B" to "Composite CC-B",
        "LABEL-TYPE-COMPOSITE_CC_C" to "Composite CC-C",
        "LABEL-TYPE-COMPOSITE_TLC39" to "Composite TLC-39",
        "LABEL-TYPE-SIGNATURE" to "Signature Capture",
        "LABEL-TYPE-WEBCODE" to "Web Code",
        "LABEL-TYPE-DISCRETE25" to "Discrete 2 of 5",
        "LABEL-TYPE-GS1-128" to "GS1-128",
        "LABEL-TYPE-GS1-DATABAR" to "GS1 DataBar",
        "LABEL-TYPE-ISBT128" to "ISBT 128",
        "LABEL-TYPE-MAILMARK" to "Royal Mail Mailmark",
        "LABEL-TYPE-HANXIN" to "Han Xin Code",
        "LABEL-TYPE-GRIDMATRIX" to "Grid Matrix",
        "LABEL-TYPE-DOTCODE" to "DotCode",
        "LABEL-TYPE-DIGIMARC" to "Digimarc"
    )
    
    /**
     * Get the human-readable name for a label type
     * @param labelType The raw label type string from DataWedge
     * @return Human-readable name or the original string if not found
     */
    fun getLabelTypeName(labelType: String?): String {
        return if (labelType != null) {
            LABEL_TYPE_MAP[labelType] ?: labelType
        } else {
            "Unknown"
        }
    }
    
    /**
     * Check if a label type is a 1D barcode
     */
    fun is1DBarcode(labelType: String?): Boolean {
        return when (labelType) {
            "LABEL-TYPE-CODE128",
            "LABEL-TYPE-CODE39",
            "LABEL-TYPE-CODE93",
            "LABEL-TYPE-CODABAR",
            "LABEL-TYPE-UPCA",
            "LABEL-TYPE-UPCE0",
            "LABEL-TYPE-UPCE1",
            "LABEL-TYPE-EAN8",
            "LABEL-TYPE-EAN13",
            "LABEL-TYPE-MSI",
            "LABEL-TYPE-EAN128",
            "LABEL-TYPE-UCCEAN128",
            "LABEL-TYPE-GS1-128",
            "LABEL-TYPE-ISBT128",
            "LABEL-TYPE-CODE11",
            "LABEL-TYPE-CHINESE25",
            "LABEL-TYPE-KOREAN35",
            "LABEL-TYPE-IATA25",
            "LABEL-TYPE-MATRIX25",
            "LABEL-TYPE-AIRLINE25",
            "LABEL-TYPE-INDUSTRIAL25",
            "LABEL-TYPE-STANDARD25",
            "LABEL-TYPE-DISCRETE25",
            "LABEL-TYPE-DATABAR14",
            "LABEL-TYPE-DATABAR_LTD",
            "LABEL-TYPE-DATABAR_EXP",
            "LABEL-TYPE-GS1-DATABAR" -> true
            else -> false
        }
    }
    
    /**
     * Check if a label type is a 2D barcode
     */
    fun is2DBarcode(labelType: String?): Boolean {
        return when (labelType) {
            "LABEL-TYPE-DATAMATRIX",
            "LABEL-TYPE-QRCODE",
            "LABEL-TYPE-MAXICODE",
            "LABEL-TYPE-PDF417",
            "LABEL-TYPE-MICROPDF",
            "LABEL-TYPE-AZTEC",
            "LABEL-TYPE-HANXIN",
            "LABEL-TYPE-GRIDMATRIX",
            "LABEL-TYPE-DOTCODE" -> true
            else -> false
        }
    }
    
    /**
     * Check if a label type is a postal code
     */
    fun isPostalCode(labelType: String?): Boolean {
        return when (labelType) {
            "LABEL-TYPE-USPOSTNET",
            "LABEL-TYPE-USPLANET",
            "LABEL-TYPE-UKPOSTAL",
            "LABEL-TYPE-JAPPOSTAL",
            "LABEL-TYPE-AUSPOSTAL",
            "LABEL-TYPE-DUTCHPOSTAL",
            "LABEL-TYPE-FINNISHPOSTAL",
            "LABEL-TYPE-CANPOSTAL",
            "LABEL-TYPE-MAILMARK" -> true
            else -> false
        }
    }
    
    /**
     * Check if a label type is a composite code
     */
    fun isComposite(labelType: String?): Boolean {
        return when (labelType) {
            "LABEL-TYPE-COMPOSITE_CC_A",
            "LABEL-TYPE-COMPOSITE_CC_B",
            "LABEL-TYPE-COMPOSITE_CC_C",
            "LABEL-TYPE-COMPOSITE_TLC39" -> true
            else -> false
        }
    }
    
    /**
     * API Decoder Names for DataWedge SetConfig API
     * 
     * Complete list of decoder names that can be used with DataWedge SetConfig API
     * to enable/disable specific barcode decoders.
     * 
     * Reference: https://techdocs.zebra.com/datawedge/15-0/guide/decoders
     * 
     * Usage example:
     * val bundle = Bundle()
     * bundle.putString("PLUGIN_NAME", "BARCODE")
     * bundle.putString("RESET_CONFIG", "true")
     * 
     * val bParams = Bundle()
     * bParams.putString(DataWedgeDecoderNames.DECODER_CODE128, "true")
     * bParams.putString(DataWedgeDecoderNames.DECODER_CODE39, "true")
     * bundle.putBundle("PARAM_LIST", bParams)
     */
    object DataWedgeDecoderNames {
        // 1D Barcodes
        const val DECODER_CODABAR = "decoder_codabar"
        const val DECODER_CODE11 = "decoder_code11"
        const val DECODER_CODE32 = "decoder_code32"
        const val DECODER_CODE39 = "decoder_code39"
        const val DECODER_CODE93 = "decoder_code93"
        const val DECODER_CODE128 = "decoder_code128"
        const val DECODER_D2OF5 = "decoder_d2of5"  // Discrete 2of5
        const val DECODER_I2OF5 = "decoder_i2of5"  // Interleaved 2 of 5
        const val DECODER_CHINESE_2OF5 = "decoder_chinese_2of5"
        const val DECODER_KOREAN_3OF5 = "decoder_korean_3of5"
        const val DECODER_MATRIX_2OF5 = "decoder_matrix_2of5"
        const val DECODER_MSI = "decoder_msi"
        const val DECODER_TRIOPTIC39 = "decoder_trioptic39"
        
        // UPC/EAN Barcodes
        const val DECODER_EAN8 = "decoder_ean8"
        const val DECODER_EAN13 = "decoder_ean13"
        const val DECODER_UPCA = "decoder_upca"
        const val DECODER_UPCE0 = "decoder_upce0"
        const val DECODER_UPCE1 = "decoder_upce1"
        
        // GS1 DataBar
        const val DECODER_GS1_DATABAR = "decoder_gs1_databar"
        const val DECODER_GS1_DATABAR_LIMITED = "decoder_gs1_databar_lim"
        const val DECODER_GS1_DATABAR_EXPANDED = "decoder_gs1_databar_exp"
        
        // 2D Barcodes
        const val DECODER_AZTEC = "decoder_aztec"
        const val DECODER_DATAMATRIX = "decoder_datamatrix"
        const val DECODER_DOTCODE = "decoder_dotcode"
        const val DECODER_GRID_MATRIX = "decoder_grid_matrix"
        const val DECODER_HANXIN = "decoder_hanxin"
        const val DECODER_MAXICODE = "decoder_maxicode"
        const val DECODER_MICROPDF = "decoder_micropdf"
        const val DECODER_MICROQR = "decoder_microqr"
        const val DECODER_PDF417 = "decoder_pdf417"
        const val DECODER_QRCODE = "decoder_qrcode"
        
        // GS1 2D Barcodes
        const val DECODER_GS1_DATAMATRIX = "decoder_gs1_datamatrix"
        const val DECODER_GS1_QRCODE = "decoder_gs1_qrcode"
        
        // Composite Codes
        const val DECODER_COMPOSITE_AB = "decoder_composite_ab"
        const val DECODER_COMPOSITE_C = "decoder_composite_c"
        
        // Special Decoders
        const val DECODER_SIGNATURE = "decoder_signature"  // Signature Capture
        const val DECODER_TLC39 = "decoder_tlc39"
        
        // Postal Codes
        const val DECODER_AUSTRALIAN_POSTAL = "decoder_australian_postal"
        const val DECODER_CANADIAN_POSTAL = "decoder_canadian_postal"
        const val DECODER_DUTCH_POSTAL = "decoder_dutch_postal"
        const val DECODER_FINNISH_POSTAL_4S = "decoder_finnish_postal_4s"
        const val DECODER_JAPANESE_POSTAL = "decoder_japanese_postal"
        const val DECODER_MAILMARK = "decoder_mailmark"
        const val DECODER_UK_POSTAL = "decoder_uk_postal"
        const val DECODER_US_POSTAL = "decoder_us_postal"
        const val DECODER_USPLANET = "decoder_usplanet"
        const val DECODER_USPOSTNET = "decoder_uspostnet"
        const val DECODER_US4STATE = "decoder_us4state"
        const val DECODER_US4STATE_FICS = "decoder_us4state_fics"
        
        // OCR
        const val DECODER_OCR_A = "decoder_ocr_a"
        const val DECODER_OCR_B = "decoder_ocr_b"
        
        // Currency
        const val DECODER_US_CURRENCY = "decoder_us_currency"
        const val DECODER_MICR_E13B = "decoder_micr_e13b"
        
        /**
         * Get all decoder names as an ArrayList
         * @return ArrayList containing all API decoder names
         */
        fun getAllDecoderNames(): ArrayList<String> {
            return arrayListOf(
                // 1D Barcodes
                DECODER_CODABAR,
                DECODER_CODE11,
                DECODER_CODE32,
                DECODER_CODE39,
                DECODER_CODE93,
                DECODER_CODE128,
                DECODER_D2OF5,
                DECODER_I2OF5,
                DECODER_CHINESE_2OF5,
                DECODER_KOREAN_3OF5,
                DECODER_MATRIX_2OF5,
                DECODER_MSI,
                DECODER_TRIOPTIC39,
                
                // UPC/EAN Barcodes
                DECODER_EAN8,
                DECODER_EAN13,
                DECODER_UPCA,
                DECODER_UPCE0,
                DECODER_UPCE1,
                
                // GS1 DataBar
                DECODER_GS1_DATABAR,
                DECODER_GS1_DATABAR_LIMITED,
                DECODER_GS1_DATABAR_EXPANDED,
                
                // 2D Barcodes
                DECODER_AZTEC,
                DECODER_DATAMATRIX,
                DECODER_DOTCODE,
                DECODER_GRID_MATRIX,
                DECODER_HANXIN,
                DECODER_MAXICODE,
                DECODER_MICROPDF,
                DECODER_MICROQR,
                DECODER_PDF417,
                DECODER_QRCODE,
                
                // GS1 2D Barcodes
                DECODER_GS1_DATAMATRIX,
                DECODER_GS1_QRCODE,
                
                // Composite Codes
                DECODER_COMPOSITE_AB,
                DECODER_COMPOSITE_C,
                
                // Special Decoders
                DECODER_SIGNATURE,
                DECODER_TLC39,
                
                // Postal Codes
                DECODER_AUSTRALIAN_POSTAL,
                DECODER_CANADIAN_POSTAL,
                DECODER_DUTCH_POSTAL,
                DECODER_FINNISH_POSTAL_4S,
                DECODER_JAPANESE_POSTAL,
                DECODER_MAILMARK,
                DECODER_UK_POSTAL,
                DECODER_US_POSTAL,
                DECODER_USPLANET,
                DECODER_USPOSTNET,
                DECODER_US4STATE,
                DECODER_US4STATE_FICS,
                
                // OCR
                DECODER_OCR_A,
                DECODER_OCR_B,
                
                // Currency
                DECODER_US_CURRENCY,
                DECODER_MICR_E13B
            )
        }
  
        /**
         * Find a single decoder name that exactly matches a search term (case-insensitive)
         * Useful when you know the exact decoder type you're looking for
         * @param searchTerm The term to search for (e.g., "ean13", "code128", "qrcode")
         * @return The exact decoder name if found, null otherwise
         * 
         * Examples:
         * - findExactDecoder("ean13") returns "decoder_ean13"
         * - findExactDecoder("code128") returns "decoder_code128"
         * - findExactDecoder("qrcode") returns "decoder_qrcode"
         */
        fun findExactDecoder(searchTerm: String): String? {
            val searchLower = searchTerm.lowercase()
            val allDecoders = getAllDecoderNames()
            
            // First try exact match after "decoder_"
            val exactMatch = allDecoders.find { decoder ->
                decoder.substringAfter("decoder_").lowercase() == searchLower
            }
            
            if (exactMatch != null) {
                return exactMatch
            }
            
            // If no exact match, try partial match but return only if it's a single result
            val partialMatches = allDecoders.filter { decoder ->
                decoder.lowercase().contains(searchLower)
            }
            
            return if (partialMatches.size == 1) {
                partialMatches.first()
            } else {
                null
            }
        }
        
    }
}