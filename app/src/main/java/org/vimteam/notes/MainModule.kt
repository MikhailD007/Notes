package org.vimteam.notes

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.factoryBy
import org.vimteam.notes.data.models.NoteDB
import org.vimteam.notes.data.repositories.NotesRepository
import org.vimteam.notes.domain.contracts.NotesContract
import org.vimteam.notes.domain.contracts.NotesRepositoryContract
import org.vimteam.notes.domain.contracts.ResourcesProviderContract
import org.vimteam.notes.domain.viewmodels.NotesViewModel
import org.vimteam.notes.ui.providers.ResourcesProvider

object MainModule {
    fun get() = module {

        factoryBy<ResourcesProviderContract, ResourcesProvider>()

        //single { NoteDB() }
        factoryBy<NotesRepositoryContract, NotesRepository>()
        viewModel<NotesContract.ViewModel> { NotesViewModel(get(), get()) }
    }
}