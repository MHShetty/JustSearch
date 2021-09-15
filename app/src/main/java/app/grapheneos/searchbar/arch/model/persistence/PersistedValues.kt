package app.grapheneos.searchbar.arch.model.persistence

interface PersistedValues {
    fun setPersistedProviderId(id: Long)
    fun getPersistedProviderId(): Long
    fun setCustomProviderUrl(url: String)
    fun getCustomProviderUrl(): String
}
