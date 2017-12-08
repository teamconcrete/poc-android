package lucas.campos.showcase.feature.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.adapter_home.view.*
import lucas.campos.showcase.R
import lucas.campos.showcase.data.model.Product

/**
 * @author Lucas Campos
 */
class HomeAdapter(private val products: List<Product>, private val clickListener: () -> Unit) : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(
                    LayoutInflater
                            .from(parent.context)
                            .inflate(R.layout.adapter_home, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(products[position], clickListener)
    }

    override fun getItemCount() = products.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(product: Product, clickListener: () -> Unit) {
            itemView.apply {
                name.text = product.name
                description.text = product.description
                Glide.with(context).load(product.imageUrl).into(photo)
                buy.setOnClickListener { clickListener() }
            }
        }

    }

}