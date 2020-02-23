package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.CronJobAdapter
import com.obrienrobert.main.R

class CronJobFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.cronjob_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayList=ArrayList<String>()
        arrayList.add("Clusters")
        arrayList.add("Pods")
        arrayList.add("Nodes")
        arrayList.add("Storage")

        val viewManager:  RecyclerView.LayoutManager = LinearLayoutManager(context)
        val viewAdapter: RecyclerView.Adapter<*> =  CronJobAdapter(arrayList)

        view.findViewById<RecyclerView>(R.id.cronjob_recycler_view).apply {

            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    companion object {
        fun newInstance(): CronJobFragment {
            return CronJobFragment()
        }
    }
}