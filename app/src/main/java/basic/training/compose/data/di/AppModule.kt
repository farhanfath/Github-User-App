package basic.training.compose.data.di

import basic.training.compose.data.remote.provideGithubApi
import basic.training.compose.data.remote.provideOkHttpClient
import basic.training.compose.data.remote.provideRetrofit
import basic.training.compose.data.repository.UserRepositoryImpl
import basic.training.compose.domain.repository.UserRepository
import basic.training.compose.domain.usecase.GetUsersUseCase
import basic.training.compose.presentation.viewmodel.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single { provideGithubApi(get()) }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single { GetUsersUseCase(get()) }
    viewModel { UserViewModel(get()) }
}