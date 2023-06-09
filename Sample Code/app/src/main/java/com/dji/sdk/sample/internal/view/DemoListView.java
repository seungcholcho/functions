package com.dji.sdk.sample.internal.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;

import com.dji.sdk.sample.R;
import com.dji.sdk.sample.demo.accessory.AccessoryAggregationView;
import com.dji.sdk.sample.demo.accessory.AudioFileListManagerView;
import com.dji.sdk.sample.demo.airlink.RebootWiFiAirlinkView;
import com.dji.sdk.sample.demo.airlink.SetGetOcuSyncLinkView;
import com.dji.sdk.sample.demo.airlink.SetGetWiFiLinkSSIDView;
import com.dji.sdk.sample.demo.appactivation.AppActivationView;
import com.dji.sdk.sample.demo.battery.PushBatteryDataView;
import com.dji.sdk.sample.demo.battery.SetGetDischargeDayView;
import com.dji.sdk.sample.demo.camera.CameraCalibration;
import com.dji.sdk.sample.demo.camera.FetchMediaView;
import com.dji.sdk.sample.demo.camera.LiveStreamView;
import com.dji.sdk.sample.demo.camera.MediaPlaybackView;
import com.dji.sdk.sample.demo.camera.MultipleLensCameraView;
import com.dji.sdk.sample.demo.camera.PlaybackCommandsView;
import com.dji.sdk.sample.demo.camera.PlaybackDownloadView;
import com.dji.sdk.sample.demo.camera.PlaybackPushInfoView;
import com.dji.sdk.sample.demo.camera.PushCameraDataView;
import com.dji.sdk.sample.demo.camera.RecordVideoView;
import com.dji.sdk.sample.demo.camera.SetGetISOView;
import com.dji.sdk.sample.demo.camera.ShootSinglePhotoView;
import com.dji.sdk.sample.demo.camera.ShootSuperResolutionPhotoView;
import com.dji.sdk.sample.demo.camera.VideoFeederView;
import com.dji.sdk.sample.demo.camera.XT2CameraView;
import com.dji.sdk.sample.demo.datalocker.AccessLockerView;
import com.dji.sdk.sample.demo.firmwareupgrade.FirmwareUpgradeView;
import com.dji.sdk.sample.demo.flightcontroller.CompassCalibrationView;
import com.dji.sdk.sample.demo.flightcontroller.FlightAssistantPushDataView;
import com.dji.sdk.sample.demo.flightcontroller.FlightHubView;
import com.dji.sdk.sample.demo.flightcontroller.FlightLimitationView;
import com.dji.sdk.sample.demo.flightcontroller.NetworkRTKView;
import com.dji.sdk.sample.demo.flightcontroller.OrientationModeView;
import com.dji.sdk.sample.demo.flightcontroller.VirtualStickView;
import com.dji.sdk.sample.demo.gimbal.GimbalCapabilityView;
import com.dji.sdk.sample.demo.gimbal.MoveGimbalWithSpeedView;
import com.dji.sdk.sample.demo.gimbal.PushGimbalDataView;
import com.dji.sdk.sample.demo.key.KeyedInterfaceView;
import com.dji.sdk.sample.demo.keymanager.KeyManagerView;
import com.dji.sdk.sample.demo.lidar.LidarView;
import com.dji.sdk.sample.demo.lookat.LookAtMissionView;
import com.dji.sdk.sample.demo.missionoperator.FollowMeMissionOperatorView;
import com.dji.sdk.sample.demo.missionoperator.WaypointMissionOperatorView;
import com.dji.sdk.sample.demo.missionoperator.WaypointV2MissionOperatorView;
import com.dji.sdk.sample.demo.mobileremotecontroller.MobileRemoteControllerView;
import com.dji.sdk.sample.demo.radar.RadarView;
import com.dji.sdk.sample.demo.remotecontroller.DualRemoteControllerView;
import com.dji.sdk.sample.demo.remotecontroller.PushRemoteControllerDataView;
import com.dji.sdk.sample.demo.rid.UASView;
import com.dji.sdk.sample.demo.timeline.TimelineMissionControlView;
import com.dji.sdk.sample.demo.useraccount.LDMView;
import com.dji.sdk.sample.internal.controller.DJISampleApplication;
import com.dji.sdk.sample.internal.controller.ExpandableListAdapter;
import com.dji.sdk.sample.internal.controller.MainActivity;
import com.dji.sdk.sample.internal.model.GroupHeader;
import com.dji.sdk.sample.internal.model.GroupItem;
import com.dji.sdk.sample.internal.model.ListItem;
import com.squareup.otto.Subscribe;

/**
 * This view is in charge of showing all the demos in a list.
 */

public class DemoListView extends FrameLayout {

