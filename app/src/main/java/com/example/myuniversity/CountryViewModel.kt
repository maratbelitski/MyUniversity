package com.example.myuniversity

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myuniversity.api.ApiFactory
import com.example.myuniversity.pojo.ServerResponse
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @author Belitski Marat
 * @date  21.11.2023
 * @project MyUniversity
 */
class CountryViewModel(application: Application) : AndroidViewModel(application) {

    val loadUniversityLD: MutableLiveData<MutableList<String>> = MutableLiveData()
    val progressBarLD: MutableLiveData<Boolean> = MutableLiveData(true)
    private val titleSpinner =application.resources.getString(R.string.tittle_spinner)
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    fun loadCountry() {
        val disposable = ApiFactory.apiService.loadUniversity()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSubscribe { progressBarLD }
            ?.doAfterTerminate(Action{ progressBarLD.value=false })
            ?.subscribe({
                loadUniversityLD.value = filter(it)
            }, {
                Toast.makeText(getApplication(),R.string.error_download,Toast.LENGTH_SHORT).show() })

        disposable?.let { compositeDisposable.add(it) }
    }

    //фильтер неверных имен из API
    private fun filter(it: ServerResponse):MutableList<String>{
        val setCountry: MutableSet<String> = mutableSetOf()
        val listCountryNew: MutableList<String> = mutableListOf()

        for (country in it) {
            if (country.country?.length in 3..20)
                country.country?.let { it1 -> setCountry.add(it1) }
        }
        listCountryNew.addAll(setCountry)
        listCountryNew.sort()
        listCountryNew[0] = titleSpinner

        return listCountryNew
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}

