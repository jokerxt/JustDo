package com.example.justdo.model.data.server

import com.example.justdo.domain.entities.server.*
import com.example.justdo.domain.entities.tasks.TodoTask
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface ServerApi {

    companion object {
        private const val API_VERSION = "v2"

        const val GET__GET_TERMS_CONDITIONS = "/api/$API_VERSION/terms"
        const val GET__GET_PRIVACY_POLICY = "/api/$API_VERSION/privacy"

        const val GET__GET_TODO_LIST = "/api/$API_VERSION/todo/all"
        const val POST__TODO_ADD = "/api/$API_VERSION/todo/add"
        const val POST__TODO_DELETE = "/api/$API_VERSION/todo/delete"

        const val POST__GET_TOKEN = "/api/$API_VERSION/token"
        const val POST__REGISTRATION = "/api/$API_VERSION/register"

        const val POST__CHANGE_PASSWORD = "/api/$API_VERSION/password/change"
        const val POST__FORGOT_PASSWORD = "/api/$API_VERSION/password/forgot"

        const val QUERY_TODO_TASK_ID = "todo_task_id"

        const val AUTHORIZATION_HEADER = "Authorization"
    }

    @POST(POST__GET_TOKEN)
    fun getToken(@Body body: TokenRequest): Single<TokenInfo>

    @POST(POST__GET_TOKEN)
    fun refreshToken(@Body body: RefreshTokenRequest): Single<TokenInfo>

    @POST(POST__REGISTRATION)
    fun registration(@Body user: RegUserRequest): Single<BaseServerInfo>

    @GET(GET__GET_PRIVACY_POLICY)
    fun getPrivacyPolicy(): Single<Map<String, String>>

    @GET(GET__GET_TERMS_CONDITIONS)
    fun getTermsConditions(): Single<Map<String, String>>

    @POST(POST__CHANGE_PASSWORD)
    fun changePassword(@Header(AUTHORIZATION_HEADER) typedToken: String,
                       @Body body: ChangePasswordRequest): Single<Any>

    @POST(POST__FORGOT_PASSWORD)
    fun forgotPassword(@Body body: ForgotPasswordRequest): Single<Any>

    @GET(GET__GET_TODO_LIST)
    fun getTodoTasksList(@Header(AUTHORIZATION_HEADER) typedToken: String): Single<Array<TodoTask>>

    @POST(POST__TODO_ADD)
    fun addTodoTask(@Header(AUTHORIZATION_HEADER) typedToken: String,
                    @Body body: TodoTask): Completable

    @POST(POST__TODO_DELETE)
    fun deleteTodoTask(@Header(AUTHORIZATION_HEADER) typedToken: String,
                       @Query(QUERY_TODO_TASK_ID) id: Long): Completable

}