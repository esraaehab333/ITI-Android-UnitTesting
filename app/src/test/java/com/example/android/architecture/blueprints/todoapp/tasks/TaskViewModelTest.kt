package com.example.android.architecture.blueprints.todoapp.tasks


import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import com.example.android.architecture.blueprints.todoapp.R
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TaskRepository
import com.example.android.architecture.blueprints.todoapp.data.source.remote.TasksRemoteDataSource
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class) // to understand the application
class TaskViewModelTest {
    private lateinit var viewModel:TasksViewModel
    private lateinit var repository: TaskRepository
    private val testDispatcher = UnconfinedTestDispatcher()
    @get:Rule
    val instantRule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        every{repository.observeTasks()} returns MutableLiveData()
        viewModel = TasksViewModel(repository)
    }
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
    @Test
    fun addNewTask_setNewTaskEvent(){
        //step 1: create an object from view model to all the function
        //<- deal with this as kotlin class
        // bad way
        // need a context so add a dependance to use it

        // When adding newTask
        viewModel.addNewTask()
        val value = viewModel.newTaskEvent.getOrAwaitValue()
        assertThat(value.getContentIfNotHandled() , not(nullValue()))
    }
    @Test
    fun setFiltering_allTasks_setsCorrectValues() {
        // When
        viewModel.setFiltering(TasksFilterType.ALL_TASKS)
        // Then
        val value = viewModel.tasksAddViewVisible.getOrAwaitValue()
        assertThat(value, `is`(true))
    }
    @Test
    fun setFiltering_activeTasks_setsCorrectValues() {

        // Given
        //val application = ApplicationProvider.getApplicationContext<Application>()
      //  val viewModel = TasksViewModel(application)
        // When
        viewModel.setFiltering(TasksFilterType.ACTIVE_TASKS)
        // Then
        val value = viewModel.tasksAddViewVisible.getOrAwaitValue()
        assertThat(value, `is`(false))
    }
    @Test
    fun setFiltering_completedTasks_setsCorrectValues() {

        // Given
       // val application = ApplicationProvider.getApplicationContext<Application>()
       // val viewModel = TasksViewModel(application)
        // When
        viewModel.setFiltering(TasksFilterType.COMPLETED_TASKS)
        // Then
        val value = viewModel.tasksAddViewVisible.getOrAwaitValue()
        assertThat(value, `is`(false))
    }
    @Test
    fun completeTask_taskIsCompleted_repositoryCalledCompleteTaskAndShowSnackbar() {
        // Given a task in the repository
        val task = Task(title = "Title", description = "Description")
        coEvery { repository.completeTask(task) } just runs

        // When task is completed
        viewModel.completeTask(task, completed = true)

        // Then repository is called to complete the task and snackbar is shown
        coVerify { repository.completeTask(task) }
        coVerify(exactly = 0) { repository.activateTask(task) }

        val message = viewModel.snackbarText.getOrAwaitValue()
        assertThat(
            message.getContentIfNotHandled(),
            `is`(R.string.task_marked_complete)
        )
    }
}