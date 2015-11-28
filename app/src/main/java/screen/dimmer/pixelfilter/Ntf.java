package screen.dimmer.pixelfilter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

public class Ntf {
    public static final String LOG = "Pixel Filter"; //NON-NLS
    public static final int NTF_ID = 3321;

    public static void show(Service ctx, boolean enable) {
        NotificationManager ntfMgr = (NotificationManager)ctx.getSystemService(Service.NOTIFICATION_SERVICE);

        if (enable) {
            PendingIntent edit = PendingIntent.getActivity(ctx, 0, new Intent(Intent.ACTION_EDIT, null, ctx, MainActivity.class), PendingIntent.FLAG_CANCEL_CURRENT);
            PendingIntent cancel = PendingIntent.getService(ctx, 0, new Intent(Intent.ACTION_DELETE, null, ctx, FilterService.class), PendingIntent.FLAG_CANCEL_CURRENT);

            NotificationCompat.Action.Builder ntf_act_configure = new NotificationCompat.Action.Builder(R.drawable.ic_build_black, ctx.getResources().getString(R.string.configure), edit);
            NotificationCompat.Action.Builder ntf_act_disable = new NotificationCompat.Action.Builder(R.drawable.ic_close, ctx.getResources().getString(R.string.disable), cancel);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
                    .setContentTitle(ctx.getResources().getString(R.string.app_name))
                    .setContentInfo(ctx.getResources().getString(R.string.tap_to_configure))
                    .setContentIntent(edit)
                    .addAction(ntf_act_configure.build())
                    .addAction(ntf_act_disable.build())
                    .setDeleteIntent(cancel)
                    .setPriority(NotificationCompat.PRIORITY_MIN)
                    .setSmallIcon(R.drawable.notification)
                    .setSound(null)
                    .setOngoing(true)
                    .setLocalOnly(true)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setTicker(null);

            Notification ntf = builder.build();
            ctx.startForeground(NTF_ID, ntf);
        } else {
            ctx.stopForeground(true);
            ntfMgr.cancel(NTF_ID);
        }
    }
}
