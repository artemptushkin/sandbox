package feature;

public class EfsAppealsConfigWrapper {

    private static final String APPEALS_KEY = "CIMA_APPEALS_ENABLED";

    private final ITogglesListWrapper mTogglesListWrapper;

    public EfsAppealsConfigWrapper(@NonNull ITogglesListWrapper togglesListWrapper) {
        mTogglesListWrapper = checkNotNull(togglesListWrapper);
    }

    /**
     * Включен ли фунционал "Мои обращения"
     *
     * @return {@code true}, если включен, иначе {@code false}
     */
    public boolean isAppealsEnabled() {
        return mTogglesListWrapper.getValue(APPEALS_KEY, BuildConfig.CIMA_APPEALS_ENABLED);
    }
}
