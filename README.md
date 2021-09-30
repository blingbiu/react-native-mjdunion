# react-native-mjdunion

京东联盟SDK

## Getting started

`$ npm install react-native-mjdunion --save`

### Mostly automatic installation

`$ react-native link react-native-mjdunion`

### Manual installation


#### iOS

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-mjdunio` and add `RNReactNativeMjdunion.xcodeproj`
3. In XCode, in the project navigator, select your project. Add `libRNReactNativeMjdunion.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)<

#### Android

1. Open up `android/app/src/main/java/[...]/MainActivity.java`
  - Add `import com.reactlibrary.RNReactNativeMjdunionPackage;` to the imports at the top of the file
  - Add `new RNReactNativeMjdunionPackage()` to the list returned by the `getPackages()` method
2. Append the following lines to `android/settings.gradle`:
    	```
    	include ':react-native-mjdunion'
    	project(':react-native-mjdunion').projectDir = new File(rootProject.projectDir, 	'../node_modules/react-native-mjdunion/android')
  ```
3. Insert the following lines inside the dependencies block in `android/app/build.gradle`:
    	```
      compile project(':react-native-mjdunion')
  ```

## 其他配置

#### ios
将下载的京东联盟iOS SDK（包含 JDSDK.framework 和 JDSDK.bundle）引入到项目中
URL Schemes 添加 sdkback + 京东联盟appKey
LSApplicationQueriesSchemes 添加 openapp.jdpingou


#### Android
1. 替换模块中的安全图片`android/app/src/main/res/raw/safe.jpg`
2. 打开 android/app/build.gradle ，在 defaultConfig 下添加:
   ```
   manifestPlaceholders = [
   KeplerScheme  : "xxxxxx" //京东联盟SDK中AndroidManifest.xml 中的值
   ]
   ```
3. 配置混淆`-keep class com.kepler.jd.**{ public <fields>; public <methods>; public *; }`

## Usage
```javascript
import RNReactNativeMjdunion from 'react-native-mjdunion';

// TODO: What to do with the module?
RNReactNativeMjdunion;
```
