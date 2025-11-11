# React Native DataWedge

A React Native module for integrating with Zebra DataWedge on Android devices.

## Installation

```bash
npm install react-native-kega-datawedge
# or
yarn add react-native-kega-datawedge
```

## Usage

### Basic Barcode Scanning

```javascript
import { BarcodeModule } from 'react-native-kega-datawedge';

// Start scanning with default decoders
BarcodeModule.read({
  type: ["QR", "Code 128", "Code 39", "EAN-13", "UPC-A", "PDF417"]
});

// Listen for scan results
BarcodeModule.addEventListener('onBarcode', (scanData) => {
  console.log('Scanned:', scanData.data);
  console.log('Type:', scanData.labelTypeName);
});
```

### Advanced Configuration with Custom Parameters

You can pass additional DataWedge parameters using the `params` array. Each parameter should be an object with `key` and `value` properties:

```javascript
BarcodeModule.read({
  type: ["QR", "Code 128"],
  params: [
    { key: "picklist_enabled", value: "true" },
    { key: "decode_led_feedback", value: "false" },
    { key: "decode_audio_feedback_uri", value: "system_default_notification" },
    { key: "decode_haptic_feedback", value: "true" },
    { key: "illumination_mode", value: "off" }
  ]
});
```

### Configuration Options

#### `type` (Array)
An array of barcode types to enable. See [Supported Barcode Types](./BARCODE_TYPES.md) for a complete list.

Common types include:
- `"QR"`
- `"Code 128"`
- `"Code 39"`
- `"EAN-13"`
- `"UPC-A"`
- `"PDF417"`
- And many more...

#### `params` (Array, optional)
An array of additional DataWedge configuration parameters. Each object in the array should have:
- `key` (string): The DataWedge parameter name
- `value` (string): The parameter value

Common parameters include:
- `picklist_enabled`: Enable/disable picklist mode
- `decode_led_feedback`: Control LED feedback on successful decode
- `decode_audio_feedback_uri`: Set custom audio feedback
- `illumination_mode`: Control camera illumination
- `viewfinder_mode`: Set viewfinder behavior

### Methods

#### `read(config)`
Starts barcode scanning with the specified configuration.

#### `cancelRead()`
Stops barcode scanning.

### Events

#### `onBarcode`
Fired when a barcode is successfully scanned.

**Event data:**
- `data`: The scanned barcode data
- `type`: The raw barcode type code
- `labelTypeName`: Human-readable barcode type name

#### `onBroadcastReceiver`
Fired when DataWedge broadcasts are received (for debugging purposes).

## Requirements

- Android device with Zebra DataWedge installed
- React Native 0.60+
