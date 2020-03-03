package com.obrienrobert.models

import com.soywiz.klock.DateTime

data class ClusterModel(
    var masterURL: String? = "",
    var userName: String? = "",
    var password: String? = "",
    var isActiveCluster: Boolean = false,
    var dataAdded: DateTime? = DateTime.now()
)