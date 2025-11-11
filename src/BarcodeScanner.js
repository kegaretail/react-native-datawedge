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

        read: ({ type =  null }) => {
            BarcodeModule.read({ type });
        },

        cancelRead: () => {
            BarcodeModule.cancelRead();
        }
    }
    
})();

export default BarcodeScanner;