package com.example.myuniversity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.myuniversity.database.UniversityDataBase
import com.example.myuniversity.pojo.University
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @author Belitski Marat
 * @date  24.11.2023
 * @project MyUniversity
 */
class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private val compositeDisposable: CompositeDisposable = CompositeDisposable()
    private val dao = UniversityDataBase.getDB(getApplication()).getDao()

    fun loadFavoriteUniversity(): LiveData<List<University>> {
        return dao.getFavoriteUniversity()
    }

    fun removeFavoriteUniversity(university: University) {
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