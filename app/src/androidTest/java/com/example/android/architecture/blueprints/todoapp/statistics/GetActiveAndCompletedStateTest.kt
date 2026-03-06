package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import junit.framework.TestCase.assertEquals
import org.junit.Test
// test driven development
class GetActiveAndCompletedStateTest {
    @Test
    fun getActiveAndCompletedStats_noActiveTask_returnZeroActive() {
        // AAA Arrange - Act - Assert
        // Given list of completed
        val list = listOf(
            Task("task 1", isCompleted = true),
            Task("task 2", isCompleted = true)
        )
        // When calling getActiveAndCompletedStats
        val result = getActiveAndCompletedStats(list)
        // Then return 100 completed an zero active
        assertEquals(100f, result.completedTasksPercent)
        assertEquals(0f, result.activeTasksPercent)
    }
    @Test
    fun getActiveAndCompletedStats_both_returnNumberOfCompletedActive(){
        // AAA Arrange - Act - Assert
        // Given list of completed
        val list = listOf(
            Task("task 1" , isCompleted = true),
            Task("task 2" , isCompleted = false)
        )
        // When calling getActiveAndCompletedStats
        val result = getActiveAndCompletedStats(list)
        // Then return 100 completed an zero active
        assertEquals(50f,result.completedTasksPercent)
        assertEquals(50f,result.activeTasksPercent)
    }
    @Test
    fun getActiveAndCompletedStats_noCompletedTask_returnZeroCompleted(){
        // AAA Arrange - Act - Assert
        // Given list of completed
        val list = listOf(
            Task("task 1" , isCompleted = false),
            Task("task 2" , isCompleted = false)
        )
        // When calling getActiveAndCompletedStats
        val result = getActiveAndCompletedStats(list)
        // Then return 0 completed an 100 active
        assertEquals(0f,result.completedTasksPercent)
        assertEquals(100f,result.activeTasksPercent)
    }
    @Test
    fun getActiveAndCompletedStats_nullTasks_returnZero(){
        // Given list of completed
        val tasks = null
        // When calling getActiveAndCompletedStats
        val result = getActiveAndCompletedStats(tasks)
        // Then return 0 completed an 100 active
        assertEquals(0f,result.completedTasksPercent)
        assertEquals(0f,result.activeTasksPercent)
    }
    @Test
    fun getActiveAndCompletedStats_emptyList_returnZero(){
        // Given list of completed
        val tasks = emptyList<Task>()
        // When calling getActiveAndCompletedStats
        val result = getActiveAndCompletedStats(tasks)
        // Then return 0 completed an 100 active
        assertEquals(0f,result.completedTasksPercent)
        assertEquals(0f,result.activeTasksPercent)
    }
}