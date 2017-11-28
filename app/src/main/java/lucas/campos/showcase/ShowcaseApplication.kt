package lucas.campos.showcase

import android.app.Application
import com.github.salomonbrys.kodein.*

/**
 * @author Lucas Campos
 */
class ShowcaseApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        bind<Person>() with provider { Person("Google")}
    }

}

class Person(val name: String)