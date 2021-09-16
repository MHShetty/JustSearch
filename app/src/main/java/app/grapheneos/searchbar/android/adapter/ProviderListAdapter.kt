package app.grapheneos.searchbar.android.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater.from
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import app.grapheneos.searchbar.R
import app.grapheneos.searchbar.arch.model.SearchProvider

class ProviderListAdapter(private val providers: List<SearchProvider>) : BaseAdapter() {
    override fun getItem(position: Int) = providers[position]

    override fun getItemId(position: Int) = getItem(position).id()

    override fun getCount(): Int = providers.count()

    @SuppressLint("ViewHolder") // Not expected to scroll
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item = getItem(position)

        val nameView = from(parent.context)
            .inflate(R.layout.item_provider_list, parent, false) as TextView
        nameView.setCompoundDrawablesRelativeWithIntrinsicBounds(item.iconRes(), 0, 0, 0)
        nameView.text = item.name()

        return nameView
    }
}