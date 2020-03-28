package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.ServiceAdapter
import com.obrienrobert.client.ActiveClient
import com.obrienrobert.client.Requests
import com.obrienrobert.main.R
import com.obrienrobert.models.ClusterStore
import io.fabric8.kubernetes.api.model.Service
import me.nikhilchaudhari.asynkio.core.async


class ServiceFragment : BaseFragment() {

    override fun layoutId() = R.layout.service_fragment

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var serviceList: List<Service>
    private lateinit var noData: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.service_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        async {
            serviceList = await {
                Requests(ActiveClient.newInstance()).getAllServicesInNamespace(ClusterStore.getActiveCluster()?.activeNamespace!!
                )
            }

            val recyclerView = view.findViewById<RecyclerView>(R.id.service_recycler_view)
            noData = view.findViewById(R.id.no_data) as TextView

            if (serviceList.isNullOrEmpty()) {
                recyclerView.visibility = View.GONE
                noData.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                noData.visibility = View.GONE
            }

            viewManager = LinearLayoutManager(context)
            viewAdapter = ServiceAdapter(serviceList)

            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
    }

    companion object {
        fun newInstance(): ServiceFragment {
            return ServiceFragment()
        }
    }
}