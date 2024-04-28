package com.yiwenliu.composenotesapp.feature_note.presentation.add_edit_note

import androidx.compose.ui.focus.FocusState
import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.yiwenliu.composenotesapp.MainCoroutineRule
import com.yiwenliu.composenotesapp.feature_note.domain.model.InvalidNoteException
import com.yiwenliu.composenotesapp.feature_note.domain.model.Note
import com.yiwenliu.composenotesapp.feature_note.domain.use_case.NoteUseCases
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AddEditNoteViewModelTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @MockK(relaxed = true)
    private lateinit var noteUseCases: NoteUseCases
    @MockK(relaxed = true)
    private lateinit var savedStateHandle: SavedStateHandle
    @MockK(relaxed = true)
    private lateinit var focusState: FocusState
    private lateinit var addEditNoteViewModel: AddEditNoteViewModel
    private lateinit var note: Note
    private val existingId = 1

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        note = Note("1", "1", 1L, 1, 1)
        every { savedStateHandle.get<Int>("noteId") } returns existingId
        coEvery { noteUseCases.getNote(existingId) }.returns(note)
        addEditNoteViewModel = AddEditNoteViewModel(noteUseCases, savedStateHandle)
    }

    @Test
    fun `Test init with existing note`() {
        assertThat(addEditNoteViewModel.noteTitle.value.text).isEqualTo(note.title)
        assertThat(addEditNoteViewModel.noteContent.value.text).isEqualTo(note.content)
        assertThat(addEditNoteViewModel.noteColor.value).isEqualTo(note.color)
    }

    @Test
    fun `Entered title`() {
        val title = "Test Title"
        addEditNoteViewModel.onEvent(AddEditNoteEvent.EnteredTitle(title))
        assertThat(addEditNoteViewModel.noteTitle.value.text).isEqualTo(title)
    }

    @Test
    fun `Change title focus and add a blank title, isHintVisible returns true`() {
        every { focusState.isFocused }.returns(false)
        addEditNoteViewModel.onEvent(AddEditNoteEvent.EnteredTitle(""))
        addEditNoteViewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(focusState))
        assertThat(addEditNoteViewModel.noteTitle.value.isHintVisible).isTrue()
    }

    @Test
    fun `Enter content`() {
        val content = "Test Content"
        addEditNoteViewModel.onEvent(AddEditNoteEvent.EnteredContent(content))
        assertThat(addEditNoteViewModel.noteContent.value.text).isEqualTo(content)
    }

    @Test
    fun `Change content focus and add a blank content, isHintVisible returns true`() {
        every { focusState.isFocused }.returns(false)
        addEditNoteViewModel.onEvent(AddEditNoteEvent.EnteredContent(""))
        addEditNoteViewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(focusState))
        assertThat(addEditNoteViewModel.noteContent.value.isHintVisible).isTrue()
    }

    @Test
    fun `Change color`() {
        val color = 1
        addEditNoteViewModel.onEvent(AddEditNoteEvent.ChangeColor(color))
        assertThat(addEditNoteViewModel.noteColor.value).isEqualTo(color)
    }

    @Test
    fun `Save note succeed`() = runTest {
        addEditNoteViewModel.eventFlow.test {
            addEditNoteViewModel.onEvent(AddEditNoteEvent.SaveNote)
            assertThat(awaitItem()).isEqualTo(AddEditNoteViewModel.UiEvent.SaveNote)
        }
        verify{ noteUseCases.addNote}
    }

    @Test
    fun `save note fail`() = runTest{
        val message = "Test save note fail"
        coEvery { noteUseCases.addNote }.throws(InvalidNoteException(message))
        addEditNoteViewModel.eventFlow.test {
            addEditNoteViewModel.onEvent(AddEditNoteEvent.SaveNote)
            assertThat(awaitItem()).isEqualTo(AddEditNoteViewModel.UiEvent.ShowSnackbar(message))
        }

    }
}