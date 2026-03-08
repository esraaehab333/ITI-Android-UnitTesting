package com.example.android.architecture.blueprints.todoapp.data

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksDao
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksLocalDataSource
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TasksLocalDataSourceTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: ToDoDatabase
    private lateinit var tasksDao: TasksDao
    private lateinit var localDataSource: TasksLocalDataSource

    @Before
    fun setup() {
        val application = ApplicationProvider.getApplicationContext<Application>()
        db = Room.inMemoryDatabaseBuilder(
            application,
            ToDoDatabase::class.java
        ).build()

        tasksDao = db.taskDao()
        localDataSource = TasksLocalDataSource(tasksDao)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun saveTask_getTask() = runTest {
        // Given a task saved in the local data source
        val task = Task(id = "1", title = "title", description = "description")
        localDataSource.saveTask(task)

        // When saving a task in the local data source
        val expectedTask = localDataSource.getTask(taskId = "1") as Result.Success

        // Then task can be retrieved by id from the local data source
        assertThat(expectedTask.data, `is`(task))
    }
}