package com.app.todos.di

import com.app.todos.ui.ToDoItem
import com.app.todos.ui.binding.BindingModule
import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel

@Component(modules = [AppModule::class, BindingModule::class])
interface AppComponent : AndroidInjector<DaggerApplication>


@Module
abstract class AppModule {

    @Binds
    abstract fun repository(repository: Repository): Repository


}


interface Repository {
    fun addItem(item: ToDoItem)
    fun itemList(): ReceiveChannel<List<ToDoItem>>
}


class RepositoryImpl() : Repository {


    private val todoList = mutableListOf<ToDoItem>()


    private val dataChannel = Actor<List<ToDoItem>>()


    override fun addItem(item: ToDoItem) {
        todoList.add(item)
    }

    override fun itemList(): ReceiveChannel<List<ToDoItem>> =
}


