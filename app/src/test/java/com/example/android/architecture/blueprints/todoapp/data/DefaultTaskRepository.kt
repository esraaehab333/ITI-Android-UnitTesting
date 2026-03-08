import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.DefaultTasksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.Result

class DefaultTasksRepositoryTest {

    private lateinit var repository: DefaultTasksRepository
    private lateinit var fakeRemoteDataSource: FakeDataSource
    private lateinit var fakeLocalDataSource: FakeDataSource

    private val localTasks = listOf(
        Task(title = "task1", isCompleted = true),
        Task(title = "task2", isCompleted = false)
    )
    private val remoteTasks = listOf(
        Task(title = "task3", isCompleted = true),
        Task(title = "task4", isCompleted = false)
    )
    // عشان لو حد استخدمها تاني ياخد دي مش ياخد انستانس تاني اصلا
    // before_each , before

    @Before
    fun setup() {
        fakeLocalDataSource = FakeDataSource(tasksList = localTasks.toMutableList())
        fakeRemoteDataSource = FakeDataSource(tasksList = remoteTasks.toMutableList())
        repository = DefaultTasksRepository(
            tasksRemoteDataSource = fakeRemoteDataSource,
            tasksLocalDataSource = fakeLocalDataSource,
            ioDispatcher = UnconfinedTestDispatcher()
        )
    }

    @Test
    fun getTasks_forceUpdateTrue_returnListFromRemote() = runTest {
        // when getAll tasks force update is true
        val result = repository.getTasks(true) as com.example.android.architecture.blueprints.todoapp.data.Result.Success
        // then the tasks should comes from remote data
        assertThat(result.data , IsEqual(remoteTasks))
    }
    @Test
    fun getTasks_forceUpdateTrue_returnListFromLocal() = runTest {
        // when getAll tasks force update is true
        val result = repository.getTasks(false) as com.example.android.architecture.blueprints.todoapp.data.Result.Success
        // then the tasks should comes from remote data
        assertThat(result.data , IsEqual(localTasks))
    }
}