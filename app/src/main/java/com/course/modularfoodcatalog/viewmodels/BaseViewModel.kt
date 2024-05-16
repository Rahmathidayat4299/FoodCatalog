package com.course.modularfoodcatalog.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel

/**
 *hrahm,23/04/2024, 18:59
 **/
open class BaseViewModel(application: Application) : AndroidViewModel(application) {
    protected val context
        get() = getApplication<Application>()
}