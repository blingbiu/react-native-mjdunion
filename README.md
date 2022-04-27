# react-native-mjdunion

京东联盟SDK

## 开始

`$ npm install react-native-mjdunion --save`

### 自动配置

`$ react-native link react-native-mjdunion`

### 手动配置


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
1. 将下载的京东联盟iOS SDK（JDSDK.bundle，内含安全图片）引入到自己的项目中（ps：JDSDK.framework已打包在模块里，无需引入到项目中）;
2. URL Schemes 添加 sdkback + 京东联盟appKey
3. LSApplicationQueriesSchemes 添加 jdlogin、openapp.jdmobile、openapp.jdpingou


#### Android
1. 替换模块中的安全图片`android/app/src/main/res/raw/safe.jpg`
2. 打开 android/app/build.gradle ，在 defaultConfig 下添加:
   ```
   manifestPlaceholders = [
      KeplerScheme  : "xxxxxx" //京东联盟SDK中AndroidManifest.xml 中的值
   ]
   ```
3. 配置混淆
  `-keep class com.kepler.jd.**{ public <fields>; public <methods>; public *; }`


## 使用方法
```javascript
import RNReactNativeMjdunion from 'react-native-mjdunion';

// 初始化
initSDK = async ()=>{
  alert(JSON.stringify(
    await RNReactNativeMjdunion.initSDK({
      appKey: '', //京东联盟账号下的appKey
      appSecret: '', //京东联盟账号下的appSecret
      appAndroidID: '', //android:用户同意隐私协议后获取到的androidid；ios无需该字段
      appOaid: '', //android:用户的oaid，如果没有oaid可以返回其他唯一标示；ios无需该字段
    }),
  ));
};

//唤醒京东app
showItemByUrl = async ()=>{
  let url = ''; // 可为商祥url/活动页url/券品二合一url
  alert(JSON.stringify(await RNReactNativeMjdunion.showItemByUrl({url: url})));
}

//唤醒京喜app
showJXItemByUrl = async ()=>{
  let url = ''; // 可为商祥url/活动页url/券品二合一url
  alert(JSON.stringify(await RNReactNativeMjdunion.showJXItemByUrl({url: url})));
}

```
