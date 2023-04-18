package com.dji.sdk.sample.demo.gimbal;

import android.content.Context;

import androidx.annotation.NonNull;

import com.dji.sdk.sample.R;
import com.dji.sdk.sample.internal.controller.DJISampleApplication;
//import com.dji.sdk.sample.internal.view.BasePushDataView;
import com.dji.sdk.sample.demo.gimbal.log;
import com.dji.sdk.sample.internal.utils.ModuleVerificationUtil;
import dji.common.error.DJIError;
import dji.common.flightcontroller.Attitude;
import dji.common.flightcontroller.FlightControllerState;
import dji.common.flightcontroller.GPSSignalLevel;
import dji.common.flightcontroller.LocationCoordinate3D;
import dji.common.gimbal.Rotation;
import dji.common.gimbal.RotationMode;
import dji.common.util.CommonCallbacks;
import dji.sdk.flightcontroller.FlightController;
import dji.sdk.products.Aircraft;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class for moving gimbal with speed.
 */
public class MoveGimbalWithSpeedView extends log {
    public MoveGimbalWithSpeedView(Context context) {
        super(context);
    }
    @Override
    protected void onAttachedToWindow() {

        super.onAttachedToWindow();
        if (ModuleVerificationUtil.isFlightControllerAvailable()) {
            FlightController flightController =
                    ((Aircraft) DJISampleApplication.getProductInstance()).getFlightController();


            flightController.setStateCallback(new FlightControllerState.Callback() {
                @Override
                public void onUpdate(@NonNull FlightControllerState djiFlightControllerCurrentState) {
                    LocationCoordinate3D locationCoordinate3D = djiFlightControllerCurrentState.getAircraftLocation();
                    GPSSignalLevel GPSLevel = djiFlightControllerCurrentState.getGPSSignalLevel();
                    Attitude attitude = djiFlightControllerCurrentState.getAttitude();

                    Date currentTime = Calendar.getInstance().getTime();

                    //so not practical

                    fullLog.append("Time: ").
                            append(String.valueOf(currentTime)).append("\n");
                    fullLog.append("GPSSignal: ").
                            append(GPSLevel.toString()).append("\n");
                    fullLog.append("relative altitude to sea level: ").
                            append(djiFlightControllerCurrentState.getTakeoffLocationAltitude()).append("\n");

                    fullLog.append("Altitude: ").
                            append(locationCoordinate3D.getAltitude()).append("\n");
                    fullLog.append("Latitude: ").
                            append(locationCoordinate3D.getLatitude()).append("\n");
                    fullLog.append("Longitude: ").
                            append(locationCoordinate3D.getLongitude()).append("\n");

                    fullLog.append("pitch: ").
                            append(attitude.pitch).append("\n");
                    fullLog.append("yaw: ").
                            append(attitude.yaw).append("\n");
                    fullLog.append("roll: ").
                            append(attitude.roll).append("\n");

                    fullLog.append("getVelocityX: ").
                            append(djiFlightControllerCurrentState.getVelocityX()).append("\n");
                    fullLog.append("getVelocityY: ").
                            append(djiFlightControllerCurrentState.getVelocityY()).append("\n");
                    fullLog.append("getVelocityZ: ").
                            append(djiFlightControllerCurrentState.getVelocityZ()).append("\n");
                    fullLog.append("___________________________________________");

                    showStringBufferResult2();
                }
            });
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (ModuleVerificationUtil.isFlightControllerAvailable()) {
            ((Aircraft) DJISampleApplication.getProductInstance()).getFlightController().setStateCallback(null);
        }
    }

    @Override
    public int getDescription() {
        return R.string.gimbal_listview_push_info;
    }
}
