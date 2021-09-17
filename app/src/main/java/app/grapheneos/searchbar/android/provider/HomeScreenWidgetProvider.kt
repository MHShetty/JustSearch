package app.grapheneos.searchbar.android.provider

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.widget.RemoteViews
import androidx.preference.PreferenceManager
import app.grapheneos.searchbar.R
import app.grapheneos.searchbar.android.SharedPreferencesPersistedValues
import app.grapheneos.searchbar.android.activity.AssistActivity
import app.grapheneos.searchbar.arch.model.generateProviders
import app.grapheneos.searchbar.arch.model.SearchProvider

class HomeScreenWidgetProvider : AppWidgetProvider() {

    private lateinit var context: Context

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        this.context = context!!

        val appWidgetLayout = RemoteViews(context.packageName, R.layout.activity_assist_preview)

        val intent = Intent(context, AssistActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context,0, intent, 0)

        appWidgetIds?.let {
            for (appWidgetId in it) {
                appWidgetLayout.setImageViewResource(R.id.providerButton,
                    selectedProvider.iconRes())

                appWidgetLayout.setTextViewText(
                    R.id.searchHint,
                    "${selectedProvider.name()} Search")

                appWidgetLayout.setOnClickPendingIntent(R.id.container, pendingIntent)
                appWidgetManager?.updateAppWidget(appWidgetId, appWidgetLayout)
            }
        }
    }

    private val preferences: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    private val selectedProvider by lazy {
        findProvider(persistedValues.getPersistedProviderId())
    }

    private val persistedValues: SharedPreferencesPersistedValues by lazy {
        SharedPreferencesPersistedValues(context, preferences)
    }

    private val providers: List<SearchProvider> by lazy {
        generateProviders(context, persistedValues)
    }

    private fun findProvider(providerId: Long) : SearchProvider =
        providers.firstOrNull { it.id() == providerId } ?: providers[0]
}