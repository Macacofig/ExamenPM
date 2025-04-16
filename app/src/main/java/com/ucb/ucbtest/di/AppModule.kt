package com.ucb.ucbtest.di

import android.content.Context
import com.ucb.data.BookRepository

import com.ucb.data.book.IBookLocalDataSource
import com.ucb.data.book.IBookRemoteDataSource

import com.ucb.framework.book.BookLocalDataSource
import com.ucb.framework.book.BookRemoteDataSource

import com.ucb.framework.service.RetrofitBuilder
import com.ucb.ucbtest.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

import com.ucb.usecases.book.Buscar
import com.ucb.usecases.book.GetLikedBooks
import com.ucb.usecases.book.LikeBook

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providerRetrofitBuilder(@ApplicationContext context: Context) : RetrofitBuilder {
        return RetrofitBuilder(context)
    }



    @Provides
    @Singleton
    fun provideBookRemoteDataSource(retrofit: RetrofitBuilder): IBookRemoteDataSource {
        return BookRemoteDataSource(retrofit)
    }

    @Provides
    @Singleton
    fun provideBookLocalDatasource(@ApplicationContext context: Context): IBookLocalDataSource {
        return BookLocalDataSource(context)
    }

    @Provides
    @Singleton
    fun bookRepository(remote: IBookRemoteDataSource, local: IBookLocalDataSource): BookRepository {
        return BookRepository(remote = remote, local = local)
    }

    @Provides
    @Singleton
    fun provideBuscar(repo: BookRepository): Buscar {
        return Buscar(repo)
    }

    @Provides
    @Singleton
    fun provideGetLikedBooks(repo: BookRepository): GetLikedBooks {
        return GetLikedBooks(repo)
    }

    @Provides
    @Singleton
    fun provideLikeBook(repo: BookRepository): LikeBook {
        return LikeBook(repo)
    }

}