    private ExpandableListAdapter listAdapter;
    private ExpandableListView expandableListView;

    public DemoListView(Context context) {
        this(context, null, 0);
    }

    public DemoListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DemoListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        final LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.demo_list_view, this);

        // Build model for ListView
        ListItem.ListBuilder builder = new ListItem.ListBuilder();
        builder.addGroup(R.string.component_listview_camera,
                false,
                new GroupItem(R.string.camera_listview_push_info, PushCameraDataView.class),
                new GroupItem(R.string.camera_listview_iso, SetGetISOView.class),
                new GroupItem(R.string.camera_listview_shoot_single_photo, ShootSinglePhotoView.class),
                new GroupItem(R.string.camera_listview_shoot_super_resolutiob_photo, ShootSuperResolutionPhotoView.class),
                new GroupItem(R.string.camera_listview_record_video, RecordVideoView.class),
                new GroupItem(R.string.camera_listview_playback_push_info, PlaybackPushInfoView.class),
                new GroupItem(R.string.camera_listview_playback_command, PlaybackCommandsView.class),
                new GroupItem(R.string.camera_listview_playback_download, PlaybackDownloadView.class),
                new GroupItem(R.string.camera_listview_download_media, FetchMediaView.class),
                new GroupItem(R.string.camera_listview_media_playback, MediaPlaybackView.class),
                new GroupItem(R.string.component_listview_video_feeder, VideoFeederView.class),
                new GroupItem(R.string.component_xt2_camera_view, XT2CameraView.class),
                new GroupItem(R.string.camera_calibration, CameraCalibration.class));

        builder.addGroup(R.string.component_listview_gimbal,
                false,
                new GroupItem(R.string.gimbal_listview_push_info, PushGimbalDataView.class),
                new GroupItem(R.string.gimbal_listview_rotate_gimbal, MoveGimbalWithSpeedView.class),
                new GroupItem(R.string.gimbal_listview_gimbal_capability, GimbalCapabilityView.class));

        builder.addGroup(R.string.component_listview_battery,
                false,
                new GroupItem(R.string.battery_listview_push_info, PushBatteryDataView.class),
                new GroupItem(R.string.battery_listview_set_get_discharge_day, SetGetDischargeDayView.class));

        builder.addGroup(R.string.component_listview_flight_controller,
                false,
                new GroupItem(R.string.flight_controller_listview_compass_calibration,
                        CompassCalibrationView.class),
                new GroupItem(R.string.flight_controller_listview_flight_limitation,
                        FlightLimitationView.class),
                new GroupItem(R.string.flight_controller_listview_orientation_mode, OrientationModeView.class),
                new GroupItem(R.string.flight_controller_listview_virtual_stick, VirtualStickView.class),
                new GroupItem(R.string.flight_controller_listview_intelligent_flight_assistant,
                        FlightAssistantPushDataView.class),
                new GroupItem(R.string.flight_controller_listview_networkRTK, NetworkRTKView.class));
        builder.addGroup(R.string.component_listview_flight_controller,
                false,
                new GroupItem(R.string.flight_controller_listview_compass_calibration,
                        CompassCalibrationView.class),
                new GroupItem(R.string.flight_controller_listview_flight_limitation,
                        FlightLimitationView.class),
                new GroupItem(R.string.flight_controller_listview_orientation_mode, OrientationModeView.class),
                new GroupItem(R.string.flight_controller_listview_virtual_stick, VirtualStickView.class),
                new GroupItem(R.string.flight_controller_listview_intelligent_flight_assistant,
                        FlightAssistantPushDataView.class),
                new GroupItem(R.string.flight_controller_listview_networkRTK, NetworkRTKView.class));


        // Set-up ExpandableListView
        expandableListView = (ExpandableListView) view.findViewById(R.id.expandable_list);
        listAdapter = new ExpandableListAdapter(context, builder.build());
        expandableListView.setAdapter(listAdapter);
        DJISampleApplication.getEventBus().register(this);
        expandAllGroupIfNeeded();
    }

    @Subscribe
    public void onSearchQueryEvent(MainActivity.SearchQueryEvent event) {
        listAdapter.filterData(event.getQuery());
        expandAllGroup();
    }

    /**
     * Expands all the group that has children
     */
    private void expandAllGroup() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            expandableListView.expandGroup(i);
        }
    }

     /**
     * Expands all the group that has children
     */
    private void expandAllGroupIfNeeded() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            if (listAdapter.getGroup(i) instanceof GroupHeader
                    && ((GroupHeader) listAdapter.getGroup(i)).shouldCollapseByDefault()) {
                expandableListView.collapseGroup(i);
            } else {
                expandableListView.expandGroup(i);
            }
        }
    }
}
