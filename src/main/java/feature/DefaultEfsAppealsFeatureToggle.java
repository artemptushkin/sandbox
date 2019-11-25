package feature;

public class DefaultEfsAppealsFeatureToggle implements IEfsAppealsFeatureToggle {

    private static final String TAG = "DefaultEfsAppealsFeatureToggle";
    private static final String MODULE_NAME = "Appeals";


    private static final String APPEALS_FEATURE_NAME = "showAppeals";
    private static final String APPEALS_FULL_RESPONSE_FEATURE_NAME = "appealsShowFullResponse";
    private static final String APPEALS_ACTIVE_ACTIONS_FEATURE_NAME = "appealsShowActiveActions";
    private static final String APPEALS_RESPONSE_DOWNLOADING_FEATURE_NAME = "appealsFilesDownloading";

    private static final String SUPPORTED_VERSION = "1.0";

    private final FeatureToggleFacade mFeatureToggleFacade;
    private final EfsAppealsConfigWrapper mConfigWrapper;

    /**
     * @param featureToggleFacade интерфейс для доступа к Launcher
     * @param configWrapper       обертка над BuildConfig
     */
    public DefaultEfsAppealsFeatureToggle(@NonNull FeatureToggleFacade featureToggleFacade,
                                          @NonNull EfsAppealsConfigWrapper configWrapper) {
        mFeatureToggleFacade = checkNotNull(featureToggleFacade);
        mConfigWrapper = checkNotNull(configWrapper);
    }

    @Override
    public boolean isAppealsEnabled() {
        boolean isLocalEnabled = mConfigWrapper.isAppealsEnabled();
        boolean isLauncherEnabled = isFeatureInLauncherEnabled(APPEALS_FEATURE_NAME);
        Logger.d(TAG, String.format("isAppealsEnabled : %b, isLauncherEnabled : %b",
            isLocalEnabled, isLauncherEnabled));
        return isLocalEnabled && isLauncherEnabled;
    }

    @Override
    public boolean isFullResponseEnabled() {
        boolean isLocalEnabled = mConfigWrapper.isFullResponseEnabled();
        boolean isLauncherEnabled = isFeatureInLauncherEnabled(APPEALS_FULL_RESPONSE_FEATURE_NAME);

        return isLocalEnabled && isLauncherEnabled;
    }

    @Override
    public boolean isActiveActionsEnabled() {
        boolean isLocalEnabled = mConfigWrapper.isActiveActionsEnabled();
        boolean isLauncherEnabled = isFeatureInLauncherEnabled(APPEALS_ACTIVE_ACTIONS_FEATURE_NAME);

        return isLocalEnabled && isLauncherEnabled;
    }

    @Override
    public boolean isResponseDownloadingEnabled() {
        boolean isLocalEnabled = mConfigWrapper.isResponseDownloadingEnabled();
        boolean isLauncherEnabled = isFeatureInLauncherEnabled(APPEALS_RESPONSE_DOWNLOADING_FEATURE_NAME);

        return isLocalEnabled && isLauncherEnabled;
    }

    private boolean isFeatureInLauncherEnabled(String featureName) {
        return SUPPORTED_VERSION.equals(
            mFeatureToggleFacade.getLauncherData().getModuleFeatureValue(MODULE_NAME, featureName));
    }
}
