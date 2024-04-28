package com.yiwenliu.composenotesapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.yiwenliu.composenotesapp.feature_note.data.repository.FakeNoteRepository
import com.yiwenliu.composenotesapp.feature_note.domain.model.Note
import com.yiwenliu.composenotesapp.feature_note.domain.util.NoteOrder
import com.yiwenliu.composenotesapp.feature_note.domain.util.OrderType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class DeleteNoteTest {

    private lateinit var getNotes: GetNotes
    private lateinit var fakeRepository: FakeNoteRepository
    private lateinit var deleteNote: DeleteNote
    private val note = Note("1", "1", 1L, 1, 1)

    @Before
    fun setUp() {
        fakeRepository = FakeNoteRepository()
        getNotes = GetNotes(fakeRepository)
        deleteNote = DeleteNote(fakeRepository)
        runTest {
            fakeRepository.insertNote(note)
        }
    }

    @Test
    fun deleteNote() = runTest{
        deleteNote(note)
        val notes = getNotes(NoteOrder.Title(OrderType.Ascending)).first()
        assertThat(notes).doesNotContain(note)
    }
}