package app.grapheneos.searchbar.android.provider

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import app.grapheneos.searchbar.R
import app.grapheneos.searchbar.android.SharedPreferencesPersistedValues
import app.grapheneos.searchbar.android.activity.AssistActivity
import app.grapheneos.searchbar.arch.model.generateProviders
import android.content.SharedPreferences
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
                    findSelectedProvider(appWidgetId).iconRes())
                appWidgetLayout.setTextViewText(
                    R.id.searchHint,
                    "${findSelectedProvider(appWidgetId).name()} Search")

                appWidgetLayout.setOnClickPendingIntent(R.id.container, pendingIntent)
                appWidgetManager?.updateAppWidget(appWidgetId, appWidgetLayout)
            }
        }
    }

    private fun findSelectedProvider(appWidgetId: Int) =
        findProvider(persistedValuesFor(appWidgetId).getPersistedProviderId(), appWidgetId)

    private fun getSharedPreferencesNameForAppWidget(context: Context, appWidgetId: Int): String {
        return context.packageName + "_preferences_" + appWidgetId
    }

    private fun getPreferences(appWidgetId: Int): SharedPreferences? {
        return context.getSharedPreferences(
            getSharedPreferencesNameForAppWidget(context, appWidgetId), 0
        )
    }

    private fun persistedValuesFor(appWidgetId: Int) : SharedPreferencesPersistedValues =
        SharedPreferencesPersistedValues(context, getPreferences(appWidgetId)!!)

    private fun providersFor(appWidgetId: Int) =
        generateProviders(context, persistedValuesFor(appWidgetId))

    private fun findProvider(providerId: Long, appWidgetId: Int) : SearchProvider {
        val providers = providersFor(appWidgetId)
        return providers.firstOrNull { it.id() == providerId } ?: providers[0]
    }
}