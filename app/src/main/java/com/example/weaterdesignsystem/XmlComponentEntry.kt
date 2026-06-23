package com.example.weaterdesignsystem

import android.widget.LinearLayout

data class XmlComponentEntry(
    val name: String,
    val category: String,
    val description: String,
    val buildPreview: (LinearLayout) -> Unit,
)
