package com.dji.sdk.sample.demo.gimbal;

import android.content.Context;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dji.sdk.sample.R;
import com.dji.sdk.sample.internal.controller.DJISampleApplication;
import com.dji.sdk.sample.internal.utils.ModuleVerificationUtil;
import com.dji.sdk.sample.internal.view.BasePushDataView;

import dji.common.battery.BatteryState;
import dji.common.flightcontroller.Attitude;
import dji.common.flightcontroller.FlightControllerState;
import dji.common.flightcontroller.GPSSignalLevel;
import dji.common.flightcontroller.LocationCoordinate3D;
import dji.sdk.flightcontroller.FlightController;
import dji.sdk.products.Aircraft;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class PushGimbalDataView extends BasePushDataView {
    private Context mContext;
    public PushGimbalDataView(Context context) {
        super(context);
        mContext = context;
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

                    stringBuffer.delete(0, stringBuffer.length());

                    Date currentTime = Calendar.getInstance().getTime();

                    stringBuffer.append("Time: ").
                            append(String.valueOf(currentTime)).append("\n");
                    stringBuffer.append("GPSSignal: ").
                            append(GPSLevel.toString()).append("\n");
                    stringBuffer.append("relative altitude to sea level: ").
                            append(djiFlightControllerCurrentState.getTakeoffLocationAltitude()).append("\n");

                    stringBuffer.append("Altitude: ").
                            append(locationCoordinate3D.getAltitude()).append("\n");
                    stringBuffer.append("Latitude: ").
                            append(locationCoordinate3D.getLatitude()).append("\n");
                    stringBuffer.append("Longitude: ").
                            append(locationCoordinate3D.getLongitude()).append("\n");

                    stringBuffer.append("pitch: ").
                            append(attitude.pitch).append("\n");
                    stringBuffer.append("yaw: ").
                            append(attitude.yaw).append("\n");
                    stringBuffer.append("roll: ").
                            append(attitude.roll).append("\n");

                    stringBuffer.append("getVelocityX: ").
                            append(djiFlightControllerCurrentState.getVelocityX()).append("\n");
                    stringBuffer.append("getVelocityY: ").
                            append(djiFlightControllerCurrentState.getVelocityY()).append("\n");
                    stringBuffer.append("getVelocityZ: ").
                            append(djiFlightControllerCurrentState.getVelocityZ()).append("\n");

                    //so not practical
                    Toast.makeText(mContext,"hello from file onUpdate",Toast.LENGTH_SHORT).show();

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
                    fullLog.append("___________________________________________\n");


                    writeToFile(mContext,"owlFlightLog.txt",fullLog);
                    showStringBufferResult();
                }
            });
        }
    }

    public void  writeToFile(Context context, String filename, StringBuffer content) {
        Toast.makeText(context,"hello from file writer",Toast.LENGTH_SHORT).show();
        String data = content.toString();
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
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
