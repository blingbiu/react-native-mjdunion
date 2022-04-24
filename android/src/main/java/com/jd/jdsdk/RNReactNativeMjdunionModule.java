
package com.jd.jdsdk;

import android.app.Application;

import com.facebook.react.bridge.*;

import com.kepler.jd.Listener.AsyncInitListener;
import com.kepler.jd.Listener.IOaidCallBck;
import com.kepler.jd.Listener.OpenAppAction;
import com.kepler.jd.login.KeplerApiManager;
import com.kepler.jd.sdk.bean.KelperTask;
import com.kepler.jd.sdk.bean.KeplerAttachParameter;

public class RNReactNativeMjdunionModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private final KeplerAttachParameter mKeplerAttachParameter = new KeplerAttachParameter();// 这个是即时性参数

    KelperTask mKelperTask;
    int initKepler_success = 0;
    OpenAppAction mOpenAppAction = new OpenAppAction() {
        @Override
        public void onStatus(int i, String s) {

        }
    };


    public RNReactNativeMjdunionModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNReactNativeMjdunion";
    }


    @ReactMethod // 初始化SDK ----传入AppKey与AppSecret，获取config.xml下的配置
    public void initSDK(final ReadableMap data, final Promise p) {
        if (initKepler_success == 1) {
            // 初始化成功
            WritableMap map = Arguments.createMap();
            map.putString("message", "success");
            map.putString("code", Integer.toString(0));
            p.resolve(map);
            return;
        }
        String appKey = data.getString("appKey");
        String appSecret = data.getString("appSecret");
        String appAndroidID = data.getString("appAndroidID");
        final String appOaid = data.getString("appOaid");

        if (!appKey.equals("") && !appSecret.equals("")) {
            Application app = (Application) reactContext.getApplicationContext();
            KeplerApiManager.asyncInitSdk(app,
                    appKey, appSecret, appAndroidID, new IOaidCallBck() {    // 用户的oaid，如果没有oaid可以返回其他唯一标示
                        @Override
                        public String getOaid() {
                            return appOaid;
                        }
                    }, new AsyncInitListener() {
                        public void onSuccess() {
                            // 初始化成功
                            initKepler_success = 1;
                            WritableMap map = Arguments.createMap();
                            map.putString("message", "success");
                            map.putString("code", Integer.toString(0));
                            p.resolve(map);
                        }


                        public void onFailure() {
                            WritableMap map = Arguments.createMap();
                            map.putString("message", "Kepler asyncInitSdk 授权失败，请检查lib 工程资源引用；包名,签名证书是否和注册一致");
                            map.putString("code", Integer.toString(1));
                            p.resolve(map);
                        }
                    });
        } else {
            WritableMap map = Arguments.createMap();
            map.putString("message", "AppKey或AppSecret为空");
            map.putString("code", Integer.toString(1));
            p.resolve(map);
        }
    }

    /**
     * 通过sku唤起京喜APP或京东APP单品详情页
     * ----传入itemID，isOpenByH5，processColor，backTagID，openType，customParams
     */
    @ReactMethod
    public void showJXItemByUrl(final ReadableMap data, final Promise p) {
        if (initKepler_success != 1) {
            WritableMap map = Arguments.createMap();
            map.putString("message", "未初始化SDK");
            map.putString("code", Integer.toString(0));
            p.resolve(map);
            return;
        }
        String url = data.getString("url");

        mKelperTask = KeplerApiManager.getWebViewService()
            .openAppWebViewPageJX(reactContext, url, mKeplerAttachParameter,
                new OpenAppAction() {
                    @Override
                    public void onStatus(final int status, final String url) {
                        WritableMap map = Arguments.createMap();
                        map.putString("code", Integer.toString(status));
                        if (status == OpenAppAction.OpenAppAction_result_NoJDAPP) {
                            map.putString("message", url);
                        } else if (status == OpenAppAction.OpenAppAction_result_BlackUrl) {
                            map.putString("message", url);
                        } else if (status == OpenAppAction.OpenAppAction_result_ErrorScheme) {
                            map.putString("message", url);
                        } else if (status == OpenAppAction.OpenAppAction_result_APP) {
                            //成功唤醒app
                        } else if (status == OpenAppAction.OpenAppAction_result_uawakeId_empty) {
                            map.putString("message", "缺少依赖文件，请检查libs文件夹中文件完整性，重新进行集成");
                        } else if (status == OpenAppAction.OpenAppAction_result_NetError) {
                            map.putString("message", "网络异常，请检查网络连接是否开启");
                        }
                        p.resolve(map);
                    };
                });

    }

    /**
     * 通过url打开连接
     * ----传入url，isOpenByH5，processColor，backTagID，openType，customParams
     */
    @ReactMethod
    public void showItemByUrl(final ReadableMap data, final Promise p) {
        if (initKepler_success != 1) {
            WritableMap map = Arguments.createMap();
            map.putString("message", "未初始化SDK");
            map.putString("code", Integer.toString(0));
            p.resolve(map);
            return;
        }
        String url = data.getString("url");
        mKelperTask = KeplerApiManager.getWebViewService()
            .openAppWebViewPage(reactContext, url, mKeplerAttachParameter,
                new OpenAppAction() {
                    @Override
                    public void onStatus(final int status, final String url) {
                        WritableMap map = Arguments.createMap();
                        map.putString("code", Integer.toString(status));
                        if (status == OpenAppAction.OpenAppAction_result_NoJDAPP) {
                            map.putString("message", url);
                        } else if (status == OpenAppAction.OpenAppAction_result_BlackUrl) {
                            map.putString("message", url);
                        } else if (status == OpenAppAction.OpenAppAction_result_ErrorScheme) {
                            map.putString("message", url);
                        } else if (status == OpenAppAction.OpenAppAction_result_APP) {
                            //成功唤醒app
                        } else if (status == OpenAppAction.OpenAppAction_result_uawakeId_empty) {
                            map.putString("message", "缺少依赖文件，请检查libs文件夹中文件完整性，重新进行集成");
                        } else if (status == OpenAppAction.OpenAppAction_result_NetError) {
                            map.putString("message", "网络异常，请检查网络连接是否开启");
                        }
                        p.resolve(map);
                    };
                });
    }
}
