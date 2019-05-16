
#if __has_include(<React/RCTBridge.h>)
#import <React/RCTConvert.h>
#else
#import "RCTConvert.h"
#endif

#import "RNZendeskChat.h"
#import <ZDCChat/ZDCChat.h>

@implementation RNZendeskChat

RCT_EXPORT_MODULE();

RCT_EXPORT_METHOD(initialize:(NSString *)accountKey){
    dispatch_async(dispatch_get_main_queue(), ^{
        [ZDCChat initializeWithAccountKey:accountKey];
    });
}

RCT_EXPORT_METHOD(startChat:(NSDictionary *)configDict){
    dispatch_async(dispatch_get_main_queue(), ^{
        // Ignore configDict at this moment
        [ZDCChat startChat:nil];
    });
}
@end

