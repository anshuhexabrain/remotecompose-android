package com.example.remotecompose.data.remote

import com.example.remotecompose.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.CacheControl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

object RemoteConfigFetcher {

    private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    suspend fun fetchDocument(url: String): Result<ByteArray> = withContext(Dispatchers.IO) {
        try {
            val isGitHubUrl = url.toHttpUrlOrNull()?.host == "api.github.com"
            val requestBuilder = Request.Builder()
                .url(url)
                .cacheControl(CacheControl.FORCE_NETWORK)

            if (isGitHubUrl) {
                requestBuilder.header("Accept", "application/vnd.github.raw+json")

                val token = BuildConfig.GITHUB_TOKEN
                if (token.isNotEmpty()) {
                    requestBuilder.header("Authorization", "Bearer $token")
                }
            }

            val response = client.newCall(requestBuilder.build()).execute()
            response.use { resp ->
                if (!resp.isSuccessful) {
                    return@withContext Result.failure(
                        Exception("HTTP ${resp.code}: ${resp.message}")
                    )
                }

                val bytes = resp.body?.bytes()
                    ?: return@withContext Result.failure(Exception("Empty response body"))

                Result.success(bytes)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
