package com.example.demoforajob.domen.impl

import com.example.demoforajob.data.model.ItemData
import com.example.demoforajob.domen.UseCaseMain
import com.example.demoforajob.domen.repository.RepositoryMain
import com.example.demoforajob.zzz_others.MyResponce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

class UseCaseMainImplament constructor(private var repositoryMain: RepositoryMain) : UseCaseMain {

    override fun loadItems(): Flow<MyResponce<List<ItemData>>> = flow {
        try {
            emit(MyResponce.Loading(true))
            val result = repositoryMain.getItemsToRemote()
            if (result.isSuccessful) {
                val data = result.body()
                if (data != null) {
                    Timber.d("second")
                    val list = data.results.map {
                        ItemData(it.id, it.name, it.location.name, it.origin.name, it.image, it.url, it.status, it.species)
                    }
                    emit(MyResponce.Success(list))
                    val oldList = repositoryMain.getItemsToStoradge()
                    if (oldList.isNotEmpty())
                        repositoryMain.deleteItemsToStoradge(oldList)

                    repositoryMain.saveItemsToStoradge(list)
                    Timber.d("Databasga qo`shildi")
                } else {
                    emit(MyResponce.Message("Body is empty"))
                    val list = repositoryMain.getItemsToStoradge()
                    emit(MyResponce.Success(list.map {
                        ItemData(it.defId, it.name, it.loacation, it.firstSeen, it.imageUri, "", it.status, it.spacies)
                    }))
                    Timber.d("Databasdan olindi")
                }
            } else {
                emit(MyResponce.Message(result.message()))
                val list = repositoryMain.getItemsToStoradge()
                emit(MyResponce.Success(list.map {
                    ItemData(it.defId, it.name, it.loacation, it.firstSeen, it.imageUri, "", it.status, it.spacies)
                }))
                Timber.d("Databasdan olindi")
            }
            emit(MyResponce.Loading(false))
        } catch (e: IOException) {
            Timber.d("Internet Error")
            try {
                emit(MyResponce.Message(e.message.toString()))
                val list = repositoryMain.getItemsToStoradge()
                emit(MyResponce.Success(list.map {
                    ItemData(it.defId, it.name, it.loacation, it.firstSeen, it.imageUri, "", it.status, it.spacies)
                }))
                Timber.d("Databasdan olindi")
            } catch (ext: Exception) {
                Timber.d("Room Error")
                emit(MyResponce.Loading(false))
                emit(MyResponce.Error(ext.message.toString()))
            }
        } catch (e: HttpException) {
            Timber.d("HTTP Error")
            emit(MyResponce.Loading(false))
            emit(MyResponce.Error(e.message.toString()))
        } catch (e: Exception) {
            Timber.d("Base Error\n" + e.message.toString())
            emit(MyResponce.Loading(false))
            emit(MyResponce.Message(e.message.toString()))
        }
    }
}