package lucas.campos.showcase.feature.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.adapter_home.view.*
import lucas.campos.showcase.R
import lucas.campos.showcase.data.model.Product

/**
 * @author Lucas Campos
 */
class HomeAdapter(private val products: List<Product>) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                    LayoutInflater
                            .from(parent.context)
                            .inflate(R.layout.adapter_home, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        with (holder) {
            name.text = product.name
            category.text = product.category
            price.text = product.price.toString()
        }
    }

    override fun getItemCount() = products.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.name
        val category = itemView.category
        val price = itemView.price
        val photo = itemView.photo
    }

}