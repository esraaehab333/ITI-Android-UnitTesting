package com.example.android.architecture.blueprints.todoapp.data

import android.app.Application
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.data.source.local.TasksDao
import com.example.android.architecture.blueprints.todoapp.data.source.local.ToDoDatabase
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksDaoTest {

    private lateinit var db: ToDoDatabase
    private lateinit var tasksDao: TasksDao

    @Before
    fun setup() {
        // Using an in-memory database so that the information is deleted when the process is killed.
        val application = ApplicationProvider.getApplicationContext<Application>()
        db = Room.inMemoryDatabaseBuilder(
            application,
            ToDoDatabase::class.java
        ).build()
        tasksDao = db.taskDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertTask_getTaskById() = runTest {
        // Given a task inserted in the database
        val task = Task(id = "1", title = "title", description = "description")
        tasksDao.insertTask(task)

        // When retrieving a task in the database
        val expectedTask = tasksDao.getTaskById("1")

        // Then task can be retrieved by id from the database
        assertThat(expectedTask!!.id, `is`(task.id))
        assertThat(expectedTask, `is`(task))
    }
}