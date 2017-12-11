package lucas.campos.showcase

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.lazy
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.bind

/**
 * @author Lucas Campos
 */
class ShowcaseApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        bind<Person>() with provider { Person("Google")}
    }

}

class Person(val name: String)