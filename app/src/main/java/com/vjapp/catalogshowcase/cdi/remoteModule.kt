import com.vjapp.catalogshowcase.cdi.EnvironmentConfig
import com.vjapp.catalogshowcase.cdi.EnvironmentConfig.BASE_DOMAIN
import com.vjapp.catalogshowcase.cdi.EnvironmentConfig.allowedSSlFingerprints
import com.vjapp.catalogshowcase.data.repository.datasource.RemoteDataSource
import com.vjapp.catalogshowcase.data.setup.AppServiceFactory
import com.vjapp.catalogshowcase.data.setup.HttpClientFactory
import com.vjapp.catalogshowcase.data.setup.ServiceFactory
import org.koin.core.qualifier.named
import org.koin.dsl.module

val remoteModule = module {
    single(named("HTTP_CLIENT")) { HttpClientFactory(BASE_DOMAIN, allowedSSlFingerprints) }
    single(named("SERVICE_FACTORY")) { ServiceFactory(EnvironmentConfig.BASE_URL) }

    //single { (get("SERVICE_FACTORY") as ServiceFactory).retrofitInstance }
    single(named("APP_SERVICE")) { AppServiceFactory(get(named("HTTP_CLIENT"))).getAppService(get(named("SERVICE_FACTORY"))) }
    single { RemoteDataSource(get(named("APP_SERVICE"))) as RemoteDataSource }

}