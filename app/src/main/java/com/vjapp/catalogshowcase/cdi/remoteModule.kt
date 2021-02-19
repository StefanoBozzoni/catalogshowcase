import com.vjapp.catalogshowcase.cdi.EnvironmentConfig
import com.vjapp.catalogshowcase.cdi.EnvironmentConfig.BASE_DOMAIN
import com.vjapp.catalogshowcase.cdi.EnvironmentConfig.allowedSSlFingerprints
import com.vjapp.catalogshowcase.data.repository.datasource.RemoteDataSource
import com.vjapp.catalogshowcase.data.setup.AppServiceFactory
import com.vjapp.catalogshowcase.data.setup.HttpClientFactory
import com.vjapp.catalogshowcase.data.setup.ServiceFactory
import org.koin.dsl.module.module

val remoteModule = module {
    single("HTTP_CLIENT") { HttpClientFactory(BASE_DOMAIN, allowedSSlFingerprints) }
    single("SERVICE_FACTORY") { ServiceFactory(EnvironmentConfig.BASE_URL) }

    //single { (get("SERVICE_FACTORY") as ServiceFactory).retrofitInstance }
    single("APP_SERVICE") { AppServiceFactory(get("HTTP_CLIENT")).getAppService(get("SERVICE_FACTORY")) }
    single { RemoteDataSource(get("APP_SERVICE")) as RemoteDataSource }

}