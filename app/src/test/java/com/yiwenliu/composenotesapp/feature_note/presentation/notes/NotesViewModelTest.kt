package com.yiwenliu.composenotesapp.feature_note.presentation.notes

import com.google.common.truth.Truth.assertThat
import com.yiwenliu.composenotesapp.MainCoroutineRule
import com.yiwenliu.composenotesapp.feature_note.domain.model.Note
import com.yiwenliu.composenotesapp.feature_note.domain.use_case.NoteUseCases
import com.yiwenliu.composenotesapp.feature_note.domain.util.NoteOrder
import com.yiwenliu.composenotesapp.feature_note.domain.util.OrderType
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.slot
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class NotesViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK(relaxed = true)
    private lateinit var noteUseCases: NoteUseCases
    private lateinit var notesViewModel: NotesViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        notesViewModel = NotesViewModel(noteUseCases)
    }

    @Test
    fun `Order notes by title descending`() {
        val noteList = listOf(
            Note("2", "2", 2L, 2),
            Note("1", "1", 1L, 1)
        )
        val noteListFlow = flowOf(noteList)
        val noteOrder = NoteOrder.Title(OrderType.Descending)
        coEvery { noteUseCases.getNotes(noteOrder) }.returns(noteListFlow)
        notesViewModel.onEvent(NotesEvent.Order(noteOrder))
        assertThat(notesViewModel.state.value.notes).isEqualTo(noteList)
        assertThat(notesViewModel.state.value.noteOrder.orderType).isEqualTo(OrderType.Descending)
    }

    @Test
    fun `Delete note`() {
        val note = Note("1", "1", 1L, 1)
        notesViewModel.onEvent(NotesEvent.DeleteNote(note))
        verify { noteUseCases.deleteNote }
    }

    @Test
    fun `Restore note`() = runTest{
        val slot = slot<Note>()
        val note = Note("1", "1", 1L, 1)
        coEvery { noteUseCases.addNote(capture(slot))} just Runs
        notesViewModel.onEvent(NotesEvent.DeleteNote(note))
        notesViewModel.onEvent(NotesEvent.RestoreNote)
        assertThat(slot.captured).isEqualTo(note)
    }

    @Test
    fun `Toggle order section`() {
        notesViewModel.onEvent(NotesEvent.ToggleOrderSection)
        assertThat(notesViewModel.state.value.isOrderSectionVisible).isTrue()
    }
}