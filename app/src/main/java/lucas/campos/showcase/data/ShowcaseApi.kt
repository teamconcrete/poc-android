package lucas.campos.showcase.data

import io.reactivex.Observable
import lucas.campos.showcase.data.model.Product
import retrofit2.http.GET

/**
 * @author Lucas Campos
 */
interface ShowcaseApi {

    @GET("")
    fun fetchProducts(): Observable<List<Product>>

}