package lucas.campos.showcase.data

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory.createWithScheduler
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Lucas Campos
 */
class RetrofitConfig {

    companion object {
        fun createApi() =
                Retrofit.Builder()
                        .baseUrl("")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(createWithScheduler(Schedulers.io()))
                        .build()
                        .create(ShowcaseApi::class.java)
    }

}