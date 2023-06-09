package com.dji.sdk.sample.demo.gimbal;

import android.content.Context;
import androidx.annotation.NonNull;

import com.dji.sdk.sample.R;
import com.dji.sdk.sample.internal.controller.DJISampleApplication;
import com.dji.sdk.sample.internal.utils.ModuleVerificationUtil;
import com.dji.sdk.sample.internal.view.BasePushDataView;

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
                    stringBuffer.delete(0, stringBuffer.length());
                    stringBuffer.append("Time: ").
                            append(String.valueOf(currentTime)).append("\n");
                    stringBuffer.append("GPSSignal: ").
                            append(GPSLevel.toString()).append("\n");
                    stringBuffer.append("relative altitude to sea level: ").
                            append(djiFlightControllerCurrentState.getTakeoffLocationAltitude()+locationCoordinate3D.getAltitude()).append("m\n");
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
                            append(vX).append("\n");
                    stringBuffer.append("getVelocityY: ").
                            append(vY).append("\n");
                    stringBuffer.append("getVelocityZ: ").
                            append(vZ).append("\n");
                    stringBuffer.append("getVelocityXYZ ").
                            append(vXYZ).append("\n");

                    writeToFile(mContext,fileName,stringBuffer);
                    showStringBufferResult();
                }
            });
        }
    }

    public void  writeToFile(Context context, String filename, StringBuffer content) {
       // Toast.makeText(context,"hello from file writer",Toast.LENGTH_SHORT).show();
        String data = content.toString();
        FileOutputStream outputStream;
        try {
            //FileOutputStream tmp = new FileOutputStream("testFile",false);
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
        return R.string.gimbal_listview_push_info;
    }
}
