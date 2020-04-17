package com.obrienrobert.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.main.R
import kotlinx.android.synthetic.main.resource_card_view.view.*
import org.jetbrains.anko.AnkoLogger

class CronJobAdapter(private val arrayOfCronjobs: List<String>) :
    RecyclerView.Adapter<CronJobAdapter.ViewHolder>(), Filterable {

    private var copyOfCronJobs: List<String> = arrayOfCronjobs.toList()

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(value: CharSequence?): FilterResults {
            val results = FilterResults()
            if (value.isNullOrEmpty()) {
                results.values = arrayOfCronjobs
            } else {
                copyOfCronJobs = arrayOfCronjobs.filter {
                    it.contains(value, true)
                }
                results.values = copyOfCronJobs
            }
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(value: CharSequence?, results: FilterResults?) {
            copyOfCronJobs = (results?.values as? List<String>).orEmpty()
            notifyDataSetChanged()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.resource_card_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return copyOfCronJobs.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(copyOfCronJobs[position], position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), AnkoLogger {

        fun bind(value: String, position: Int) {
            itemView.resource_name.text = value

            itemView.resource_icon.setImageResource(R.drawable.cronjob_icon)
        }
    }
}