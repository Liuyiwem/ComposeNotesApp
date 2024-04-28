package com.yiwenliu.composenotesapp.feature_note.data.data_source

import com.google.common.truth.Truth.assertThat
import androidx.test.filters.SmallTest
import com.yiwenliu.composenotesapp.di.AppModule
import com.yiwenliu.composenotesapp.feature_note.domain.model.Note
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
@UninstallModules(AppModule::class)
class NoteDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var database: NoteDatabase
    private lateinit var dao: NoteDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.noteDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertNote() = runTest {
        val noteItem = Note("1","1",1L,1)
        dao.insertNote(noteItem)
        val allNoteItems = dao.getNotes().first()
        assertThat(allNoteItems.contains(noteItem))
    }

    @Test
    fun deleteNote() = runTest {
        val noteItem = Note("1","1",1L,1)
        dao.insertNote(noteItem)
        dao.deleteNote(noteItem)
        val allNoteItems = dao.getNotes().first()
        assertThat(allNoteItems).doesNotContain(noteItem)
    }

    @Test
    fun getNoteById() = runTest {
        val noteItem1 = Note("1","1",1L,1, 1)
        val noteItem2 = Note("1","1",1L,1, 2)
        val noteItem3 = Note("1","1",1L,1, 3)
        dao.insertNote(noteItem1)
        dao.insertNote(noteItem2)
        dao.insertNote(noteItem3)
        val note = dao.getNoteById(1)
        assertThat(note).isEqualTo(noteItem1)
    }

    @Test
    fun getNotes() = runTest {
        val noteItem1 = Note("1","1",1L,1, 1)
        val noteItem2 = Note("1","1",1L,1, 2)
        val noteItem3 = Note("1","1",1L,1, 3)
        dao.insertNote(noteItem1)
        dao.insertNote(noteItem2)
        dao.insertNote(noteItem3)
        val allNoteItems = dao.getNotes().first()
        assertThat(allNoteItems.size).isEqualTo(3)
    }
}