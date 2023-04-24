package com.dji.sdk.sample.internal.view;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.dji.sdk.sample.R;

public abstract class BaseOwlMissionView extends RelativeLayout implements PresentableView{
    protected TextView owlHello;
    protected EditText inputLat;
    protected EditText inputLon;
    protected EditText inputAlt;
    protected StringBuffer owlLogger;
    public BaseOwlMissionView(Context context){
        super(context);
        init(context);
    }
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        owlLogger = new StringBuffer();
    }

    @NonNull
    @Override
    public String getHint() {
        return this.getClass().getSimpleName() + ".java";
    }

    private void init(Context context) {
        setClickable(true);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Service.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.view_owl_mission, this, true);

        owlHello = (TextView) findViewById(R.id.owl_Hello);
        owlHello.setText(context.getString(getDescription()));

        inputLat = (EditText) findViewById(R.id.inputLat);
        inputLon = (EditText) findViewById(R.id.inputLon);
        inputAlt = (EditText) findViewById(R.id.inputAlt);

    }

    protected void showStringBufferResult() {
        post(new Runnable() {
            @Override
            public void run() {;
                owlHello.setText(owlLogger.toString());
            }
        });
    }
}
