package com.datawedge.utils


object BarcodeUtil {
    private const val LOG_TAG = "BarcodeUtil"

    /**
     * Map of all DataWedge decoder names with their corresponding API parameter names and short names
     * Based on DataWedge 15.0 documentation
     */
    val DECODERS = mapOf(
        // 1D Barcodes
        "decoder_codabar" to "Codabar",
        "decoder_code11" to "Code 11",
        "decoder_code32" to "Code 32",
        "decoder_code39" to "Code 39",
        "decoder_code93" to "Code 93",
        "decoder_code128" to "Code 128",
        "decoder_d2of5" to "Discrete 2of5",
        "decoder_i2of5" to "Interleaved 2of5",
        "decoder_matrix_2of5" to "Matrix 2of5",
        "decoder_chinese_2of5" to "Chinese 2of5",
        "decoder_korean_3of5" to "Korean 3of5",
        "decoder_msi" to "MSI",
        "decoder_tlc39" to "TLC 39",
        "decoder_trioptic39" to "Trioptic39",

        // UPC/EAN
        "decoder_upca" to "UPC-A",
        "decoder_upce0" to "UPC-E0",
        "decoder_upce1" to "UPC-E1",
        "decoder_ean8" to "EAN-8",
        "decoder_ean13" to "EAN-13",

        // GS1 DataBar
        "decoder_gs1_databar" to "GS1 DataBar",
        "decoder_gs1_databar_lim" to "GS1 DataBar Limited",
        "decoder_gs1_databar_exp" to "GS1 DataBar Expanded",

        // 2D Barcodes
        "decoder_qrcode" to "QR",
        "decoder_gs1_qrcode" to "GS1 QR",
        "decoder_microqr" to "MicroQR",
        "decoder_datamatrix" to "DataMatrix",
        "decoder_gs1_datamatrix" to "GS1 DataMatrix",
        "decoder_pdf417" to "PDF417",
        "decoder_micropdf" to "MicroPDF",
        "decoder_maxicode" to "Maxicode",
        "decoder_aztec" to "Aztec",
        "decoder_dotcode" to "DotCode",
        "decoder_hanxin" to "Han Xin",
        "decoder_grid_matrix" to "Grid Matrix",

        // Composite
        "decoder_composite_ab" to "Composite AB",
        "decoder_composite_c" to "Composite C",

        // Postal Codes
        "decoder_australian_postal" to "Australian Postal",
        "decoder_canadian_postal" to "Canadian Postal",
        "decoder_dutch_postal" to "Dutch Postal",
        "decoder_finnish_postal_4s" to "Finnish Postal 4S",
        "decoder_japanese_postal" to "Japanese Postal",
        "decoder_uk_postal" to "UK Postal",
        "decoder_us_postal" to "US Postal",
        "decoder_usplanet" to "USPlanet",
        "decoder_uspostnet" to "USPostnet",
        "decoder_us4state" to "US4state",
        "decoder_us4state_fics" to "US4state FICS",
        "decoder_mailmark" to "Mailmark",

        // OCR
        "decoder_ocr_a" to "OCR A",
        "decoder_ocr_b" to "OCR B",

        // Special
        "decoder_signature" to "Decoder Signature",
        "decoder_micr_e13b" to "MICR E13B",
        "decoder_us_currency" to "US Currency"
    )

