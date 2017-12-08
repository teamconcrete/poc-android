package lucas.campos.showcase.feature.home

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import lucas.campos.showcase.data.model.Product

/**
 * @author Lucas Campos
 */
class HomeViewModel : ViewModel() {

    private val disposable = CompositeDisposable()
    var behavior = MutableLiveData<HomeBehavior>()
    //expor somente o LiveData e não o MutableLiveData

    fun fetchData() {
        disposable.add(
                fetchProducts() // fake api
                        .observeOn(Schedulers.io())
                        .doOnSubscribe({ behavior.postValue(HomeShowLoading()) })
                        .doOnError({ behavior.postValue(HomeHideLoading()) })
                        .doOnComplete({ behavior.postValue(HomeHideLoading()) })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            behavior.value = HomeListData(it)
                        }, {
                            behavior.value = HomeError()
                        }))
    }

}

sealed class HomeBehavior

class HomeShowLoading : HomeBehavior()
class HomeHideLoading : HomeBehavior()
class HomeError : HomeBehavior()
class HomeListData(val products: List<Product>) : HomeBehavior()

fun fetchProducts() =
        Observable.just(
                listOf(
                        Product(
                                "Câmeras Compactas",
                                "Camera Digital Sony Cyber-Shot DSC-HX400V",
                                "https://carrinho.casasbahia.com.br/CineFoto/CamerasDigitais/camerasdigitaissemiprofissionais/11795445/862759530/camera-canon-dslr-eos-rebel-t6-com-lente-18-55mm-11795445.jpg"
                        ),
                        Product(
                                "Binóculos e Telescópios",
                                "Kit Telescópio e Microscópio Vivitar VIVTELMIC20 - Preto/Prata",
                                "https://carrinho.casasbahia.com.br/CineFoto/BinoculoseTelescopios/Telescopios/3586691/58850892/Kit-Telescopio-e-Microscopio-Vivitar-VIVTELMIC20-Preto-Prata-3586691.jpg"
                        )
                )
        )!!
