package com.catalin.comicslibrary.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.catalin.comicslibrary.model.db.Constants.CHARACTER_TABLE
import kotlinx.coroutines.flow.Flow

@Dao
interface CharacterDao {

    @Query("SELECT * FROM $CHARACTER_TABLE ORDER BY id ASC")
    fun getCharacters(): Flow<List<DbCharacter>>

    @Query("SELECT * FROM $CHARACTER_TABLE WHERE id = :characterId")
    fun getCharacter(characterId: Int): Flow<DbCharacter>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCharacter(character: DbCharacter)

    @Update
    fun updateCharacter(character: DbCharacter)

    @Delete
    fun deleteCharacter(character: DbCharacter)

}