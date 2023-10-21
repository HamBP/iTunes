package me.algosketch.itunes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.algosketch.itunes.data.remote.RetrofitFactory
import me.algosketch.itunes.data.remote.api.TrackApi

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideTrackApi(): TrackApi = RetrofitFactory.create()
}