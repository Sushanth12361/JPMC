package com.example.jpmorgantest.data.sources.search.local
import com.example.jpmorgantest.util.state.Result

interface SearchLocalDataSource {
    fun saveQuery(value: String): Result<Unit>
    fun recentlySearched(): Result<String>
}
