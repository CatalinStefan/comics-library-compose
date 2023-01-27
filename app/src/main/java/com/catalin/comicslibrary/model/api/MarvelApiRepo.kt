package com.catalin.comicslibrary.model.api

import androidx.compose.runtime.mutableStateOf
import com.catalin.comicslibrary.model.CharacterResult
import com.catalin.comicslibrary.model.CharactersApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MarvelApiRepo(private val api: MarvelApi) {
    val characters = MutableStateFlow<NetworkResult<CharactersApiResponse>>(NetworkResult.Initial())
    val characterDetails = mutableStateOf<CharacterResult?>(null)

    fun query(query: String) {
        characters.value = NetworkResult.Loading()
        api.getCharacters(query)
            .enqueue(object : Callback<CharactersApiResponse> {
                override fun onResponse(
                    call: Call<CharactersApiResponse>,
                    response: Response<CharactersApiResponse>
                ) {
                    if (response.isSuccessful)
                        response.body()?.let {
                            characters.value = NetworkResult.Success(it)
                        }
                    else
                        characters.value = NetworkResult.Error(response.message())
                }

                override fun onFailure(call: Call<CharactersApiResponse>, t: Throwable) {
                    t.localizedMessage?.let {
                        characters.value = NetworkResult.Error(it)
                    }
                    t.printStackTrace()
                }

            })
    }

    fun getSingleCharacter(id: Int?) {
        id?.let {
            characterDetails.value =
                characters.value.data?.data?.results?.firstOrNull { character ->
                    character.id == id
                }
        }
    }
}