import { NativeModules } from 'react-native';

const BarcodeModule = NativeModules.BarcodeModule  ? NativeModules.BarcodeModule : new Proxy(
    {},
    {
      get() {
        throw new Error('');
      },
    }
);

const DataWedgeModule = NativeModules.DataWedgeModule  ? NativeModules.DataWedgeModule : new Proxy(
    {},
    {
      get() {
        throw new Error('');
      },
    }
);

import BarcodeScanner from './BarcodeScanner';

export { BarcodeScanner, BarcodeModule, DataWedgeModule };