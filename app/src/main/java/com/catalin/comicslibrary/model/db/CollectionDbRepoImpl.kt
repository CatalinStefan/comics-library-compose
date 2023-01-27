package com.catalin.comicslibrary.model.db

import kotlinx.coroutines.flow.Flow

class CollectionDbRepoImpl(private val characterDao: CharacterDao, private val noteDao: NoteDao) :
    CollectionDbRepo {
    override suspend fun getCharactersFromRepo(): Flow<List<DbCharacter>> =
        characterDao.getCharacters()

    override suspend fun getCharacterFromRepo(characterId: Int): Flow<DbCharacter> =
        characterDao.getCharacter(characterId)

    override suspend fun addCharacterToRepo(character: DbCharacter) =
        characterDao.addCharacter(character)

    override suspend fun updateCharacterInRepo(character: DbCharacter) =
        characterDao.updateCharacter(character)

    override suspend fun deleteCharacterFromRepo(character: DbCharacter) =
        characterDao.deleteCharacter(character)


    override suspend fun getAllNotes() = noteDao.getAllNotes()

    override suspend fun getNotesFromRepo(characterId: Int) = noteDao.getNotes(characterId)

    override suspend fun addNoteToRepo(note: DbNote) = noteDao.addNote(note)

    override suspend fun updateNoteInRepo(note: DbNote) = noteDao.updateNote(note)

    override suspend fun deleteNoteFromRepo(note: DbNote) = noteDao.deleteNote(note)

    override suspend fun deleteAllNotes(character: DbCharacter) =
        noteDao.deleteAllNotes(character.id)
}