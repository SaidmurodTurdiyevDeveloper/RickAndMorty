package com.example.demoforajob.domen.impl

import com.example.demoforajob.data.model.ItemData
import com.example.demoforajob.data.source.local.room.entity.ChangeData
import com.example.demoforajob.domen.UseCaseMain
import com.example.demoforajob.domen.repository.RepositoryMain
import com.example.demoforajob.zzz_others.MyResponce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class UseCaseMainImplament constructor(private var repositoryMain: RepositoryMain) : UseCaseMain {

    override fun loadItems(): Flow<MyResponce<List<ItemData>>> = flow {
        try {
            emit(MyResponce.Loading(true))
            Timber.d("First")

            val result = repositoryMain.getItemsToRemote()
            if (result.isSuccessful) {
                val data = result.body()
                if (data != null) {
                    Timber.d("second")
                    val list = data.results.map {
                        ItemData(it.id, it.name, it.location.name, it.origin.name, it.image, it.url, it.status, it.species)
                    }
                    Timber.d("third")
                    emit(MyResponce.Success(list))
                    repositoryMain.getItemsToStoradge().collectLatest { list ->
                        repositoryMain.deleteItemsToStoradge(list)
                    }
                    Timber.d("four")
                } else {
                    emit(MyResponce.Message("Body is empty"))
                    repositoryMain.getItemsToStoradge().collectLatest { list ->
                        emit(MyResponce.Success(list.map {
                            it.ChangeData()
                        }))
                    }
                }
            } else {
                emit(MyResponce.Message(result.message()))
                repositoryMain.getItemsToStoradge().collectLatest { list ->
                    emit(MyResponce.Success(list.map {
                        it.ChangeData()
                    }))
                }
            }
            emit(MyResponce.Loading(false))
        } catch (e: IOException) {
            Timber.d("Internet Error")
            try {
                emit(MyResponce.Message(e.message.toString()))
                repositoryMain.getItemsToStoradge().collectLatest { list ->
                    emit(MyResponce.Success(list.map {
                        it.ChangeData()
                    }))
                }
            } catch (ext: Exception) {
                Timber.d("Room Error")
                emit(MyResponce.Loading(false))
                emit(MyResponce.Error(ext.message.toString()))
            }
        } catch (e: HttpException) {
            Timber.d("HTTP Error")
            emit(MyResponce.Loading(false))
            emit(MyResponce.Message(e.message.toString()))
        } catch (e: Exception) {
            Timber.d("Base Error\n" + e.message.toString())
            emit(MyResponce.Loading(false))
            emit(MyResponce.Error(e.message.toString()))
        }
    }
}