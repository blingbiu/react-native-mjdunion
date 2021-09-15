
#import "RNReactNativeMjdunion.h"
#import "UIColor+Hex.h"
#import <JDSDK/JDKeplerSDK.h>

@interface RNReactNativeMjdunion () {
    NSInteger initKepler_success;
}
@end

@implementation RNReactNativeMjdunion {
    bool hasListeners;
}


- (dispatch_queue_t)methodQueue
{
    return dispatch_get_main_queue();
}

+ (BOOL) requiresMainQueueSetup {
    return YES;
}

RCT_EXPORT_MODULE()

// Will be called when this module's first listener is added.
-(void)startObserving {
    hasListeners = YES;
    // Set up any upstream listeners or background tasks as necessary
}

// Will be called when this module's last listener is removed, or on dealloc.
-(void)stopObserving {
    hasListeners = NO;
    // Remove upstream listeners, stop unnecessary background tasks
}

///////
/**
 *  处理公用参数
 *  isOpenByH5, processColor, backTagID, openType, customParams
 */
- (NSDictionary *)dealParam:(NSDictionary *)param {
    NSInteger openType = 2;
    NSDictionary *customParams = (NSDictionary *)param[@"customParams"];
    if([(NSString *)param[@"openType"] isEqual:@"present"]){
        openType = 1;
    }else{
        openType = 2;
    }

    NSString *actId = (NSString *)param[@"actId"];
    NSString *ext = (NSString *)param[@"ext"];

    if(actId != nil && ![actId isEqual:@""]){
        [KeplerApiManager sharedKPService].actId = actId;
    }

    if(ext != nil && ![ext isEqual:@""]){
        [KeplerApiManager sharedKPService].ext = ext;
    }

    //返回处理后的参数
    return @{
        @"openType":[NSNumber numberWithInteger:openType],
        @"customParams":customParams
    };
}
///////

//初始化SDK
RCT_EXPORT_METHOD(initSDK: (NSDictionary *)param resolve: (RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    NSString *AppKey = @"";
    NSString *AppSecret = @"";
    if ((NSString *)param[@"appKey"] != nil) {
        AppKey=(NSString *)param[@"appKey"];
    }
    if ((NSString *)param[@"appSecret"] != nil) {
        AppSecret=(NSString *)param[@"appSecret"];
    }
    if(![AppKey isEqual: @""] && ![AppSecret isEqual: @""]){
        [[KeplerApiManager sharedKPService]asyncInitSdk:AppKey secretKey:AppSecret sucessCallback:^(){
            NSDictionary *ret = @{@"code": @"0", @"message":@"success"};
            self->initKepler_success = 1;
            resolve(ret);
        }failedCallback:^(NSError *error){
            NSDictionary *ret = @{@"code": @(error.code), @"message":error.description};
            resolve(ret);
        }];
    }else{
        NSDictionary *ret = @{@"code": @"1", @"message":@"AppKey或AppSecret为空"};
        resolve(ret);
    }
}

//通过url打开商品
RCT_EXPORT_METHOD(showItemByUrl: (NSDictionary *)param resolve: (RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    if(initKepler_success != 1){
        NSDictionary *ret = @{@"code": @"0", @"message":@"未初始化SDK"};
        resolve(ret);
        return;
    }
    NSDictionary* result = [self dealParam: param];
    NSDictionary *customParams = result[@"customParams"];
    NSString *url = (NSString *)param[@"url"];

    [[KeplerApiManager sharedKPService] openKeplerPageWithURL:url userInfo:customParams successCallback:^{

    } failedCallback:^(NSInteger code, NSString * _Nonnull url) {
        NSDictionary *ret = @{@"code": @(code), @"message":url};
        resolve(ret);
    }];
}

//通过url打开商品
RCT_EXPORT_METHOD(showJXItemByUrl: (NSDictionary *)param resolve: (RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject) {
    if(initKepler_success != 1){
        NSDictionary *ret = @{@"code": @"0", @"message":@"未初始化SDK"};
        resolve(ret);
        return;
    }
    NSDictionary* result = [self dealParam: param];
    NSDictionary *customParams = result[@"customParams"];
    NSString *url = (NSString *)param[@"url"];

    [[KeplerApiManager sharedKPService] openJXPageWithUrl:url userInfo:customParams success:^{

    } failure:^(NSInteger code, NSString * _Nonnull url) {
        NSDictionary *ret = @{@"code": @(code), @"message":url};
        resolve(ret);
    }];
}
///////

@end
