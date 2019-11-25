то есть каждый метод может быть вариацией:
1) что-то неизвестное, здесь это mHasSystemFeatureWrapper.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)
2) локальный рубильник, это mCallToBankFromInternetBuildConfigWrapper.isIpCallInChatWithOperatorEnabled()
3) статический рубильник не по нодам
здесь это mFeatureToggleFacade.getConfigWrapper().isParamPropertyEnabled(CALL_TO_BANK_FROM_INTERNET, IP_CALL_DEFAULT_ENABLED,
                false);
4) статический рубильник по нодам, здесь это метод  private boolean isNodePropertyEnabled
5) пермишн, здесь это mFeatureToggleFacade.isExtendedPermissionEribOn(CALL_TO_BANK_FROM_INTERNET_EXTENDED_PERMISSION)
