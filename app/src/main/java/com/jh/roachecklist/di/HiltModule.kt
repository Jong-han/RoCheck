package com.jh.roachecklist.di

import android.content.Context
import androidx.room.Room
import com.baycon.mobilefax.db.RoCheckDB
import com.jh.roachecklist.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltModule {
    @Singleton
    @Provides
    fun provideDataBase(@ApplicationContext context: Context): RoCheckDB {
        return Room.databaseBuilder(
            context, RoCheckDB::class.java, "RoCheckDB.db"
        ).build()
    }
    @Singleton
    @Provides
    fun provideRepository( db: RoCheckDB ): Repository = Repository( db )
}