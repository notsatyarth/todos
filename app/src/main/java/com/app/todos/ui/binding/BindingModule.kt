package com.app.todos.ui.binding

import com.app.todos.ui.ToDoListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class BindingModule {

    @ContributesAndroidInjector
    abstract fun bindsToDoListActivity(): ToDoListActivity
}