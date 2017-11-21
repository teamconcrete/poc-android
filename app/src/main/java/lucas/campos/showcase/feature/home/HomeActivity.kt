package lucas.campos.showcase.feature.home

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_home.*
import lucas.campos.showcase.R
import lucas.campos.showcase.data.model.Product

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val products = listOf(
                Product(
                        "Camera Digital Sony Cyber-Shot DSC-HX400V",
                        "Câmeras Compactas",
                        1999.90
                ),
                Product(
                        "Kit Telescópio e Microscópio Vivitar VIVTELMIC20 - Preto/Prata",
                        "Binóculos e Telescópios",
                        169.90
                ),
                Product(
                        "Kit Telescópio e Microscópio Vivitar VIVTELMIC20 - Preto/Prata",
                        "Binóculos e Telescópios",
                        169.90
                ),
                Product(
                        "Camera Digital Sony Cyber-Shot DSC-HX400V",
                        "Câmeras Compactas",
                        1999.90
                ),
                Product(
                        "Camera Digital Sony Cyber-Shot DSC-HX400V",
                        "Câmeras Compactas",
                        1999.90
                ),
                Product(
                        "Kit Telescópio e Microscópio Vivitar VIVTELMIC20 - Preto/Prata",
                        "Binóculos e Telescópios",
                        169.90
                )
        )

        recycler_view.adapter = HomeAdapter(products)
        recycler_view.layoutManager = GridLayoutManager(this, 2)
    }

}
