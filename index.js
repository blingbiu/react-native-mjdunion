
import { NativeModules } from 'react-native';

const { RNReactNativeMjdunion } = NativeModules;

export async function initSDK(params) {
    return await RNReactNativeMjdunion.initSDK(params);
}

export async function showJXItemByUrl(params) {
    return await RNReactNativeMjdunion.showJXItemByUrl(params);
}

export async function showItemByUrl(params) {
    return await RNReactNativeMjdunion.showItemByUrl(params);
}
