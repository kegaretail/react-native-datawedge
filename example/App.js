
import { useEffect, useState } from 'react';

import { StatusBar, StyleSheet, useColorScheme, View, NativeEventEmitter, Text, TouchableOpacity } from 'react-native';
import { SafeAreaProvider, useSafeAreaInsets, } from 'react-native-safe-area-context';

import { NativeModules } from 'react-native';

const { DataWedgeModule } = NativeModules;

import BarcodeScanner from './lib/BarcodeScanner';
/*
function App() {

    useEffect(() => {

        console.log("App Name:", DataWedgeModule.getAppName());
 
        DataWedgeModule.broadcastAction({ 
            key: "com.symbol.datawedge.api.GET_VERSION_INFO", 
            data: ''
        });

        DataWedgeModule.broadcastAction({ 
            key: "com.symbol.datawedge.api.SET_CONFIG", 
            data: {
                PROFILE_NAME: DataWedgeModule.getAppName(),
                PROFILE_ENABLED: 'true',
                CONFIG_MODE: 'CREATE_IF_NOT_EXIST',
                PLUGIN_CONFIG: {
                    PLUGIN_NAME: 'INTENT',
                    RESET_CONFIG: 'true',
                    PARAM_LIST: {
                        intent_output_enabled: 'true',
                        intent_action: 'com.symbol.datawedge.data_scan',
                        intent_delivery: '2'
                    }
                },
                APP_LIST: [{
                    PACKAGE_NAME: DataWedgeModule.getPackageName(),
                    ACTIVITY_LIST: ['*'],
                }]
            }
        });

        DataWedgeModule.broadcastAction({ 
            key: "com.symbol.datawedge.api.SET_CONFIG", 
            data: {
                PROFILE_NAME: DataWedgeModule.getAppName(),
                PROFILE_ENABLED: 'true',
                CONFIG_MODE: 'UPDATE',
                PLUGIN_CONFIG: {
                    PLUGIN_NAME: 'BARCODE',
                    RESET_CONFIG: 'true',
                    PARAM_LIST: {
                        scanner_selection: 'auto',
                        decoder_ean8: 'true',
                        decoder_ean13: 'true',
                        decoder_code128: 'true',
                        decoder_code39: 'true',
                    }
                }
           
            }
        });

        DataWedgeModule.broadcastAction({ 
            key: "com.symbol.datawedge.api.SCANNER_INPUT_PLUGIN", 
            data: "SUSPEND_PLUGIN"
        });

        const eventEmitter = new NativeEventEmitter();
        const subscription = eventEmitter.addListener('onBroadcastReceiver', (data) => {
            console.log('BroadcastReceiver data:', data);
        });

        return () => {
            subscription.remove();
        };
    }, []);

  
  return (
    <SafeAreaProvider>
      <StatusBar barStyle={'light-content'} />
      <AppContent />
    </SafeAreaProvider>
  );
}
*/
function App() {
    const [ isScanning, setIsScanning ] = useState(false);
    const [ lastScanData, setLastScanData ] = useState(null);

    useEffect(() => {

        const onBarcodeEvent = BarcodeScanner.onBarcode((data) => {
            console.log('onBarcode', data)

        })

        BarcodeScanner.read({ types: [ 'ean13', 'code128' ] });

        return () => {
            onBarcodeEvent.remove();
            BarcodeScanner.cancelRead();
        };

    }, []);

  return (
     <SafeAreaProvider>
        <View style={styles.container}>
            <View style={styles.content}>
       
            </View>
        </View>
    </SafeAreaProvider>
  );
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: '#f5f5f5',
    },
    content: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        padding: 20,
    },
    title: {
        fontSize: 24,
        fontWeight: 'bold',
        marginBottom: 40,
        textAlign: 'center',
        color: '#333',
    },
    button: {
        paddingVertical: 15,
        paddingHorizontal: 30,
        borderRadius: 10,
        marginVertical: 10,
        minWidth: 200,
        shadowColor: '#000',
        shadowOffset: {
            width: 0,
            height: 2,
        },
        shadowOpacity: 0.25,
        shadowRadius: 3.84,
        elevation: 5,
    },
    startButton: {
        backgroundColor: '#4CAF50',
    },
    stopButton: {
        backgroundColor: '#f44336',
    },
    buttonText: {
        color: 'white',
        fontSize: 18,
        fontWeight: 'bold',
        textAlign: 'center',
    },
    statusContainer: {
        marginTop: 30,
        padding: 15,
        backgroundColor: 'white',
        borderRadius: 10,
        shadowColor: '#000',
        shadowOffset: {
            width: 0,
            height: 1,
        },
        shadowOpacity: 0.22,
        shadowRadius: 2.22,
        elevation: 3,
    },
    statusText: {
        fontSize: 16,
        color: '#666',
        textAlign: 'center',
    },
    scanResultContainer: {
        marginTop: 20,
        padding: 15,
        backgroundColor: 'white',
        borderRadius: 10,
        borderLeftWidth: 4,
        borderLeftColor: '#2196F3',
        shadowColor: '#000',
        shadowOffset: {
            width: 0,
            height: 1,
        },
        shadowOpacity: 0.22,
        shadowRadius: 2.22,
        elevation: 3,
        width: '100%',
    },
    scanResultTitle: {
        fontSize: 16,
        fontWeight: 'bold',
        color: '#333',
        marginBottom: 10,
    },
    scanResultData: {
        fontSize: 14,
        color: '#666',
        marginBottom: 5,
        fontFamily: 'monospace',
    },
});

export default App;
