package feature;

public class CallToBankFromInternetFeatureToggle implements ICallToBankFromInternetFeatureToggle {

    private static final String TAG = "CallToBankFromInternetFeatureToggle";

    //region Названия параметров

    private static final String CALL_TO_BANK_FROM_INTERNET = "VOIPCallOperator";
    private static final String CALL_TO_BANK_FROM_INTERNET_EXTENDED_PERMISSION = "IPcallToOperator";
    private static final String ENABLED_SIDE_BAR = "enabledSideBar";
    private static final String ENABLED_IN_CHAT = "enabledInChat";
    private static final String IP_CALL_DEFAULT_ENABLED = "IPCallDefaultEnabled";

    //endregion

    private final FeatureToggleFacade mFeatureToggleFacade;
    private final ICallToBankFromInternetBuildConfigWrapper mCallToBankFromInternetBuildConfigWrapper;
    private final IHasSystemFeatureWrapper mHasSystemFeatureWrapper;

    /**
     * Конструктор для создания  Feature Toggle для функционала IP -звонка
     *
     * @param featureToggleFacade     интерфейс для доступа к статическому конфигу и специфичным для пользователя
     *                                разрешениям
     * @param wrapper                 обертка над BuildConfig для функционала звонка в банк через интернет
     * @param hasSystemFeatureWrapper обертка над {@link PackageManager} для получения информации
     *                                о доступности hardware фичи на устройстве
     */
    public CallToBankFromInternetFeatureToggle(@NonNull FeatureToggleFacade featureToggleFacade,
                                               @NonNull ICallToBankFromInternetBuildConfigWrapper wrapper,
                                               @NonNull IHasSystemFeatureWrapper hasSystemFeatureWrapper) {
        mFeatureToggleFacade = checkNotNull(featureToggleFacade);
        mCallToBankFromInternetBuildConfigWrapper = checkNotNull(wrapper);
        mHasSystemFeatureWrapper = checkNotNull(hasSystemFeatureWrapper);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isIpCallInSideBarEnabled() {
        return mHasSystemFeatureWrapper.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)
            && isNodePropertyEnabled(ENABLED_SIDE_BAR)
            && mFeatureToggleFacade.isExtendedPermissionEribOn(CALL_TO_BANK_FROM_INTERNET_EXTENDED_PERMISSION);
        ;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isIpCallInChatWithOperatorEnabled() {
        return mHasSystemFeatureWrapper.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)
            && mCallToBankFromInternetBuildConfigWrapper.isIpCallInChatWithOperatorEnabled()
            && isNodePropertyEnabled(ENABLED_IN_CHAT);
    }

    @Override
    public boolean isIPCallDefaultEnabled() {
        return mFeatureToggleFacade.getConfigWrapper().isParamPropertyEnabled(CALL_TO_BANK_FROM_INTERNET, IP_CALL_DEFAULT_ENABLED,
            false);
    }

    /**
     * Проверить, включено ли свойство ноды
     */
    private boolean isNodePropertyEnabled(@NonNull String property) {
        return mFeatureToggleFacade.getConfigWrapper().isEnabledOnCurrentNode(
            CALL_TO_BANK_FROM_INTERNET,
            property,
            false);
    }
}
