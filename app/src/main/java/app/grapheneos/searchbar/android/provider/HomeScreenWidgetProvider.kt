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

class HomeScreenWidgetProvider : AppWidgetProvider() {

    private lateinit var context: Context
    private var appWidgetId: Int = 0

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        this.context = context!!

        val appWidgetLayout = RemoteViews(context.packageName, R.layout.activity_assist_preview)

        appWidgetLayout.setImageViewResource(R.id.providerButton, findSelectedProvider().iconRes())

        val intent = Intent(context, AssistActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context,0, intent, 0)

        appWidgetLayout.setOnClickPendingIntent(R.id.container, pendingIntent)
        appWidgetIds?.let {
            for (appWidgetId in it) {
                appWidgetManager?.updateAppWidget(appWidgetId, appWidgetLayout)
            }
        }

        appWidgetId = appWidgetIds?.firstOrNull()!!
    }

    private fun findSelectedProvider() =
        findProvider(persistedValues.getPersistedProviderId())

    private fun getSharedPreferencesNameForAppWidget(context: Context, appWidgetId: Int): String {
        return context.packageName + "_preferences_" + appWidgetId
    }

    private fun getPreferences(): SharedPreferences? {

        return context.getSharedPreferences(
            getSharedPreferencesNameForAppWidget(context, appWidgetId), 0
        )
    }

    private val persistedValues by lazy {
        SharedPreferencesPersistedValues(context, getPreferences()!!)
    }

    private val providers by lazy { generateProviders(context, persistedValues) }

    private fun findProvider(providerId: Long) =
        providers.firstOrNull { it.id() == providerId } ?: providers[0]
}