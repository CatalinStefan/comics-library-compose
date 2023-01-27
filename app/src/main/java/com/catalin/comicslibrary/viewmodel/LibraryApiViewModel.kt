package com.catalin.comicslibrary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.catalin.comicslibrary.model.api.MarvelApiRepo
import com.catalin.comicslibrary.model.connectivity.ConnectivityMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LibraryApiViewModel @Inject constructor(
    private val repo: MarvelApiRepo,
    connectivityMonitor: ConnectivityMonitor
): ViewModel() {

    val result = repo.characters
    val queryText = MutableStateFlow("")
    private val queryInput = Channel<String>(Channel.CONFLATED)
    val characterDetails = repo.characterDetails
    val networkAvailable = connectivityMonitor

    init {
        retrieveCharacters()
    }

    private fun retrieveCharacters() {
        viewModelScope.launch(Dispatchers.IO) {
            queryInput.receiveAsFlow()
                .filter { validateQuery(it) }
                .debounce(1000)
                .collect {
                    repo.query(it)
                }
        }
    }

    private fun validateQuery(query: String): Boolean = query.length >= 2

    fun onQueryUpdate(input: String) {
        queryText.value = input
        queryInput.trySend(input)
    }

    fun retrieveSingleCharacter(id: Int) {
        repo.getSingleCharacter(id)
    }
}




