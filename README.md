# Android Unit Testing: A Comprehensive Guide

**Master automated testing to build reliable, maintainable Android applications**

---

## Table of Contents

### Part I: Testing Fundamentals
1. [Overview](#overview)
2. [What is Testing?](#what-is-testing)
3. [Testing Goals](#testing-goals)
4. [Why Testing Matters](#why-testing-matters)
5. [Manual vs. Automated Testing](#manual-vs-automated-testing)
6. [Testing Types](#testing-types)
7. [Testing Pyramid & Scopes](#testing-pyramid--scopes)
8. [JUnit4 Basics](#junit4-basics)

### Part II: Unit Testing in Practice
9. [Day 1: Writing Your First Tests](#day-1-writing-your-first-tests)
10. [Testing Pure Functions (Statistics Example)](#testing-pure-functions-statistics-example)
11. [Test Structure: Given-When-Then](#test-structure-given-when-then)
12. [Assertions: assertEquals vs. assertThat](#assertions-assertequals-vs-assertthat)
13. [Testing LiveData in ViewModels](#testing-livedata-in-viewmodels)

### Part III: Advanced Testing & Test Doubles
14. [Day 2: Testing Repositories](#day-2-testing-repositories)
15. [The Problem: Testing Repositories](#the-problem-testing-repositories)
16. [Test Doubles Explained](#test-doubles-explained)
17. [Fake Implementation](#fake-implementation)
18. [Dependency Injection for Testability](#dependency-injection-for-testability)
19. [Mock Objects with MockK](#mock-objects-with-mockk)
20. [Testing Coroutines](#testing-coroutines)

### Part IV: Best Practices & Interview Questions
21. [Interview Questions & Answers](#interview-questions--answers)
22. [Important Notes & Common Pitfalls](#important-notes--common-pitfalls)
23. [Testing Checklist](#testing-checklist)
24. [Key Concepts to Research](#key-concepts-to-research)

---

## Overview

**One-line summary:** Automated testing ensures your code behaves correctly, catches regressions early, and enables safe refactoring by running tests instantly rather than manually checking every change.

Testing is not optional—it's a critical part of professional software development. Without tests, bugs make it into production. With tests, you catch issues during development. This guide covers unit, integration, and end-to-end testing patterns for Android.

---

## What is Testing?

### Definition

A **test** is a manual or automatic procedure used to evaluate whether the System Under Test (SUT) behaves correctly.

**From the course definition:**

> Testing your app is an integral part of the app development process. By running tests against your app consistently, you can verify your app's correctness, functional behavior, and usability.

### Two Forms of Testing

| Form | Description | Usage |
|------|-------------|-------|
| **Manual Testing** | Open the app, click buttons, verify results by eye. | Initial exploration, user experience validation. |
| **Automated Testing** | Write code that verifies behavior automatically. | Regression testing, continuous integration, safety net for refactoring. |

**Key insight:** This course focuses on **automated testing**—writing code that tests your code.

---

## Testing Goals

### 1. **Everything is OK** ✅

- Allow safe refactoring without breaking functionality.
- Stable development velocity by minimizing regressions.
- Documentation: tests show how code is supposed to work.

### 2. **Increase Development Speed** ⚡

- Rapid feedback on failures (seconds vs. manual testing).
- Catch bugs immediately during development.
- No need to run the full app constantly.

### 3. **Document Your Code** 📝

- Tests are executable documentation.
- New developers understand expected behavior by reading tests.
- Shows edge cases and error scenarios.

---

## Why Testing Matters

Without tests:
- Developers fear changing code (might break something).
- Each code change requires manual verification.
- Regressions appear in production.
- Onboarding new developers is slow.

With tests:
- Refactor with confidence.
- Changes verified instantly.
- Bugs caught before release.
- Code behavior is clear and documented.

**Interview Question:** *"Why is testing important in software development?"*

**Answer:** *"Testing ensures code correctness, enables safe refactoring, provides rapid feedback during development, and acts as documentation for expected behavior. Without tests, regressions appear in production, and developers fear making changes."*

---

## Manual vs. Automated Testing

### The Problem with Manual Testing

You write code → Open app → Navigate to feature → Click buttons → Check results → Repeat for every change.

**Cost:** Time-consuming, error-prone, non-scalable.

### Automated Testing Solution

You write code → Write test → Run test (0.5 seconds) → Move to next feature.

**Benefit:** Tests run in milliseconds. No need to manually verify every change. Safe refactoring.

---

## Testing Types

Android has three main testing types:

### 1. **Unit Testing** (Local)

**Definition:** Tests a single function or small class in isolation.

```
┌─────────────────────┐
│  Unit Test          │
│  ├─ One function    │
│  ├─ Fast (< 100ms)  │
│  └─ Local JVM       │
└─────────────────────┘
```

**Characteristics:**
- Run on your development machine (JVM), no emulator needed.
- Test business logic without Android framework.
- Fast—usually completes in < 100ms.
- Low fidelity—don't test real UI or Android components.
- Point to failure clearly—if it fails, you know exactly where.

**Tools:** JUnit4, Hamcrest matchers, MockK.

**Example:** Testing a statistics calculation function.

---

### 2. **Integration Testing**

**Definition:** Tests multiple classes or a complete feature working together.

```
┌──────────────────────────┐
│  Integration Test        │
│  ├─ Multiple classes     │
│  ├─ Can be local or      │
│  │  instrumented         │
│  └─ Medium speed         │
└──────────────────────────┘
```

**Characteristics:**
- Tests interaction between components.
- Can run locally (pure Kotlin) or on an emulator (with Android components).
- Slower than unit tests but faster than end-to-end.
- Example: Repository + DataSource + Database interaction.

**Tools:** JUnit4 + MockK, local Room database, fake HTTP responses.

---

### 3. **End-to-End Testing**

**Definition:** Tests the entire app from user interaction to database.

```
┌──────────────────────────┐
│  End-to-End Test         │
│  ├─ Entire feature       │
│  ├─ Always instrumented  │
│  │  (emulator/device)    │
│  └─ Slow (minutes)       │
└──────────────────────────┘
```

**Characteristics:**
- Tests complete user flows.
- Must run on an emulator or physical device (Android environment required).
- Slowest—takes minutes.
- Highest fidelity—reflects real-world scenarios.
- Example: User navigates to task list, adds a task, verifies it appears.

**Tools:** Espresso, UI Automator.

---

## Testing Pyramid & Scopes

```
        /\
       /  \  ← End-to-End Tests (small # of tests, slow)
      /____\
     /      \
    /  Inte- \  ← Integration Tests (medium # of tests, medium speed)
   / gration  \
  /__________\
 /            \
/ Unit Tests   \  ← Unit Tests (lots of tests, fast)
/_______________\
```

**Rule of thumb:**
- **70%** unit tests (fast, isolated)
- **20%** integration tests (components together)
- **10%** end-to-end tests (full user flows)

Why? Unit tests are cheap to write and run fast. E2E tests are expensive and slow. Integration tests balance the two.

---

### Comparison Table

| Aspect | Unit Test | Integration Test | End-to-End Test |
|--------|-----------|------------------|-----------------|
| **Scope** | Single function/class | Multiple classes/feature | Entire app |
| **Speed** | Fast (< 100ms) | Medium (seconds) | Slow (minutes) |
| **Location** | Local JVM | Local or instrumented | Always instrumented |
| **Fidelity** | Low | Medium | High |
| **Isolation** | Complete | Partial | None |
| **Point of Failure** | Exact | Approximate | Unclear |

---

## JUnit4 Basics

### Simple Test Example

```kotlin
import org.junit.Test
import org.junit.Assert.*

class ExampleUnitTest {
    
    @Test
    fun addition_isCorrect() {
        // Arrange: Set up test data
        val expected = 4
        
        // Act: Call the function under test
        val actual = 2 + 2
        
        // Assert: Verify the result
        assertEquals(expected, actual)
    }
}
```

**Key elements:**
- `@Test` annotation marks the method as a test.
- `assertEquals(expected, actual)` verifies the result.
- Test name describes what it tests: `addition_isCorrect()`.
- Green checkmark ✅ = test passed; Red X ❌ = test failed.

When a test fails, JUnit shows exactly what was expected vs. what you got.

---

# PART II: UNIT TESTING IN PRACTICE

## Day 1: Writing Your First Tests

---

## Testing Pure Functions (Statistics Example)

### The Function to Test

```kotlin
// In a Statistics.kt file or extension
internal fun getActiveAndCompletedStats(tasks: List<Task>?): StatsResult {
    val totalTasks = tasks!!.size
    val numberOfActiveTasks = tasks.count { it.isActive }
    return StatsResult(
        activeTasksPercent = 100f * numberOfActiveTasks / tasks.size,
        completedTasksPercent = 100f * (totalTasks - numberOfActiveTasks) / tasks.size
    )
}

data class StatsResult(
    val activeTasksPercent: Float,
    val completedTasksPercent: Float
)

data class Task(
    val title: String,
    val id: String = UUID.randomUUID().toString(),
    val description: String = "",
    val isCompleted: Boolean = false,
) {
    val isActive
        get() = !isCompleted
}
```

This function calculates the percentage of active and completed tasks. It's a pure function—no side effects, same input always gives same output. Perfect for unit testing.

### Writing the Test

```kotlin
import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Test
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat

class GetActiveAndCompletedStatsTest {
    
    @Test
    fun getActiveAndCompletedStats_noActiveTask_returnsZeroActive() {
        // Given a list of completed tasks
        val list = listOf(
            Task(title = "title", isCompleted = true),
            Task(title = "title2", isCompleted = true)
        )
        
        // When calling getActiveAndCompletedStats
        val result = getActiveAndCompletedStats(tasks = list)
        
        // Then return 100% completed and 0% active
        assertThat(result.completedTasksPercent, `is`(100f))
        assertThat(result.activeTasksPercent, `is`(0f))
    }
}
```

---

## Test Structure: Given-When-Then

Every test should follow three clear sections:

### 1. **Given (Arrange)**

Set up the test data and preconditions.

```kotlin
// Given a list of completed tasks
val list = listOf(
    Task(title = "title", isCompleted = true),
    Task(title = "title2", isCompleted = true)
)
```

### 2. **When (Act)**

Call the function under test.

```kotlin
// When calling getActiveAndCompletedStats
val result = getActiveAndCompletedStats(tasks = list)
```

### 3. **Then (Assert)**

Verify the expected outcome.

```kotlin
// Then return 100 completed and zero active
assertThat(result.completedTasksPercent, `is`(100f))
assertThat(result.activeTasksPercent, `is`(0f))
```

### Test Naming Convention

Test names should be descriptive:

```
getActiveAndCompletedStats_noActiveTask_returnsZeroActive()
                          ↑                  ↑
                      Condition          Expected Result
```

**Pattern:** `<functionName>_<inputCondition>_<expectedOutput>()`

This makes test purpose clear without reading the code.

---

## Assertions: assertEquals vs. assertThat

### Using assertEquals (Old Style)

```kotlin
assertEquals(expected = 100f, actual = result.completedTasksPercent)
assertEquals(expected = 0f, actual = result.activeTasksPercent)
```

### Using assertThat (Modern Style)

```kotlin
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat

assertThat(result.completedTasksPercent, `is`(100f))
assertThat(result.activeTasksPercent, `is`(0f))
```

**Why assertThat is better:**
- More readable: "assert that result is 100f"
- Extensible: can use custom matchers like `not()`, `containsString()`, etc.
- Better error messages.

**Note:** `is` is wrapped in backticks because it's a Kotlin keyword.

---

## Adding More Test Cases

### Test: All Tasks Active

```kotlin
@Test
fun getActiveAndCompletedStats_noCompletedTask_returnsZeroComplete() {
    // Given a list of active tasks (none completed)
    val list = listOf(
        Task(title = "title", isCompleted = false),
        Task(title = "title2", isCompleted = false)
    )
    
    // When calling getActiveAndCompletedStats
    val result = getActiveAndCompletedStats(tasks = list)
    
    // Then return 0% completed and 100% active
    assertThat(result.completedTasksPercent, `is`(0f))
    assertThat(result.activeTasksPercent, `is`(100f))
}
```

### Test: Mixed Active and Completed

```kotlin
@Test
fun getActiveAndCompletedStats_both_returnsNumberOfCompletedAndActive() {
    // Given list with one active and one completed task
    val list = listOf(
        Task(title = "title", isCompleted = false),
        Task(title = "title2", isCompleted = true)
    )
    
    // When calling getActiveAndCompletedStats
    val result = getActiveAndCompletedStats(tasks = list)
    
    // Then return 50% completed and 50% active
    assertThat(result.completedTasksPercent, `is`(50f))
    assertThat(result.activeTasksPercent, `is`(50f))
}
```

---

### Test: Edge Case - Null Input

```kotlin
@Test
fun getActiveAndCompletedStats_null_returnsZero() {
    // Given a null task list
    val task: List<Task>? = null
    
    // When calling getActiveAndCompletedStats
    val result = getActiveAndCompletedStats(tasks = task)
    
    // Then return 0% completed and 0% active
    assertThat(result.completedTasksPercent, `is`(0f))
    assertThat(result.activeTasksPercent, `is`(0f))
}
```

**Problem:** The original function crashes with `NullPointerException` on the `tasks!!.size` line.

**Solution - Fix the production code:**

```kotlin
internal fun getActiveAndCompletedStats(tasks: List<Task>?): StatsResult {
    // Handle null input
    val totalTasks = tasks?.size ?: return StatsResult(
        activeTasksPercent = 0f,
        completedTasksPercent = 0f
    )
    
    // Handle empty list (avoid division by zero)
    if (tasks.isEmpty()) return StatsResult(
        activeTasksPercent = 0f,
        completedTasksPercent = 0f
    )
    
    val numberOfActiveTasks = tasks.count { it.isActive }
    return StatsResult(
        activeTasksPercent = 100f * numberOfActiveTasks / tasks.size,
        completedTasksPercent = 100f * (totalTasks - numberOfActiveTasks) / tasks.size
    )
}
```

Now the null test passes. **This is Test-Driven Development (TDD):** write tests first, watch them fail, then fix the code.

---

## Testing LiveData in ViewModels

### Setup Dependencies

Add to `build.gradle.kts` (test):

```kotlin
testImplementation("androidx.arch.core:core-testing:2.2.0")
testImplementation("androidx.test.ext:junit-ktx:1.1.5")
testImplementation("androidx.test:core-ktx:1.5.0")
testImplementation("org.robolectric:robolectric:4.11.1")
```

### The ViewModel to Test

```kotlin
class TasksViewModel(private val repository: TaskRepository) : ViewModel() {
    
    private val _newTaskEvent = MutableLiveData<Event<Unit>>()
    val newTaskEvent: LiveData<Event<Unit>> = _newTaskEvent
    
    fun addNewTask() {
        _newTaskEvent.value = Event(Unit)
    }
}

// Event wrapper to prevent multiple triggerings
data class Event<out T>(private val content: T) {
    private var hasBeenHandled = false
    
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}
```

### Test LiveData Observation

```kotlin
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {
    
    private lateinit var tasksViewModel: TasksViewModel
    
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    
    @Before
    fun setup() {
        tasksViewModel = TasksViewModel(repository = mockRepository())
    }
    
    @Test
    fun addNewTask_setsNewTaskEvent() {
        // When adding a new task
        tasksViewModel.addNewTask()
        
        // Then the new task event is triggered
        val value = tasksViewModel.newTaskEvent.getOrAwaitValue()
        
        assertThat(value.getContentIfNotHandled(), not(nullValue()))
    }
}
```

**Key annotations:**
- `@RunWith(AndroidJUnit4::class)` - Tells JUnit to use AndroidJUnit4 runner (required for Android dependencies).
- `@get:Rule val instantExecutorRule` - Makes LiveData operations synchronous in tests (otherwise they're async).

### The getOrAwaitValue() Helper

LiveData is asynchronous by default. You need a helper to wait for values:

```kotlin
fun <T> LiveData<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T {
    var data: T? = null
    val latch = CountDownLatch(1)
    
    val observer = object : Observer<T> {
        override fun onChanged(o: T?) {
            data = o
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    
    this.observeForever(observer)
    afterObserve.invoke()
    
    if (!latch.await(time, timeUnit)) {
        this.removeObserver(observer)
        throw TimeoutException("LiveData value was never set.")
    }
    
    @Suppress("UNCHECKED_CAST")
    return data as T
}
```

**Why this is needed:** LiveData posts values on the main thread asynchronously. `getOrAwaitValue()` waits synchronously for the value.

---

# PART III: ADVANCED TESTING & TEST DOUBLES

## Day 2: Testing Repositories

---

## The Problem: Testing Repositories

Repositories are hard to test because they:
1. Call real APIs (takes time, requires network, unstable).
2. Access real databases (requires Android framework, slow).
3. Have complex error handling.

**Example problems:**
- `getTasks()` hits a real API → test takes seconds and flakes (sometimes fails, sometimes passes).
- Uses Room database → needs an emulator to run.
- Dependent on network availability → test fails if WiFi is down.

**Solution:** Use **Test Doubles** to replace real dependencies.

---

## Test Doubles Explained

A **test double** is a version of a class specifically created for testing. Think of it like a stunt double in movies—same role, different (safer) version.

### Types of Test Doubles

| Type | Purpose | Use Case |
|------|---------|----------|
| **Fake** | Working implementation, but simplified | Replace database with in-memory list |
| **Mock** | Tracks method calls; verifies they happened correctly | Verify that a method was called with specific arguments |
| **Stub** | Hardcoded responses; no logic | Return "success" every time, ignore actual logic |
| **Dummy** | Placeholder; not actually used | Pass as a required parameter but never interact |
| **Spy** | Real object that tracks calls | Wrap real object, verify it was called |

---

### Fake Implementation

A **fake** is a simplified but functional implementation.

```kotlin
interface TasksDataSource {
    suspend fun getTasks(): Result<List<Task>>
    suspend fun saveTask(task: Task)
    suspend fun deleteAllTasks()
}

// Real implementation (calls API or database)
class TasksRemoteDataSource : TasksDataSource {
    override suspend fun getTasks(): Result<List<Task>> {
        // Real API call
        return try {
            val response = api.getTasks()
            Result.Success(response.tasks)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}

// Fake implementation (in-memory list)
class FakeDataSource(
    private val tasksList: MutableList<Task> = mutableListOf()
) : TasksDataSource {
    
    override suspend fun getTasks(): Result<List<Task>> {
        return Result.Success(tasksList)  // Just return the list
    }
    
    override suspend fun saveTask(task: Task) {
        tasksList.add(task)
    }
    
    override suspend fun deleteAllTasks() {
        tasksList.clear()
    }
}
```

**Advantages of Fake:**
- No network calls.
- No database.
- Instant execution.
- Predictable behavior.

---

## Dependency Injection for Testability

### The Problem: Hardcoded Dependencies

```kotlin
class DefaultTasksRepository {
    // ❌ Hard-coded dependencies—can't inject fakes
    private val remote = TasksRemoteDataSource()
    private val local = TasksLocalDataSource()
}
```

Tests can't use fakes because the repository creates real instances.

### The Solution: Constructor Injection

```kotlin
class DefaultTasksRepository(
    val tasksRemoteDataSource: TasksDataSource,  // Interface, not concrete class
    val tasksLocalDataSource: TasksDataSource
) {
    // Dependencies passed in, can be real or fake
}
```

Now tests can inject fakes:

```kotlin
@Test
fun getTasks_forceUpdateTrue_returnListFromRemote() = runTest {
    // Given: Create fakes
    val fakeRemote = FakeDataSource(tasksList = remoteTasks.toMutableList())
    val fakeLocal = FakeDataSource(tasksList = localTasks.toMutableList())
    
    // Inject fakes into repository
    val repository = DefaultTasksRepository(
        tasksRemoteDataSource = fakeRemote,
        tasksLocalDataSource = fakeLocal
    )
    
    // When
    val result = repository.getTasks(forceUpdate = true)
    
    // Then
    assertThat(result.data, IsEqual(remoteTasks))
}
```

**Key principle:** Depend on abstractions (interfaces), not concrete classes. This is **Dependency Inversion** from SOLID principles.

---

## Mock Objects with MockK

### What is MockK?

MockK is a library for creating **mocks**—objects that track method calls and return programmed responses.

**Add to build.gradle.kts:**

```kotlin
testImplementation("io.mockk:mockk:1.14.0")
```

### Creating a Mock

```kotlin
import io.mockk.mockk
import io.mockk.every

@Test
fun addNewTask_setsNewTaskEvent() {
    // Create a mock repository
    val repository = mockk<TaskRepository>()
    
    // Program the mock to return a value when observeTasks() is called
    val fakeTasks = listOf(
        Task("task1", isCompleted = false),
        Task("task2", isCompleted = true)
    )
    val liveData = MutableLiveData<Result<List<Task>>>()
    liveData.value = Result.Success(fakeTasks)
    
    every { repository.observeTasks() } returns liveData
    
    // Create ViewModel with mocked repository
    val viewModel = TasksViewModel(tasksRepository = repository)
    
    // Test the ViewModel
    viewModel.addNewTask()
    val value = viewModel.newTaskEvent.getOrAwaitValue()
    
    assertThat(value.getContentIfNotHandled(), not(nullValue()))
}
```

**Key MockK functions:**

- `mockk<T>()` - Creates a mock of type T.
- `every { ... } returns ...` - Programs what the mock returns.
- `coEvery { ... } returns ...` - For suspend functions (coroutines).
- `verify { ... }` - Checks if a method was called.
- `coVerify { ... }` - For suspend functions.

### Example: Verifying Method Calls

```kotlin
@Test
fun deleteTask_callsRepository() {
    val repository = mockk<TaskRepository>()
    val task = Task("Test task")
    
    // Program the mock to do nothing (void function)
    every { repository.deleteTask(task) } just Runs
    
    val viewModel = TasksViewModel(tasksRepository = repository)
    viewModel.deleteTask(task)
    
    // Verify deleteTask was called with the right argument
    verify { repository.deleteTask(task) }
}
```

---

## Testing Coroutines

### The Problem

Repository functions are suspend functions. You can't call them from regular test code.

```kotlin
suspend fun getTasks(forceUpdate: Boolean = false): Result<List<Task>>
```

### The Solution: runTest

Use `runTest` from `kotlinx-coroutines-test`:

```kotlin
testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.8.0")
```

### Testing Suspend Functions

```kotlin
import kotlinx.coroutines.test.runTest

@Test
fun getTasks_forceUpdateTrue_returnListFromRemote() = runTest {
    // Setup
    val fakeRemote = FakeDataSource(remoteTasks.toMutableList())
    val fakeLocal = FakeDataSource(localTasks.toMutableList())
    val repository = DefaultTasksRepository(
        tasksRemoteDataSource = fakeRemote,
        tasksLocalDataSource = fakeLocal,
        ioDispatcher = UnconfinedTestDispatcher()  // Test dispatcher
    )
    
    // When
    val result = repository.getTasks(forceUpdate = true) as Result.Success
    
    // Then
    assertThat(result.data, IsEqual(remoteTasks))
}
```

**Key points:**
- `runTest { }` creates a coroutine scope for testing.
- Use `UnconfinedTestDispatcher()` instead of `Dispatchers.IO`.
- Suspend functions run synchronously in tests.

---

## Test Setup with @Before

When you have multiple tests on the same class, avoid duplication:

```kotlin
import org.junit.Before

@RunWith(AndroidJUnit4::class)
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
    
    @Before
    fun setup() {
        // Run before each test
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
        val result = repository.getTasks(forceUpdate = true) as Result.Success
        assertThat(result.data, IsEqual(remoteTasks))
    }
    
    @Test
    fun getTasks_forceUpdateFalse_returnListFromLocal() = runTest {
        val result = repository.getTasks(forceUpdate = false) as Result.Success
        assertThat(result.data, IsEqual(localTasks))
    }
}
```

**Why `@Before`?** Eliminates duplicate setup code. Runs before each test, ensuring a clean state.

---

# PART IV: BEST PRACTICES & INTERVIEW QUESTIONS

## Interview Questions & Answers

### 📌 Q1: What's the difference between unit and integration tests?

**Answer:** *"Unit tests verify a single function or class in isolation, running locally on the JVM. They're fast (milliseconds) and pinpoint failures. Integration tests verify multiple classes working together, can involve databases or network, and are slower. E2E tests verify entire user flows on a real device and are slowest."*

---

### 📌 Q2: Why is it hard to test repositories?

**Answer:** *"Repositories depend on external services: APIs (slow, network-dependent), databases (require Android environment), etc. To test repositories in isolation, we use test doubles—fakes, mocks, or stubs that replace these dependencies. This allows fast, deterministic tests."*

---

### 📌 Q3: What are test doubles?

**Answer:** *"Test doubles are simplified versions of dependencies created for testing. Fakes have working implementations but are simplified (e.g., in-memory list instead of database). Mocks track method calls and verify they happened correctly. Stubs return hardcoded responses. Spies wrap real objects to track calls."*

---

### 📌 Q4: What's the difference between a fake and a mock?

**Answer:** *"A fake has a working implementation but is simplified—e.g., FakeRepository stores tasks in-memory. A mock has no logic; it just tracks calls. Use fakes when testing behavior. Use mocks when verifying that certain methods were called."*

---

### 📌 Q5: Why use dependency injection in testing?

**Answer:** *"If a class creates its own dependencies (hardcoded), tests can't replace them with fakes. Dependency injection passes dependencies to the constructor, allowing tests to inject fakes. This makes code testable and loosely coupled."*

---

### 📌 Q6: What does @Before do?

**Answer:** *"`@Before` runs setup code before each test method. It eliminates duplication and ensures each test starts with a clean state. `@BeforeClass` runs once before all tests. `@After` runs cleanup after each test."*

---

### 📌 Q7: What's the Given-When-Then pattern?

**Answer:** *"Given-When-Then organizes test code into three clear sections: (1) Given—set up test data and preconditions. (2) When—call the function under test. (3) Then—assert the expected result. This makes tests readable and maintainable."*

---

### 📌 Q8: Why use assertThat instead of assertEquals?

**Answer:** *"`assertThat` is more readable ('assert that X is Y') and extensible with matchers like `is()`, `not()`, `containsString()`. It provides clearer error messages. `assertEquals` is older and less flexible."*

---

### 📌 Q9: What's a stunt double in testing?

**Answer:** *"A test double is similar to a stunt double in movies—it's a specialized version of a class that replaces the real version in tests. Just as a stunt double performs dangerous actions, a test double handles testing-specific needs like isolating code or verifying calls."*

---

### 📌 Q10: When would you use mocks vs. fakes?

**Answer:** *"Use fakes when testing behavior (e.g., test a repository that uses a fake database). Use mocks when verifying interaction (e.g., verify that the repository called the API). Fakes focus on 'what' the code does; mocks focus on 'how' it does it."*

---

### 📌 Q11: What's Test-Driven Development (TDD)?

**Answer:** *"TDD is writing tests before production code: (1) Write a test—it fails. (2) Write code to make it pass. (3) Refactor. This leads to testable code, fewer bugs, and better design. The test failures guide development."*

---

### 📌 Q12: Why do tests make you a better developer?

**Answer:** *"Tests document expected behavior, catch regressions early, enable safe refactoring, and force you to write testable code (loose coupling, dependency injection, single responsibility). Developers who write tests are more confident in their changes."*

---

## Important Notes & Common Pitfalls

### 🚨 Pitfall 1: Testing Implementation Details Instead of Behavior

**Problem:**
```kotlin
@Test
fun test_privateVariableChanged() {
    // ❌ Testing private variables (implementation detail)
    viewModel.setName("John")
    assertEquals(viewModel._name.value, "John")  // Don't do this
}
```

**Solution:** Test observable behavior (public API):
```kotlin
@Test
fun test_setName_updatesNameLiveData() {
    viewModel.setName("John")
    assertThat(viewModel.name.getOrAwaitValue(), `is`("John"))  // ✅
}
```

---

### 🚨 Pitfall 2: Not Using Dependency Injection

**Problem:**
```kotlin
class MyRepository {
    // ❌ Hard-coded dependency
    private val api = RetrofitClient.create(ApiService::class.java)
}
```

Tests can't inject a fake API.

**Solution:** Inject via constructor:
```kotlin
class MyRepository(private val apiService: ApiService) {
    // ✅ Can inject fake in tests
}
```

---

### 🚨 Pitfall 3: Forgetting @Before or Setting Up Test Data Inline

**Problem:**
```kotlin
@Test
fun test1() {
    val repo = FakeRepository(tasks = setupTasks())  // Duplicate setup
}

@Test
fun test2() {
    val repo = FakeRepository(tasks = setupTasks())  // Duplicate setup
}
```

**Solution:** Use `@Before`:
```kotlin
@Before
fun setup() {
    repo = FakeRepository(tasks = setupTasks())  // ✅
}
```

---

### 🚨 Pitfall 4: Mixing Unit and Integration Tests

**Problem:**
```kotlin
@Test
fun testRepository() {
    // ❌ Mixing: tests database, API, and ViewModel all at once
    val realDb = Room.databaseBuilder(...).build()
    val realApi = RetrofitClient.create()
    val repository = RealRepository(realDb, realApi)
    // Hard to debug if it fails
}
```

**Solution:** Test in isolation:
```kotlin
// Unit test: Test ViewModel with mocked repo
@Test
fun testViewModel() {
    val mockRepo = mockk<Repository>()
    val viewModel = ViewModel(mockRepo)
    // Fast, focused test
}

// Integration test: Test Repository with real DB and fake API
@Test
fun testRepository() {
    val fakeApi = FakeApiService()
    val realDb = testDatabase()
    val repo = Repository(fakeApi, realDb)
    // Tests integration, still fast
}
```

---

### 📝 Important: Test Independence

Each test must be independent—running test A shouldn't affect test B.

```kotlin
// ❌ BAD: Tests depend on execution order
@Test
fun test1() {
    repository.addTask(task)
}

@Test
fun test2() {
    // ❌ Assumes task from test1 exists
    assertThat(repository.getTasks(), hasItem(task))
}
```

**Solution:** Set up clean state in `@Before`:
```kotlin
@Before
fun setup() {
    repository = FakeRepository()  // Fresh instance every test
}
```

---

### 📝 Important: Naming Tests Clearly

Good test names are documentation:

```kotlin
// ❌ Unclear
@Test
fun test1() { }

// ✅ Clear
@Test
fun addNewTask_withValidTitle_returnsSuccessResult() { }
```

Someone reading the test name should understand what it tests without reading the code.

---

### 📝 Important: Use Fake, Not Mock, for Complex Behavior

```kotlin
// ❌ Overkill with mocks
val mockDb = mockk<Database>()
every { mockDb.getTasks() } returns listOf(...)
every { mockDb.saveTasks(any()) } just Runs
every { mockDb.deleteAllTasks() } just Runs

// ✅ Simpler with fake
val fakeDb = FakeDatabase()
```

If you're programming many `every { }` calls, a fake is cleaner.

---

### 📝 Important: Test Edge Cases

```kotlin
@Test
fun getStats_withEmptyList_returnsZero() {
    // Test edge case: empty list
}

@Test
fun getStats_withNull_returnsZero() {
    // Test edge case: null input
}

@Test
fun getStats_withSingleTask_returnsCorrectPercent() {
    // Test edge case: one item
}
```

Edge cases are where bugs hide.

---

## Testing Checklist

Before pushing code, verify:

- [ ] **Unit tests written** for business logic (statistics, calculations, validation).
- [ ] **ViewModels tested** with mocked repositories.
- [ ] **Repositories tested** with fakes (not real API/DB).
- [ ] **Test names are clear** (Given-When-Then pattern).
- [ ] **@Before setup** eliminates duplication.
- [ ] **Each test is independent** (no dependencies between tests).
- [ ] **Edge cases covered** (null, empty, single item).
- [ ] **Dependencies injected** (not hardcoded).
- [ ] **Mocks used sparingly** (prefer fakes for complex behavior).
- [ ] **Tests run in < 5 seconds** (local tests should be fast).

---

## Key Concepts to Research

| Concept | Why Important | Where to Learn |
|---------|---------------|----|
| **Test-Driven Development (TDD)** | Write tests first, code second. Drives better design. | "Test Driven Development: By Example" by Kent Beck |
| **SOLID Principles** | Testable code follows SOLID. Essential for unit testing. | See Part I: Dependency Injection guide |
| **Coroutine Testing** | Suspend functions need special handling. | Kotlin coroutines docs, test section |
| **LiveData Testing** | Async data requires helpers like `getOrAwaitValue()`. | Android Architecture Components docs |
| **Room Database Testing** | In-memory databases for integration tests. | Android Room docs |
| **Espresso Testing** | UI testing framework for Android. | Android Espresso docs |
| **Mocking Frameworks** | MockK, Mockito, PowerMock. Understand when to use each. | MockK documentation |
| **Code Coverage** | Measure % of code tested. Aim for 70%+ in critical paths. | JaCoCo for Android |
| **Flaky Tests** | Tests that sometimes fail, sometimes pass. Dangerous! | "Flaky Tests" - Google Testing Blog |
| **Property-Based Testing** | Generate random inputs to find edge cases. | QuickCheck, Hypothesis frameworks |

---

## Summary

**Testing is not a luxury—it's a professional responsibility.**

1. **Write unit tests** for pure functions and business logic.
2. **Test ViewModels** with mocked repositories.
3. **Test Repositories** with fake data sources.
4. **Use dependency injection** to enable testable code.
5. **Follow Given-When-Then** for readable tests.
6. **Keep tests fast** (should run in milliseconds).
7. **Name tests clearly** (documentation through test names).
8. **Test edge cases** (null, empty, boundary conditions).
9. **Don't test implementation** (test behavior, not internals).
10. **Aim for 70% unit / 20% integration / 10% E2E** (test pyramid).

**Tests are your safety net. They let you refactor fearlessly.**

---

## Additional Resources

- **Google Testing Codelab:** https://developer.android.com/codelabs/advanced-android-kotlin-training-testing
- **Android Testing Guide:** https://developer.android.com/training/testing
- **MockK Documentation:** https://mockk.io/
- **Kotlin Coroutines Testing:** https://kotlinlang.org/docs/shared-mutable-state-and-concurrency.html#testing
- **JUnit 4 Documentation:** https://junit.org/junit4/
- **Hamcrest Matchers:** https://hamcrest.org/JavaHamcrest/
- **Test-Driven Development:** Kent Beck's "Test Driven Development: By Example"
- **Growing Object-Oriented Software, Guided by Tests:** Freeman & Pryce (classic on testing)
