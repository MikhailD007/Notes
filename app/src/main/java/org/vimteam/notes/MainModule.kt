package org.vimteam.notes

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.experimental.builder.factoryBy
import org.vimteam.notes.data.interfaces.FirestoreDatabaseContract
import org.vimteam.notes.data.database.FirestoreDatabase
import org.vimteam.notes.data.repositories.NotesRepository
import org.vimteam.notes.domain.contracts.*
import org.vimteam.notes.domain.viewmodels.NavigationViewModel
import org.vimteam.notes.domain.viewmodels.NoteViewModel
import org.vimteam.notes.domain.viewmodels.NotesListViewModel
import org.vimteam.notes.ui.providers.ResourcesProvider

object MainModule {
    fun get() = module {

        factoryBy<ResourcesProviderContract, ResourcesProvider>()

        single<FirestoreDatabaseContract> { FirestoreDatabase() }

        factory<NotesRepositoryContract> { NotesRepository(get()) }

        viewModel<NotesListContract.ViewModel> { NotesListViewModel(get(), get()) }
        viewModel<NoteContract.ViewModel> { NoteViewModel(get(), get()) }
        viewModel<NavigationContract.ViewModel> { NavigationViewModel() }
    }
}