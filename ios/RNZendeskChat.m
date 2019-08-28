
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
        [ZDCChat startChat:^(ZDCConfig *config) {

            if ([configDict valueForKey:@"preChatForm"] != nil) {
                NSDictionary *form = [configDict valueForKey:@"preChatForm"];
                config.preChatDataRequirements.name = [self mapStringToZDCPreChatOption:[form valueForKey:@"name"]];
                config.preChatDataRequirements.email = [self mapStringToZDCPreChatOption:[form valueForKey:@"email"]];
                config.preChatDataRequirements.phone = [self mapStringToZDCPreChatOption:[form valueForKey:@"phone"]];
                config.preChatDataRequirements.department = [self mapStringToZDCPreChatOption:[form valueForKey:@"department"]];
                config.preChatDataRequirements.message = [self mapStringToZDCPreChatOption:[form valueForKey:@"message"]];
            }

        }];
    });
}

- (NSUInteger) mapStringToZDCPreChatOption:(NSString*) str {

    if (str == nil) {
        return ZDCPreChatDataNotRequired;
    }

    if ([str isEqualToString:@"required"]) {
        return ZDCPreChatDataRequiredEditable;
    }
    if ([str isEqualToString:@"optional"]) {
        return ZDCPreChatDataOptionalEditable;
    }

    @throw [NSException
        exceptionWithName:@"ZDCPreChatOptionInvalidException"
        reason:@"Got unknown prechat form option"
        userInfo:nil];
}


@end

