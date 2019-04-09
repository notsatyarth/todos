package com.app.todos.di

import com.app.todos.ToDoApp
import com.app.todos.ui.ToDoItem
import com.app.todos.ui.binding.BindingModule
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import javax.inject.Inject


@Component(modules = [AppModule::class, BindingModule::class, AndroidSupportInjectionModule::class, AndroidInjectionModule::class])
interface AppComponent : AndroidInjector<ToDoApp>


@Module
abstract class AppModule {

    @Binds
    abstract fun repository(repository: RepositoryImpl): Repository


}


interface Repository {
    fun addItem(item: ToDoItem)
    fun itemList(): ReceiveChannel<List<ToDoItem>>
}


class RepositoryImpl @Inject constructor() : Repository {

    val repoScope = CoroutineScope(newSingleThreadContext("repository"));

    private val todoList = mutableListOf<ToDoItem>()


    private val dataChannel = Channel<List<ToDoItem>>()


    override fun addItem(item: ToDoItem) {
        todoList.add(item)
        addToStorage(item)
    }

    private fun addToStorage(item: ToDoItem) = repoScope.launch {
        dataChannel.send(todoList)
    }

    override fun itemList(): ReceiveChannel<List<ToDoItem>> = dataChannel
}


