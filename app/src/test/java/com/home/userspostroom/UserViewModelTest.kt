package com.home.userspostroom

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import co.com.ceiba.mobile.pruebadeingreso.data.network.responseModel.UserResponse
import co.com.ceiba.mobile.pruebadeingreso.ui.userlist.UsersViewModel
import com.home.userspostroom.util.OperationResult
import com.home.userspostroom.utiltest.FakeEmptyUserRepository
import com.home.userspostroom.utiltest.FakeEmptyUserRepositoryDB
import com.home.userspostroom.utiltest.FakeUserRepository
import com.home.userspostroom.utiltest.FakeUserRepositoryDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class UserViewModelTest {

    @Mock
    private lateinit var context: Application

    private lateinit var viewModel: UsersViewModel

    private lateinit var isViewLoadingObserver: Observer<Boolean>
    private lateinit var renderUserList: Observer<List<UserResponse>>
    private lateinit var isEmptyUserlist: Observer<Any>

    private val fakeMuseumRepository = FakeUserRepository()
    private val fakeEmptyUserRepository = FakeEmptyUserRepository()
    private val fakeUserRepositoryDB = FakeUserRepositoryDB()
    private val fakeEmptyUserRepositoryDB = FakeEmptyUserRepositoryDB()

    private lateinit var userList: List<UserResponse>
    private lateinit var userEmptyList: List<UserResponse>

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        `when`<Context>(context.applicationContext).thenReturn(context)
        Dispatchers.setMain(testDispatcher)

        mockData()
        setupObservers()
    }

    @After
    fun cleanUp() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }


    @Test
    suspend fun `load users list with ViewModel and Repository returning empty data`() {
        viewModel= UsersViewModel(fakeEmptyUserRepository, fakeEmptyUserRepositoryDB)

        with(viewModel) {
            retrieveUsersDefault()
            isEmptyList.observeForever(isEmptyUserlist)
            progressVisibility.observeForever(isViewLoadingObserver)

            usersList.observeForever(renderUserList)
        }

        runBlockingTest {
            val response = fakeEmptyUserRepository.getUsers()
            Assert.assertTrue(response is OperationResult.Success)
            Assert.assertNotNull(viewModel.progressVisibility.value)
            Assert.assertNotNull(viewModel.isEmptyList.value)
            Assert.assertTrue(viewModel.usersList.value?.size==0)
        }
    }

    @Test
    fun `retrieve users with ViewModel and Repository returns full data`() {

        viewModel= UsersViewModel(fakeMuseumRepository, fakeUserRepositoryDB)

        with(viewModel) {

            retrieveUsersDefault()
            progressVisibility.observeForever(isViewLoadingObserver)
            usersList.observeForever(renderUserList)
        }

        runBlockingTest {
            val response = fakeMuseumRepository.getUsers()
            Assert.assertTrue(response is OperationResult.Success)
            Assert.assertNotNull(viewModel.progressVisibility.value)
            Assert.assertTrue(viewModel.usersList.value?.size==4)
        }
    }


    private fun setupObservers() {
        isViewLoadingObserver = mock(Observer::class.java) as Observer<Boolean>
        isEmptyUserlist = mock(Observer::class.java) as Observer<Any>
        renderUserList = mock(Observer::class.java) as Observer<List<UserResponse>>
    }

    private fun mockData() {
        val mockList: MutableList<UserResponse> = mutableListOf()
        mockList.add(UserResponse(0, "Jhon Doe", "Jhon_doe@gmail.com", "1-770-736-8031 x56442"))
        mockList.add(UserResponse(1, "Jack Black", "Jack_black@gmail.com", "1-230-555-8001 x53332"))
        mockList.add(UserResponse(2, "Jane Doe", "Jane_doe@gmail.com", "1-987-223-3242 x51142"))
        mockList.add(UserResponse(3, "Jack White", "Jack_white@gmail.com", "1-550-441-1122 x56556"))
        userList = mockList.toList()
        userEmptyList = emptyList()
    }
}