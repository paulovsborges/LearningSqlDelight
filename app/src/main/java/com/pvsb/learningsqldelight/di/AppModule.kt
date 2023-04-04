package com.pvsb.learningsqldelight.di

import android.app.Application
import com.pvsb.learningsqldelight.PersonDatabase
import com.pvsb.learningsqldelight.datasource.PersonDatasource
import com.pvsb.learningsqldelight.datasource.PersonDatasourceImpl
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesSqlDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = PersonDatabase.Schema, context = app, name = "person.db"
        )
    }

    @Provides
    @Singleton
    fun providesDataSource(sqlDriver: SqlDriver): PersonDatasource {
        return PersonDatasourceImpl(PersonDatabase(sqlDriver))
    }
}