package app.grapheneos.searchbar.arch.model

import android.content.Context
import app.grapheneos.searchbar.R
import app.grapheneos.searchbar.arch.model.persistence.PersistedValues

interface SearchProvider {
    fun iconRes(): Int
    fun name(): String
    fun queryUrl(): String
    fun suggestionsUrl(): String
    fun id(): Long
}

class StaticSearchProvider(
    private val iconRes: Int,
    private val name: String,
    private val queryUrl: String,
    private val suggestionsUrl: String,
    private val id: Long
) : SearchProvider {
    override fun iconRes() = iconRes
    override fun name() = name
    override fun suggestionsUrl() = suggestionsUrl
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
    override fun suggestionsUrl() = ""
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
        "https://duckduckgo.com/ac/?q=%s",
        0
    ),
    StaticSearchProvider(
        R.drawable.ic_provider_google,
        "Google",
        "https://www.google.com/search?q=%s",
        "http://suggestqueries.google.com/complete/search?client=firefox&q=%s",
        1
    ),
    StaticSearchProvider(
        R.drawable.ic_provider_bing,
        "Bing",
        "https://www.bing.com/search?q=%s",
        "https://www.bing.com/AS/Suggestions?qry=%s&cvid=.",
        2
    ),
    StaticSearchProvider(
        R.drawable.ic_provider_yandex,
        "Yandex",
        "https://www.yandex.com/search/?text=%s",
        "https://yandex.com/suggest/suggest-ya.cgi?part=%s&n=5&v=4",
        3
    ),
    StaticSearchProvider(
        R.drawable.ic_provider_yahoo,
        "Yahoo",
        "https://search.yahoo.com/yhs/search?p=%s",
        "https://search.yahoo.com/sugg/gossip/gossip-in-ura/?command=%s&output=json",
        4
    ),
    StaticSearchProvider(
        R.drawable.ic_provider_qwant,
        "Qwant",
        "https://www.qwant.com/?q=%s",
        "https://api.qwant.com/v3/suggest?q=%s",
        5
    ),
    CustomSearchProvider(
        R.drawable.ic_provider_generic,
        context.getString(R.string.custom_provider),
        persistedValues,
        6
    )
)