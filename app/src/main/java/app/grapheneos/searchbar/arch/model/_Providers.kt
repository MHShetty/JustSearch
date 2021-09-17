package app.grapheneos.searchbar.arch.model

import android.content.Context
import app.grapheneos.searchbar.R
import app.grapheneos.searchbar.arch.model.persistence.PersistedValues

interface SearchProvider {
    fun iconRes(): Int
    fun name(): String
    fun queryUrl(): String
    fun id(): Long
}

class StaticSearchProvider(
    private val iconRes: Int,
    private val name: String,
    private val queryUrl: String,
    private val id: Long
) : SearchProvider {
    override fun iconRes() = iconRes
    override fun name() = name
    override fun queryUrl() = queryUrl
    override fun id() = id
}

class CustomSearchProvider(
    private val iconRes: Int,
    private val name: String,
    private val persistedValues: PersistedValues,
    private val id: Long
) : SearchProvider {
    override fun iconRes() = iconRes
    override fun name(): String = name
    override fun id() = id
    override fun queryUrl(): String {
        return persistedValues.getCustomProviderUrl()
    }
}

fun generateProviders(context: Context, persistedValues: PersistedValues) = listOf(
    StaticSearchProvider(
        R.drawable.ic_provider_duckduckgo,
        "DuckDuckGo",
        "https://duckduckgo.com?q=%s",
        0
    ),
    StaticSearchProvider(
        R.drawable.ic_provider_google,
        "Google",
        "https://www.google.com/search?q=%s",
        1
    ),
    StaticSearchProvider(
        R.drawable.ic_provider_bing,
        "Bing",
        "https://www.bing.com/search?q=%s",
        2
    ),
    StaticSearchProvider(
        R.drawable.ic_provider_yandex,
        "Yandex",
        "https://www.yandex.com/search/?text=%s",
        3
    ),
    StaticSearchProvider(
        R.drawable.ic_provider_yahoo,
        "Yahoo",
        "https://search.yahoo.com/yhs/search?p=%s",
        4
    ),
    StaticSearchProvider(
        R.drawable.ic_provider_qwant,
        "Qwant",
        "https://www.qwant.com/?q=%s",
        5
    ),
    CustomSearchProvider(
        R.drawable.ic_provider_generic,
        context.getString(R.string.custom_provider),
        persistedValues,
        6
    )
)