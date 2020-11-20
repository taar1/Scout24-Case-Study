package ch.scout24.casestudy

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException

class MainViewModel constructor(app: Application) : AndroidViewModel(app) {

    private val TAG = "MainViewModel"

    private val repository: Repository = Repository()

    private val _repoList = MutableLiveData<List<RepoDataModelItem>>()

    val repoList: LiveData<List<RepoDataModelItem>>
        get() = _repoList

    private var _eventNetworkError = MutableLiveData(false)

    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError


    private var _isNetworkErrorShown = MutableLiveData(false)

    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    init {
        getRepositoryListFromApi()
    }

    fun getRepositoryListFromApi() {
        viewModelScope.launch {
            try {
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

                repository.getRepoListFromApi()
                _repoList.postValue(repository.repoList)

                Log.d(TAG, "NETWORK OK")
            } catch (networkError: IOException) {
                Log.d(TAG, "IOException NO NETWORK")
                _repoList.postValue(null)
            }

            // If data is empty show a error message
            if (repository.repoList.isNullOrEmpty()) {
                Log.d(TAG, "Repo list is empty. Show a error message in the UI.")
                _eventNetworkError.value = true
            }
        }
    }

    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

}