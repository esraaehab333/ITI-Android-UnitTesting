package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import junit.framework.TestCase.assertEquals
import org.junit.Test
class GetActiveAndCompletedStatsTest {

    @Test
    fun getActiveAndCompletedStats_noActiveTask_returnZeroActive() {
        // Arrange - Given a list with only completed tasks
        val list = listOf(
            Task("task 1", isCompleted = true),
            Task("task 2", isCompleted = true)
        )
        // Act - When calculating statistics
        val result = getActiveAndCompletedStats(list)
        // Assert - Then it should return 100% completed and 0% active
        assertEquals(100f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }
    @Test
    fun getActiveAndCompletedStats_both_returnNumberOfCompletedActive() {
        // Arrange - Given a list with both active and completed tasks
        val list = listOf(
            Task("task 1", isCompleted = true),
            Task("task 2", isCompleted = false)
        )
        // Act - When calculating statistics
        val result = getActiveAndCompletedStats(list)
        // Assert - Then it should return 50% for both
        assertEquals(50f, result.completedTasksPercent)
        assertEquals(50f, result.activeTasksPercent)
    }
    @Test
    fun getActiveAndCompletedStats_noCompletedTask_returnZeroCompleted() {
        // Arrange - Given a list with only active tasks
        val list = listOf(
            Task("task 1", isCompleted = false),
            Task("task 2", isCompleted = false)
        )
        // Act - When calculating statistics
        val result = getActiveAndCompletedStats(list)
        // Assert - Then it should return 0% completed and 100% active
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(100f, result.activeTasksPercent)
    }
    @Test
    fun getActiveAndCompletedStats_nullTasks_returnZero() {
        // Arrange - Given a null task list
        val tasks = null
        // Act - When calculating statistics
        val result = getActiveAndCompletedStats(tasks)
        // Assert - Then both percentages should be 0
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }
    @Test
    fun getActiveAndCompletedStats_emptyList_returnZero() {
        // Arrange - Given an empty task list
        val tasks = emptyList<Task>()
        // Act - When calculating statistics
        val result = getActiveAndCompletedStats(tasks)
        // Assert - Then both percentages should be 0
        assertEquals(0f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }
}