package com.example.android.architecture.blueprints.todoapp.tasks

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.AdditionalMatchers.not

@RunWith(AndroidJUnit4::class) // to understand the application
class TaskViewModelTest {
    @get:Rule
    val instantRule = InstantTaskExecutorRule()
    @Test
    fun addNewTask_setNewTaskEvent(){
        //step 1: create an object from view model to all the function
        //<- deal with this as kotlin class
        // bad way
        // need a context so add a dependance to use it
        val application = ApplicationProvider.getApplicationContext<Application>()
        // Given create viewModel
        val viewModel= TasksViewModel(application)

        // When adding newTask
        viewModel.addNewTask()

        // Then new task value not null
       /* val observar = Observer{
            // make a memory leak in production
            assertNotNull(it)
        }
        // التقليديه عندي هي الاوبسيرف و لكن هي بتحتاج لايف سايكل ف بنسخدم ال observforever
        viewModel.newTaskEvent.observeForever(observar)
        // عشان مش تاثر ع اي يونيت تيست تانيه
        viewModel.newTaskEvent.removeObserver(observar)
        */
        // use the live data test util
        val value = viewModel.newTaskEvent.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled() , not(nullValue()))
    }
}