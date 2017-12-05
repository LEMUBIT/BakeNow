package com.lemuel.lemubit.bakenow.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.lemuel.lemubit.bakenow.MainActivity;
import com.lemuel.lemubit.bakenow.R;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {
    public static String EXTRA_WORD =
            "com.lemuel.lemubit.bakenow.Widget.WORD";
    private int[] mAppWidgetIds;

    //not used currently
    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId) {


        Intent RcpIntent = new Intent(context, RecipeWidgetService.class);
        RcpIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        RcpIntent.setData(Uri.parse(RcpIntent.toUri(Intent.URI_INTENT_SCHEME)));
        RemoteViews widget = new RemoteViews(context.getPackageName(),
                R.layout.recipe_app_widget);
        widget.setRemoteAdapter(R.id.recipeList, RcpIntent);
        widget.setEmptyView(R.id.recipeList, R.id.empty_view);
        Intent clickIntent = new Intent(context, MainActivity.class);
        PendingIntent clickList = PendingIntent
                .getActivity(context, 0,
                        clickIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        widget.setPendingIntentTemplate(R.id.recipeList, clickList);
        appWidgetManager.updateAppWidget(appWidgetId, widget);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, widget);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them

        mAppWidgetIds = appWidgetIds;
        for (int appWidgetId : appWidgetIds) {


            Intent RcpIntent = new Intent(context, RecipeWidgetService.class);
            RcpIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            RcpIntent.setData(Uri.parse(RcpIntent.toUri(Intent.URI_INTENT_SCHEME)));
            RemoteViews widget = new RemoteViews(context.getPackageName(),
                    R.layout.recipe_app_widget);
            widget.setRemoteAdapter(R.id.recipeList, RcpIntent);
            widget.setEmptyView(R.id.recipeList, R.id.empty_view);
            Intent clickIntent = new Intent(context, MainActivity.class);
            PendingIntent clickList = PendingIntent
                    .getActivity(context, 0,
                            clickIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            widget.setPendingIntentTemplate(R.id.recipeList, clickList);
            appWidgetManager.updateAppWidget(appWidgetId, widget);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {

    }


    @Override
    public void onReceive(Context context, Intent intent) {
        mAppWidgetIds = intent.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            onUpdate(context, AppWidgetManager.getInstance(context), mAppWidgetIds);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.notifyAppWidgetViewDataChanged(mAppWidgetIds, R.id.recipeList);
        }
        super.onReceive(context, intent);
    }
}

