package com.example.yogaadmin.utils;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.yogaadmin.R;
import com.example.yogaadmin.activities.MainActivity;

public class NotificationHelper {

    private static final String CHANNEL_ID_REMINDERS = "yoga_reminders";
    private static final String CHANNEL_ID_BOOKINGS = "yoga_bookings";
    private static final String CHANNEL_ID_UPDATES = "yoga_updates";

    private static final int NOTIFICATION_ID_CLASS_REMINDER = 1001;
    private static final int NOTIFICATION_ID_BOOKING_CONFIRMATION = 1002;
    private static final int NOTIFICATION_ID_CLASS_CANCELLED = 1003;
    private static final int NOTIFICATION_ID_NEW_BOOKING = 1004;

    private Context context;
    private NotificationManagerCompat notificationManager;

    public NotificationHelper(Context context) {
        this.context = context;
        this.notificationManager = NotificationManagerCompat.from(context);
        createNotificationChannels();
    }

    /**
     * Creates notification channels for different types of notifications
     * Required for Android O (API 26) and above
     */
    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Class reminders channel
            NotificationChannel reminderChannel = new NotificationChannel(
                    CHANNEL_ID_REMINDERS,
                    "Class Reminders",
                    NotificationManager.IMPORTANCE_HIGH
            );
            reminderChannel.setDescription("Notifications for upcoming yoga classes");
            reminderChannel.enableVibration(true);
            reminderChannel.setVibrationPattern(new long[]{0, 250, 250, 250});

            // Booking notifications channel
            NotificationChannel bookingChannel = new NotificationChannel(
                    CHANNEL_ID_BOOKINGS,
                    "Booking Updates",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            bookingChannel.setDescription("Notifications for booking confirmations and updates");

            // General updates channel
            NotificationChannel updateChannel = new NotificationChannel(
                    CHANNEL_ID_UPDATES,
                    "App Updates",
                    NotificationManager.IMPORTANCE_LOW
            );
            updateChannel.setDescription("General app notifications and updates");

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(reminderChannel);
            manager.createNotificationChannel(bookingChannel);
            manager.createNotificationChannel(updateChannel);
        }
    }

    /**
     * Shows a class reminder notification
     */
    @SuppressLint("MissingPermission")
    public void showClassReminder(String courseName, String time, String instructor) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_REMINDERS)
                .setSmallIcon(R.drawable.ic_yoga)
                .setContentTitle("Yoga Class Reminder")
                .setContentText(courseName + " starts at " + time + " with " + instructor)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Don't forget! Your " + courseName + " class starts at " + time + " with instructor " + instructor + ". Get ready for your practice!"))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        notificationManager.notify(NOTIFICATION_ID_CLASS_REMINDER, builder.build());
    }

    /**
     * Shows a booking confirmation notification
     */
    @SuppressLint("MissingPermission")
    public void showBookingConfirmation(String courseName, String date, String time) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_BOOKINGS)
                .setSmallIcon(R.drawable.ic_yoga)
                .setContentTitle("Booking Confirmed")
                .setContentText("Your booking for " + courseName + " is confirmed")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Booking confirmed for " + courseName + " on " + date + " at " + time + ". See you on the mat!"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(NOTIFICATION_ID_BOOKING_CONFIRMATION, builder.build());
    }

    /**
     * Shows a class cancellation notification
     */
    @SuppressLint("MissingPermission")
    public void showClassCancellation(String courseName, String date, String time, String reason) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_UPDATES)
                .setSmallIcon(R.drawable.ic_yoga)
                .setContentTitle("Class Cancelled")
                .setContentText(courseName + " on " + date + " has been cancelled")
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Unfortunately, " + courseName + " scheduled for " + date + " at " + time + " has been cancelled. " +
                                (reason != null && !reason.isEmpty() ? "Reason: " + reason : "We apologize for any inconvenience.")))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(NOTIFICATION_ID_CLASS_CANCELLED, builder.build());
    }

    /**
     * Shows a new booking notification (for instructors/admins)
     */
    @SuppressLint("MissingPermission")
    public void showNewBookingNotification(String studentName, String courseName, String date) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_BOOKINGS)
                .setSmallIcon(R.drawable.ic_person)
                .setContentTitle("New Booking")
                .setContentText(studentName + " booked " + courseName)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(studentName + " has booked " + courseName + " for " + date + ". Check your class roster for details."))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(NOTIFICATION_ID_NEW_BOOKING, builder.build());
    }

    /**
     * Shows a simple notification with custom title and message
     */
    @SuppressLint("MissingPermission")
    public void showSimpleNotification(String title, String message) {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? PendingIntent.FLAG_IMMUTABLE : 0
        );

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID_UPDATES)
                .setSmallIcon(R.drawable.ic_yoga)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify((int) System.currentTimeMillis(), builder.build());
    }

    /**
     * Cancels all notifications
     */
    public void cancelAllNotifications() {
        notificationManager.cancelAll();
    }

    /**
     * Cancels a specific notification by ID
     */
    public void cancelNotification(int notificationId) {
        notificationManager.cancel(notificationId);
    }

    /**
     * Checks if notifications are enabled for the app
     */
    public boolean areNotificationsEnabled() {
        return notificationManager.areNotificationsEnabled();
    }
}