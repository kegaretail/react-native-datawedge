# Supported Barcode Types

This document lists all barcode types supported by the React Native DataWedge module. These types can be used in the `type` array when calling `BarcodeModule.read()`.

## 1D Barcodes

### Linear Barcodes

| Type Name | Description | Common Use Cases |
|-----------|-------------|------------------|
| `Codabar` | Variable length, numeric + 4 special chars | Libraries, photo labs, blood banks |
| `Code 11` | Numeric + dash | Telecommunications |
| `Code 32` | Italian pharmaceutical standard | Pharmaceutical industry (Italy) |
| `Code 39` | Alphanumeric | General purpose, automotive, defense |
| `Code 93` | Alphanumeric, higher density than Code 39 | Canada Post, logistics |
| `Code 128` | Full ASCII character set | Shipping, packaging, general purpose |
| `Discrete 2of5` | Numeric only | Industrial applications |
| `Interleaved 2of5` | Numeric, high density | Warehouse, distribution |
| `Matrix 2of5` | Numeric | Industrial applications |
| `Chinese 2of5` | Numeric, Chinese standard | Chinese market applications |
| `Korean 3of5` | Numeric, Korean standard | Korean market applications |
| `MSI` | Numeric | Inventory control, marking storage containers |
| `TLC 39` | Three-level Code 39 | Automotive industry |
| `Trioptic39` | Modified Code 39 | FedEx applications |

### UPC/EAN Product Codes

| Type Name | Description | Common Use Cases |
|-----------|-------------|------------------|
| `UPC-A` | 12-digit product code | North American retail |
| `UPC-E0` | 8-digit compressed UPC-A | Small packages |
| `UPC-E1` | 8-digit compressed UPC-A | Small packages |
| `EAN-8` | 8-digit European product code | Small packages (European) |
| `EAN-13` | 13-digit European product code | International retail |

### GS1 DataBar

| Type Name | Description | Common Use Cases |
|-----------|-------------|------------------|
| `GS1 DataBar` | Linear barcode for small items | Fresh foods, coupons |
| `GS1 DataBar Limited` | Fixed length DataBar | Small healthcare items |
| `GS1 DataBar Expanded` | Variable length with application identifiers | Healthcare, general merchandise |

## 2D Barcodes

### Matrix Codes

| Type Name | Description | Common Use Cases |
|-----------|-------------|------------------|
| `QR` | Quick Response code | Marketing, URLs, contact info |
| `GS1 QR` | GS1 standard QR code | Supply chain, product information |
| `MicroQR` | Smaller version of QR | Space-constrained applications |
| `DataMatrix` | High-density matrix code | Electronics, automotive, aerospace |
| `GS1 DataMatrix` | GS1 standard DataMatrix | Healthcare, pharmaceuticals |
| `Aztec` | Public domain 2D code | Transportation, tickets |
| `Maxicode` | Fixed-size matrix code | UPS shipping |
| `Han Xin` | Chinese national standard | Chinese applications |
| `Grid Matrix` | Chinese 2D standard | Chinese market |

### PDF Codes

| Type Name | Description | Common Use Cases |
|-----------|-------------|------------------|
| `PDF417` | Portable Data File | ID cards, transportation, inventory |
| `MicroPDF` | Smaller version of PDF417 | Space-constrained applications |

### Special 2D Codes

| Type Name | Description | Common Use Cases |
|-----------|-------------|------------------|
| `DotCode` | Dot-based 2D code | High-speed printing applications |

## Composite Codes

| Type Name | Description | Common Use Cases |
|-----------|-------------|------------------|
| `Composite AB` | Combines linear and 2D components | GS1 applications |
| `Composite C` | 2D component only | GS1 applications |

## Postal Codes

| Type Name | Description | Common Use Cases |
|-----------|-------------|------------------|
| `Australian Postal` | Australia Post barcode | Australian mail |
| `Canadian Postal` | Canada Post barcode | Canadian mail |
| `Dutch Postal` | PostNL barcode | Dutch mail |
| `Finnish Postal 4S` | Finland Post barcode | Finnish mail |
| `Japanese Postal` | Japan Post barcode | Japanese mail |
| `UK Postal` | Royal Mail barcode | UK mail |
| `US Postal` | USPS Intelligent Mail | US mail |
| `USPlanet` | USPS Planet Code | US mail routing |
| `USPostnet` | USPS Postnet | US mail (legacy) |
| `US4state` | USPS 4-State | US mail |
| `US4state FICS` | USPS 4-State FICS | US mail |
| `Mailmark` | Royal Mail Mailmark | UK mail (modern) |

## OCR (Optical Character Recognition)

| Type Name | Description | Common Use Cases |
|-----------|-------------|------------------|
| `OCR A` | Machine-readable font | Passports, documents |
| `OCR B` | Machine-readable font | Banking, documents |

## Special Types

| Type Name | Description | Common Use Cases |
|-----------|-------------|------------------|
| `Decoder Signature` | Signature capture | Document verification |
| `MICR E13B` | Magnetic Ink Character Recognition | Banking, checks |
| `US Currency` | US currency recognition | Financial applications |

## Usage Examples

### Common Retail Setup
```javascript
BarcodeModule.read({
  type: ["UPC-A", "UPC-E0", "UPC-E1", "EAN-8", "EAN-13", "Code 128"]
});
```

### Warehouse/Logistics Setup
```javascript
BarcodeModule.read({
  type: ["Code 128", "Code 39", "Interleaved 2of5", "QR", "PDF417"]
});
```

### Modern Mobile Applications
```javascript
BarcodeModule.read({
  type: ["QR", "DataMatrix", "PDF417", "Aztec"]
});
```

### Pharmaceutical/Healthcare
```javascript
BarcodeModule.read({
  type: ["GS1 DataMatrix", "GS1 QR", "Code 128", "DataMatrix"]
});
```

## Notes

- Not all barcode types may be available on all devices
- Some barcode types require specific licensing from Zebra
- Performance may vary depending on the number of enabled decoders
- For best performance, enable only the barcode types you actually need

## Related Documentation

- [DataWedge Decoder Configuration](https://techdocs.zebra.com/datawedge/)
- [GS1 Standards](https://www.gs1.org/)