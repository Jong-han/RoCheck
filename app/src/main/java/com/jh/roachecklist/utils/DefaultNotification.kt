package com.jh.roachecklist.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import com.jh.roachecklist.R
import com.jh.roachecklist.ui.character.CharacterActivity

object DefaultNotification {

    private const val NOTIFICATION_ID_DAILY = "NOTIFICATION_ID_DAILY"
    private const val NOTIFICATION_NAME_DAILY = "일일 숙제 알림"

    private const val NOTIFICATION_ID_WEEKLY = "NOTIFICATION_ID_WEEKLY"
    private const val NOTIFICATION_NAME_WEEKLY = "주간 숙제 알림"

    // Notification Code
    const val NOTIFICATION_CODE_DEFAULT = 8001024

    private const val NOTIFICATION_CODE_DAILY = 1000
    private const val NOTIFICATION_CODE_WEEKLY = 1001


    fun startDailyNotification(context: Context, content: String?, mipmapId: Int = R.mipmap.ic_launcher) {

        val notificationManager = context.getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager

        // version for O upper
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel( NOTIFICATION_ID_DAILY, NOTIFICATION_NAME_DAILY, importance ).apply {
                enableLights( false )
                enableVibration( true )
                setShowBadge( false )
            }
            notificationManager.createNotificationChannel( notificationChannel )

        }

        // Make Intent go Activity When Notification Click
        val processIntent = Intent( context, CharacterActivity::class.java )
        processIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK )

        val pIntent = PendingIntent.getActivity( context, System.currentTimeMillis().toInt(), processIntent, 0)

        // builder
        val builder = NotificationCompat.Builder( context, NOTIFICATION_ID_DAILY )
        builder.setLargeIcon( BitmapFactory.decodeResource( context.resources, R.mipmap.ic_launcher ) )
        builder.setSmallIcon( mipmapId )
        builder.setTicker( content )
        builder.setWhen( System.currentTimeMillis() )
        builder.setNumber( 0 )
        builder.setContentTitle( context.getString( R.string.app_name ) )
        builder.setContentText( content ?: "" )
        builder.setContentIntent( pIntent )
        builder.setAutoCancel( true )


        val notification: Notification = builder.build()

        notificationManager.notify( NOTIFICATION_CODE_DAILY, notification )

    }

    fun startWeeklyNotification(context: Context, content: String?, mipmapId: Int = R.mipmap.ic_launcher) {

        val notificationManager = context.getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager

        // version for O upper
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel( NOTIFICATION_ID_WEEKLY, NOTIFICATION_NAME_WEEKLY, importance ).apply {
                enableLights( false )
                enableVibration( true )
                setShowBadge( false )
            }
            notificationManager.createNotificationChannel( notificationChannel )

        }

        // Make Intent go Activity When Notification Click
        val processIntent = Intent( context, CharacterActivity::class.java )
        processIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK )

        val pIntent = PendingIntent.getActivity( context, System.currentTimeMillis().toInt(), processIntent, 0)

        // builder
        val builder = NotificationCompat.Builder( context, NOTIFICATION_ID_WEEKLY )
        builder.setLargeIcon( BitmapFactory.decodeResource( context.resources, R.mipmap.ic_launcher ) )
        builder.setSmallIcon( mipmapId )
        builder.setTicker( content )
        builder.setWhen( System.currentTimeMillis() )
        builder.setNumber( 0 )
        builder.setContentTitle( context.getString( R.string.app_name ) )
        builder.setContentText( content ?: "" )
        builder.setContentIntent( pIntent )
        builder.setAutoCancel( true )


        val notification: Notification = builder.build()

        notificationManager.notify( NOTIFICATION_CODE_WEEKLY, notification )

    }

    fun cancelNotification( context: Context ) {

        val notificationManager = context.getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager
        notificationManager.cancel( NOTIFICATION_CODE_DEFAULT )
    }

}