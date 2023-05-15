package com.dji.sdk.sample.internal.view;

import android.content.Context;
import androidx.annotation.NonNull;

import com.dji.sdk.sample.R;
import com.dji.sdk.sample.internal.controller.DJISampleApplication;
import com.dji.sdk.sample.internal.utils.ModuleVerificationUtil;
import com.dji.sdk.sample.internal.view.BaseOwlMissionView;

import dji.common.flightcontroller.Attitude;
import dji.common.flightcontroller.FlightControllerState;
import dji.common.flightcontroller.GPSSignalLevel;
import dji.common.flightcontroller.LocationCoordinate3D;
import dji.sdk.flightcontroller.FlightController;
import dji.sdk.products.Aircraft;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class OwlMission extends BaseOwlMissionView{
    private Context mContext;
    public OwlMission(Context context){
        super(context);
        mContext = context;
    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (ModuleVerificationUtil.isFlightControllerAvailable()) {
            String fileName = ("owllog: " + Calendar.getInstance().getTime()+".txt");
            FlightController flightController = ((Aircraft) DJISampleApplication.getProductInstance()).getFlightController();
            flightController.setStateCallback(new FlightControllerState.Callback() {
                @Override
                public void onUpdate(@NonNull FlightControllerState djiFlightControllerCurrentState) {
                    Date currentTime = Calendar.getInstance().getTime();
                    GPSSignalLevel GPSLevel = djiFlightControllerCurrentState.getGPSSignalLevel();
                    LocationCoordinate3D locationCoordinate3D = djiFlightControllerCurrentState.getAircraftLocation();
                    Attitude attitude = djiFlightControllerCurrentState.getAttitude();

                    float vX = djiFlightControllerCurrentState.getVelocityX();
                    float vY = djiFlightControllerCurrentState.getVelocityY();
                    float vZ = djiFlightControllerCurrentState.getVelocityZ();
                    float vXYZ = (float) Math.sqrt((vX*vX) + (vY*vY) + (vZ*vZ));

                    owlLogger.delete(0, owlLogger.length());
                    owlLogger.append("Time: ").append("\t").
                            append(String.valueOf(currentTime)).append("\n");
                    owlLogger.append("GPSSignal: ").append("\t").
                            append(GPSLevel.toString()).append("\n");
                    owlLogger.append("relative altitude to sea level: ").append("\t").
                            append(djiFlightControllerCurrentState.getTakeoffLocationAltitude()).append("\n");
                    owlLogger.append("Altitude: ").append("\t").
                            append(locationCoordinate3D.getAltitude()).append("\n");
                    owlLogger.append("Latitude: ").append("\t").
                            append(locationCoordinate3D.getLatitude()).append("\n");
                    owlLogger.append("Longitude: ").append("\t").
                            append(locationCoordinate3D.getLongitude()).append("\n");
                    owlLogger.append("pitch: ").append("\t").
                            append(attitude.pitch).append("\n");
                    owlLogger.append("yaw: ").append("\t").
                            append(attitude.yaw).append("\n");
                    owlLogger.append("roll: ").append("\t").
                            append(attitude.roll).append("\n");
                    owlLogger.append("getVelocityX: ").append("\t").
                            append(vX).append("\n");
                    owlLogger.append("getVelocityY: ").append("\t").
                            append(vY).append("\n");
                    owlLogger.append("getVelocityZ: ").append("\t").
                            append(vZ).append("\n");
                    owlLogger.append("getVelocityXYZ: ").append("\t").append(vXYZ).append("\n");
                    writeToFile3(mContext,fileName,owlLogger);
                    showStringBufferResult();
                }
            });
        }
    }
    public void  writeToFile3(Context context, String filename, StringBuffer content) {
        String data = content.toString();
        FileOutputStream outputStream;
        try {
            outputStream = context.openFileOutput(filename, Context.MODE_APPEND);
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
        return R.string.view_owl_view;
    }
}
