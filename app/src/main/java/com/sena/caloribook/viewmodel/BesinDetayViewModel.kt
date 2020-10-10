package com.sena.caloribook.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.sena.caloribook.model.Besin
import com.sena.caloribook.service.BesinDatabase
import kotlinx.coroutines.launch

class BesinDetayViewModel(application: Application) : BaseViewModel(application) {

    val besinLiveData = MutableLiveData<Besin>()

    fun roomVerisiniAl(besinid: Int) {
        launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            val besin = dao.getBesin(besinid)
            besinLiveData.value = besin
        }
    }
}