package app.grapheneos.searchbar.android.provider

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import app.grapheneos.searchbar.R
import app.grapheneos.searchbar.android.activity.AssistActivity

class HomeScreenWidgetProvider : AppWidgetProvider() {
    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)

        val appWidgetLayout = RemoteViews(context?.packageName, R.layout.activity_assist_preview)
        val intent = Intent(context, AssistActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context,0, intent, 0)

        appWidgetLayout.setOnClickPendingIntent(R.id.container, pendingIntent)
        appWidgetIds?.let {
            for (appWidgetId in it) {
                appWidgetManager?.updateAppWidget(appWidgetId, appWidgetLayout)
            }
        }
    }
}