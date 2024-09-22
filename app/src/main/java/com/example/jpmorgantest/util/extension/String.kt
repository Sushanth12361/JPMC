package com.example.jpmorgantest.util.extension

import com.example.jpmorgantest.util.constants.DEFAULT_FORMAT
import com.example.jpmorgantest.util.constants.FORMAT_IMAGE

fun String?.formatImageUrl() = FORMAT_IMAGE.format(
    this ?: DEFAULT_FORMAT
)