    val LABEL_TYPE_MAP = mapOf(
        "LABEL-TYPE-NONE" to "None",
        "LABEL-TYPE-UCCEAN128" to "Code 128",
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
        "LABEL-TYPE-EAN128" to "Code 128",
        "LABEL-TYPE-TRIOPTIC39" to "Trioptic39",
        "LABEL-TYPE-BOOKLAND" to "EAN-13",
        "LABEL-TYPE-COUPON" to "Code 39",
        "LABEL-TYPE-DATABAR14" to "GS1 DataBar",
        "LABEL-TYPE-DATABAR_LTD" to "GS1 DataBar Limited",
        "LABEL-TYPE-DATABAR_EXP" to "GS1 DataBar Expanded",
        "LABEL-TYPE-USPOSTNET" to "USPostnet",
        "LABEL-TYPE-USPLANET" to "USPlanet",
        "LABEL-TYPE-UKPOSTAL" to "UK Postal",
        "LABEL-TYPE-JAPPOSTAL" to "Japanese Postal",
        "LABEL-TYPE-AUSPOSTAL" to "Australian Postal",
        "LABEL-TYPE-DUTCHPOSTAL" to "Dutch Postal",
        "LABEL-TYPE-FINNISHPOSTAL" to "Finnish Postal 4S",
        "LABEL-TYPE-CANPOSTAL" to "Canadian Postal",
        "LABEL-TYPE-CHINESE25" to "Chinese 2of5",
        "LABEL-TYPE-KOREAN35" to "Korean 3of5",
        "LABEL-TYPE-CODE11" to "Code 11",
        "LABEL-TYPE-IATA25" to "Interleaved 2of5",
        "LABEL-TYPE-MATRIX25" to "Matrix 2of5",
        "LABEL-TYPE-AIRLINE25" to "Interleaved 2of5",
        "LABEL-TYPE-INDUSTRIAL25" to "Interleaved 2of5",
        "LABEL-TYPE-STANDARD25" to "Interleaved 2of5",
        "LABEL-TYPE-DATAMATRIX" to "DataMatrix",
        "LABEL-TYPE-QRCODE" to "QR",
        "LABEL-TYPE-MAXICODE" to "Maxicode",
        "LABEL-TYPE-PDF417" to "PDF417",
        "LABEL-TYPE-MICROPDF" to "MicroPDF",
        "LABEL-TYPE-AZTEC" to "Aztec",
        "LABEL-TYPE-COMPOSITE_CC_A" to "Composite AB",
        "LABEL-TYPE-COMPOSITE_CC_B" to "Composite AB",
        "LABEL-TYPE-COMPOSITE_CC_C" to "Composite C",
        "LABEL-TYPE-COMPOSITE_TLC39" to "TLC 39",
        "LABEL-TYPE-SIGNATURE" to "Decoder Signature",
        "LABEL-TYPE-WEBCODE" to "QR",
        "LABEL-TYPE-DISCRETE25" to "Discrete 2of5",
        "LABEL-TYPE-GS1-128" to "Code 128",
        "LABEL-TYPE-GS1-DATABAR" to "GS1 DataBar",
        "LABEL-TYPE-ISBT128" to "Code 128",
        "LABEL-TYPE-MAILMARK" to "Mailmark",
        "LABEL-TYPE-HANXIN" to "Han Xin",
        "LABEL-TYPE-GRIDMATRIX" to "Grid Matrix",
        "LABEL-TYPE-DOTCODE" to "DotCode",
        "LABEL-TYPE-DIGIMARC" to "QR"
    )

    /**
     * Get short name for a decoder API parameter
     */
    fun getDecoderName(decoderApiName: String): String? {
        return DECODERS[decoderApiName]
    }

    /**
     * Get all available decoders as a list of API parameter names
     */
    fun getAllDecoderApiNames(): List<String> {
        return DECODERS.keys.toList()
    }

    /**
     * Get all available decoders as a list of short names
     */
    fun getAllDecoderNames(): List<String> {
        return DECODERS.values.toList()
    }

    /**
     * Check if a decoder API name is valid
     */
    fun isValidDecoderApiName(decoderApiName: String): Boolean {
        return DECODERS.containsKey(decoderApiName)
    }

    /**
     * Filter decoders by an array of decoder names (short names)
     * @param wantedNames Array of decoder short names you want to include
     * @return Map of filtered decoders (API name -> short name)
     */
    fun filterDecodersByNames(wantedNames: Array<String>): Map<String, String> {
        return DECODERS.filter { (_, shortName) -> 
            wantedNames.contains(shortName) 
        }
    }

    /**
     * Filter decoders by an array of API names
     * @param wantedApiNames Array of decoder API names you want to include
     * @return Map of filtered decoders (API name -> short name)
     */
    fun filterDecodersByApiNames(wantedApiNames: Array<String>): Map<String, String> {
        return DECODERS.filter { (apiName, _) -> 
            wantedApiNames.contains(apiName) 
        }
    }

    /**
     * Get only the API names for specific decoder short names
     * @param wantedNames Array of decoder short names
     * @return List of API names for the specified decoders
     */
    fun getApiNamesForDecoders(wantedNames: Array<String>): List<String> {
        return DECODERS.filter { (_, shortName) -> 
            wantedNames.contains(shortName) 
        }.keys.toList()
    }

}