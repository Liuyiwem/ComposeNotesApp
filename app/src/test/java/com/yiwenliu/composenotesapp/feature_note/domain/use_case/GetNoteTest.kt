package com.yiwenliu.composenotesapp.feature_note.domain.use_case

import com.google.common.truth.Truth.assertThat
import com.yiwenliu.composenotesapp.feature_note.data.repository.FakeNoteRepository
import com.yiwenliu.composenotesapp.feature_note.domain.model.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetNoteTest {

    private lateinit var getNote: GetNote
    private lateinit var fakeRepository: FakeNoteRepository
    private val note = Note("1", "1", 1L, 1, 1)

    @Before
    fun setup() {
        fakeRepository = FakeNoteRepository()
        getNote = GetNote(fakeRepository)
        runTest {
            fakeRepository.insertNote(note)
        }
    }

    @Test
    fun `get note item by id`() = runTest {
        val getNote = getNote(1)
        assertThat(getNote).isEqualTo(note)
    }
}