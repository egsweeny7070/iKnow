package ai.exemplar.analytics;

import ai.exemplar.analytics.providers.payments.BasicPaymentsDiagramProvider;
import ai.exemplar.analytics.providers.tracks.histogram.MainFeaturesBaselineHistogramProvider;
import ai.exemplar.analytics.providers.tracks.spider.FeaturesSpiderDiagramProvider;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public class AnalyticsModule {

    @Provides
    @IntoMap
    @StringKey(BasicPaymentsDiagramProvider.PROVIDER_NAME)
    static AnalyticsProvider provideBasicPaymentsDiagram(
            BasicPaymentsDiagramProvider provider
    ) {
        return provider;
    }

    @Provides
    @IntoMap
    @StringKey(MainFeaturesBaselineHistogramProvider.PROVIDER_NAME)
    static AnalyticsProvider provideMainFeaturesBaselineHistogram(
            MainFeaturesBaselineHistogramProvider provider
    ) {
        return provider;
    }

    @Provides
    @IntoMap
    @StringKey(FeaturesSpiderDiagramProvider.PROVIDER_NAME)
    static AnalyticsProvider provideHoursSpiderDiagram(
            FeaturesSpiderDiagramProvider provider
    ) {
        return provider;
    }
}
