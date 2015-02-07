package com.example.danco.homework5.h255danco.fragment;

import android.app.Notification;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.danco.homework5.h255danco.R;
import com.example.danco.homework5.h255danco.activity.TopLevelActivity;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.danco.homework5.h255danco.fragment.FloatingActionFragment.FloatingActionFragmentListener} interface
 * to handle interaction events.
 * Use the {@link FloatingActionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FloatingActionFragment extends Fragment implements View.OnClickListener {

    private FloatingActionFragmentListener listener;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FloatingActionFragment.
     */
    public static FloatingActionFragment newInstance() {
        FloatingActionFragment fragment = new FloatingActionFragment();
        return fragment;
    }


    public FloatingActionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_floating_action, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView fab = (ImageView) view.findViewById(R.id.floatingActionImageView);
        fab.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        // The button clicked so build a notification

        //count the clicks and save to sharedPrefs

        // Always use NotificationCompat as large number of changes across all versions of
        // android. Notification Compat takes care of building a notification that is
        // accurate for each platform.

        // Build public notification, subset of text ok to be shown when phone is locked.
        Notification publicNotification = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_stat_action_android)
                .setContentTitle("Notification Demo")
                .setContentText("Sample pubic notification data")
                .setTicker("Sample pubic notification data")
                .setAutoCancel(true)
                .build();

        // Private notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_stat_action_android)
                .setContentTitle("Notification Demo")

                        // Need to set for OS versions that can not handle Styles. Also used when
                        // notification is "compressed" vs "expanded"
                .setContentText("Sample private notification data")

                        // Left for older OS versions where notification would scroll in status bar
                .setTicker("Sample private notification data")

                        // For OS versions that do not use actions, auto cancel is important
                .setAutoCancel(true)

                        // Showing an expanded text style
                .setStyle(new NotificationCompat.BigTextStyle().bigText(getString(R.string.long_notification_text)))

                        // New for Android 5.0/Wear to set the background color behind the icon
                .setColor(getResources().getColor(R.color.colorPrimary))
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)

                        // Add public version of the notification
                .setPublicVersion(publicNotification)

                        // Adding two actions that will show the scale or color fragment respectively.
                .addAction(R.drawable.ic_stat_image_flare, "Scale",
                        TopLevelActivity.buildNotificationIntent(getActivity(), TopLevelActivity.SCALE_POS))
                .addAction(R.drawable.ic_stat_image_color_lens, "Color",
                        TopLevelActivity.buildNotificationIntent(getActivity(), TopLevelActivity.COLOR_POS));

        // Only add a default intent for Jelly Bean and below. Above, the action buttons provide
        // similar functionality
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            // Always takes you to transform... Still works on newer OS and makes the main
            // notification clickable which isn't desired with action buttons.
            builder.setContentIntent(TopLevelActivity.buildNotificationIntent(getActivity(), TopLevelActivity.TRANSLATION_POS));
        }

        // Using notification manager compat to send notification. Required for Android Wear/Auto extensions on
        // older phones.
        NotificationManagerCompat.from(getActivity()).notify(TopLevelActivity.NOTIFICATION_ID, builder.build());
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface FloatingActionFragmentListener {
        // TODO: Update argument type and name
        public void onFloatingActionInteraction(Uri uri);
    }

    private ViewHolder getViewHolder() {
        View view = getView();
        return view != null ? (ViewHolder) view.getTag() : null;
    }

    /* package */ static class ViewHolder {
        final ImageView actionButton;

        ViewHolder(View view) {
            actionButton = (ImageView) view.findViewById(R.id.floatingActionImageView);
        }
    }
}
