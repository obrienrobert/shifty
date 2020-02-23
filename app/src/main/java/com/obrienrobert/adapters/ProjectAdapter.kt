package com.obrienrobert.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.main.R
import io.fabric8.openshift.api.model.Project

class ProjectAdapter(private val projectList: List<Project>) :
    RecyclerView.Adapter<ProjectAdapter.ViewHolder>(), Filterable {

    private var copyOfProjects: List<Project> = projectList.toList()

    override fun getFilter(): Filter =
        object : Filter() {
            override fun performFiltering(value: CharSequence?): FilterResults {
                val results = FilterResults()
                if (value.isNullOrEmpty()) {
                    results.values = projectList
                } else {
                    copyOfProjects = projectList.filter {
                        it.metadata.name.contains(value, true)
                    }
                    results.values = copyOfProjects
                }
                return results
            }

            override fun publishResults(value: CharSequence?, results: FilterResults?) {
                copyOfProjects = (results?.values as List<Project>)
                notifyDataSetChanged()
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view, parent, false) as CardView
        )

    }

    override fun getItemCount(): Int {
        return copyOfProjects.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(copyOfProjects[position].metadata.name, position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(value: String, position: Int) {
            this.itemView.findViewById<TextView>(R.id.info_text).text = value
            this.itemView.setOnClickListener {
                Log.e("CLICK", "Clicked item $value at $position")
            }
        }
    }
}