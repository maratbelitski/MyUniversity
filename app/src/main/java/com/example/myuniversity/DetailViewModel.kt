package com.example.myuniversity

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myuniversity.api.ApiFactory
import com.example.myuniversity.database.UniversityDataBase
import com.example.myuniversity.pojo.University
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @author Belitski Marat
 * @date  20.11.2023
 * @project MyUniversity
 */
class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    val loadCountryUniversityLD: MutableLiveData<MutableList<University>> = MutableLiveData()
    val progressBarLD: MutableLiveData<Boolean> = MutableLiveData(true)
    private val dao = UniversityDataBase.getDB(getApplication()).getDao()

    fun loadFavoriteUniversity(): LiveData<List<University>> {
        return dao.getFavoriteUniversity()
    }

    fun loadCountryUniversity(country: String) {

        val disposable = ApiFactory.apiService.loadCountryUniversity(country)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.doOnSubscribe { progressBarLD }
            ?.doAfterTerminate { progressBarLD.value = false }
            ?.subscribe({

                loadCountryUniversityLD.value = it

            }, {Toast.makeText(getApplication(),R.string.error_download, Toast.LENGTH_SHORT).show() })

        if (disposable != null) {
            compositeDisposable.add(disposable)
        }
    }


    fun insertUniversityDB(university: University) {
        val disposable = dao.insertUniversity(university)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ()
        compositeDisposable.add(disposable)
    }

    fun removeFavoriteUniversity(university: University){
        val disposable = dao.removeUniversity(university.name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        compositeDisposable.add(disposable)
    }

        override fun onCleared() {
            super.onCleared()
            compositeDisposable.clear()
        }
    }