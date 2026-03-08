import androidx.lifecycle.LiveData
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.TasksDataSource

class FakeDataSource(val tasksList: MutableList<Task>? = mutableListOf()) : TasksDataSource {

    override fun observeTasks(): LiveData<Result<List<Task>>> {
        TODO(reason = "Not yet implemented")
    }

    override suspend fun deleteAllTasks() {
        tasksList?.clear()
    }

    override suspend fun deleteTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun saveTask(task: Task) {
        tasksList?.add(task)
    }

    override suspend fun completeTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun completeTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun activateTask(task: Task) {
        TODO("Not yet implemented")
    }

    override suspend fun activateTask(taskId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun clearCompletedTasks() {
        TODO("Not yet implemented")
    }

    override suspend fun getTasks(): Result<List<Task>> {
        if (tasksList == null) return Result.Error(Exception("Tasks not found"))
        return Result.Success(tasksList)
    }

    override suspend fun refreshTasks() {
        TODO(reason = "Not yet implemented")
    }

    override fun observeTask(taskId: String): LiveData<Result<Task>> {
        TODO(reason = "Not yet implemented")
    }

    override suspend fun getTask(taskId: String): Result<Task> {
        TODO(reason = "Not yet implemented")
    }

    override suspend fun refreshTask(taskId: String) {
        TODO(reason = "Not yet implemented")
    }
}