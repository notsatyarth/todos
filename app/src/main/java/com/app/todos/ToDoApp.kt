package com.app.todos

import com.app.todos.di.DaggerAppComponent
import dagger.android.support.DaggerApplication

class ToDoApp : DaggerApplication() {


    override fun applicationInjector() =
            DaggerAppComponent.builder().build()

}