declare module "react-native-zendesk-chat" {
  type PrechatFormFieldOption = "required" | "optional";

  interface ChatConfig {
    preChatForm?: {
      name?: PrechatFormFieldOption;
      email?: PrechatFormFieldOption;
      phone?: PrechatFormFieldOption;
      department?: PrechatFormFieldOption;
      message?: PrechatFormFieldOption;
    };
  }

  export default class RNZendeskChat {
    static initialize(accountKey: string): void;
    static startChat(config: ChatConfig): void;
  }
}
