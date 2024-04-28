package com.yiwenliu.composenotesapp.feature_note.data.repository

import com.google.common.truth.Truth.assertThat
import com.yiwenliu.composenotesapp.feature_note.data.data_source.NoteDao
import com.yiwenliu.composenotesapp.feature_note.domain.model.Note
import com.yiwenliu.composenotesapp.feature_note.domain.repository.NoteRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class NoteRepositoryImplTest {

    @MockK(relaxed = true)
    private lateinit var mockDao: NoteDao
    private lateinit var repository: NoteRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = NoteRepositoryImpl(mockDao)
    }

    @Test
    fun getNotes() = runTest {
        val noteList = listOf(
            Note("1", "1", 1L, 1),
            Note("2", "2", 2L, 2)
        )
        every { mockDao.getNotes() }.returns(flowOf(noteList))
        val getNoteList = repository.getNotes().first()
        assertThat(getNoteList).isEqualTo(noteList)
    }

    @Test
    fun getNoteById() = runTest {
        val note = Note("1", "1", 1L, 1, 1)
        coEvery { mockDao.getNoteById(1) }.returns(note)
        val getNoteById = repository.getNoteById(1)
        assertThat(getNoteById).isEqualTo(note)
    }

    @Test
    fun insertNote() = runTest{
        val note = Note("1", "1", 1L, 1)
        repository.insertNote(note)
        coVerify { mockDao.insertNote(note) }
    }

    @Test
    fun deleteNote()= runTest{
        val note = Note("1", "1", 1L, 1)
        repository.deleteNote(note)
        coVerify { mockDao.deleteNote(note) }
    }
}