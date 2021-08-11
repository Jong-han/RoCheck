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

    // Notification Channel
    private const val NOTIFICATION_ID_EVENT_1 = "sample notification 1"
    private const val NOTIFICATION_NAME_EVENT_1 = "test notification 1"

    private const val NOTIFICATION_ID_EVENT_2 = "sample notification 2"
    private const val NOTIFICATION_NAME_EVENT_2 = "test notification 2"

    // Notification Code
    const val NOTIFICATION_CODE_DEFAULT = 8001024

    // Push Data
    private const val PUSH_DATA_PUSH_KEY_MSG_TYPE = "MT"
    private const val PUSH_DATA_PUSH_TYPE_LINK = "LINK"
    private const val PUSH_DATA_PUSH_KEY_URL = "URL"

    fun startNotification(context: Context, content: String?, type: String,
                          mipmapId: Int = R.mipmap.ic_launcher) {

        val notificationManager = context.getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager

        // version for O upper
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel( NOTIFICATION_ID_EVENT_1, NOTIFICATION_NAME_EVENT_1, importance ).apply {
                enableLights( false )
                enableVibration( true )
                setShowBadge( false )
            }
            notificationManager.createNotificationChannel( notificationChannel )

        }

        // Make Intent go Activity When Notification Click
        val processIntent = Intent( context, CharacterActivity::class.java )
        processIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK )
//        processIntent.putExtra( PUSH_DATA_PUSH_KEY_MSG_TYPE, type)

        val pIntent = PendingIntent.getActivity( context, System.currentTimeMillis().toInt(), processIntent, 0)

        // builder
        val builder = NotificationCompat.Builder( context, NOTIFICATION_ID_EVENT_1 )
        builder.setLargeIcon( BitmapFactory.decodeResource( context.resources, R.mipmap.ic_launcher ) )
        builder.setSmallIcon( mipmapId )
        builder.setTicker( content )
        builder.setWhen( System.currentTimeMillis() )
        builder.setNumber( 0 )
        builder.setContentTitle( context.getString( R.string.app_name ) )
        builder.setContentText( content ?: "" )
        builder.setContentIntent( pIntent )
        builder.setAutoCancel( true )

        /** set Notification when Expand */

        /* set Picture */
//        builder.setStyle( NotificationCompat.BigPictureStyle()
//            .bigPicture( ContextCompat.getDrawable(context, R.drawable.flag_aland_islands)?.toBitmap(100, 100 )) )

        /* set Text */
//        builder.setStyle( NotificationCompat.BigTextStyle().bigText( "txt for test") )

        /* set Inbox */
//        builder.setStyle(
//            NotificationCompat.InboxStyle()
//                .addLine("line 1")
//                .addLine("line 2"))


        val notification: Notification = builder.build()

        notificationManager.notify( NOTIFICATION_CODE_DEFAULT, notification )

    }

    fun cancelNotification( context: Context ) {

        val notificationManager = context.getSystemService( Context.NOTIFICATION_SERVICE ) as NotificationManager
        notificationManager.cancel( NOTIFICATION_CODE_DEFAULT )
    }

}