package com.dji.sdk.sample.demo.gimbal;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.dji.sdk.sample.R;
import com.dji.sdk.sample.internal.view.PresentableView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import dji.common.flightcontroller.Attitude;
import dji.common.flightcontroller.GPSSignalLevel;

public abstract class log extends RelativeLayout implements PresentableView {
    protected StringBuffer fullLog;
    protected TextView textViewOSD;

    public log(Context context) {
        super(context);
        init(context);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        fullLog = new StringBuffer();
    }

    @NonNull
    @Override
    public String getHint() {
        return this.getClass().getSimpleName() + ".java";
    }

    private void init(Context context) {
        setClickable(true);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);

        layoutInflater.inflate(R.layout.view_show_osd, this, true);

        textViewOSD = (TextView) findViewById(R.id.fullLog);
        textViewOSD.setText(context.getString(getDescription()));

    }

    protected void  writeToFile(Context context, String filename, StringBuffer content) {
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

    protected void showStringBufferResult2() {
        post(new Runnable() {
            @Override
            public void run() {;
                textViewOSD.setText(fullLog.toString());
            }
        });
    }


}

