package com.sena.caloribook.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sena.caloribook.model.Besin
import com.sena.caloribook.service.BesinApiService
import com.sena.caloribook.service.BesinDatabase
import com.sena.caloribook.util.OzelSharedPreferences
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application) : BaseViewModel(application) {

    val besinler = MutableLiveData<List<Besin>>()
    val hataMesaji = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()
    private var guncellemeZamani = 10 * 60 * 1000 * 1000 * 1000L //10 dakka nano cinsinden
    private val besinApiService = BesinApiService()
    private val disposable = CompositeDisposable()

    private val ozelSharedPreferences = OzelSharedPreferences(getApplication())

    fun refreshData() {

        val kaydedilmeZamani = ozelSharedPreferences.getTime()
        if (kaydedilmeZamani != null && kaydedilmeZamani != 0L && (System.nanoTime() - kaydedilmeZamani) < guncellemeZamani) {
            //sql den al
            verileriSqldenCek()
        } else {
            //internetten al
            verileriCek()
        }
    }

    fun refreshFromInternet() {
        verileriCek()
    }
    private fun verileriSqldenCek() {
        besinYukleniyor.value = true
        launch {
            val besinListesi = BesinDatabase(context = getApplication()).besinDao().getAllBesin()
            besinleriGoster(besinListesi)
            Toast.makeText(getApplication(), "besinleri roomdan aldık", Toast.LENGTH_LONG).show()
        }
    }

    private fun verileriCek() {
        besinYukleniyor.value = true

        disposable.add(
            besinApiService.getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Besin>>() {
                    override fun onSuccess(t: List<Besin>) {
                        besinleriGoster(t)
                        sqlLiteSakla(t)
                        Toast.makeText(getApplication(), "besinleri internetten aldık", Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        besinYukleniyor.value = false
                        hataMesaji.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun besinleriGoster(t: List<Besin>) {
        besinler.value = t
        besinYukleniyor.value = false
        hataMesaji.value = false
    }

    private fun sqlLiteSakla(t: List<Besin>) {
        //yeni bir scope olustu. farklı threadde işlemi yapar. diğer işlemleri duraksatmaz
        launch {
            val dao = BesinDatabase(getApplication()).besinDao()
            dao.deleteAll()
            val idListesi = dao.insertAll(*t.toTypedArray())
            var i = 0
            while (i < t.size) {
                t[i].uuid = idListesi[i].toInt()
                i++
            }
            besinleriGoster(t)
        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())
    }
}