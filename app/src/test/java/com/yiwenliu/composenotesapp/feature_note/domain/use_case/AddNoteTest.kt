package com.yiwenliu.composenotesapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.yiwenliu.composenotesapp.feature_note.data.repository.FakeNoteRepository
import com.yiwenliu.composenotesapp.feature_note.domain.model.InvalidNoteException
import com.yiwenliu.composenotesapp.feature_note.domain.model.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class AddNoteTest {

    private lateinit var fakeRepository: FakeNoteRepository
    private lateinit var addNote: AddNote

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        addNote = AddNote(fakeRepository)
    }

    @Test
    fun `add note with blank title returns error`() = runTest {
        val note = Note("", "1", 1L, 1, 1)
        val exception = try {
            addNote(note)
            null
        } catch (e: InvalidNoteException) {
            e
        }
        assertThat(exception).isInstanceOf(InvalidNoteException::class.java)
        assertThat(exception?.message).isEqualTo("The title of the note can't be empty.")
    }

    @Test
    fun `add note with blank content returns error`() = runTest {
        val note = Note("1", "", 1L, 1, 1)
        val exception = try {
            addNote(note)
            null
        } catch (e: InvalidNoteException) {
            e
        }
        assertThat(exception).isInstanceOf(InvalidNoteException::class.java)
        assertThat(exception?.message).isEqualTo("The content of the note can't be empty.")
    }

    @Test
    fun `add note success`() = runTest{
        val note = Note("1", "1", 1L, 1, 1)
        addNote(note)
        assertThat(fakeRepository.getNoteById(1)).isEqualTo(note)
    }
}