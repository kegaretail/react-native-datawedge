import { NativeModules, NativeEventEmitter } from 'react-native';

const {  BarcodeModule } = NativeModules;

const BarcodeScanner = (() => {

    const eventEmitter = new NativeEventEmitter();
  
    return {

        onBroadcastReceiver: (callback) => {
            return eventEmitter.addListener('onBroadcastReceiver', callback);
        },

        onBarcode: (callback) => {
            return eventEmitter.addListener('onBarcode', callback);
        },

        read: ({ types =  null }) => {
            BarcodeModule.read({ types });
        },

        cancelRead: () => {
            BarcodeModule.cancelRead();
        }
    }
    
})();

export default BarcodeScanner;