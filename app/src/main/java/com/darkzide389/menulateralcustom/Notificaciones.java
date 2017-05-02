package com.darkzide389.menulateralcustom;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class Notificaciones extends MenuLateral {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_notificaciones, null, false);
        frame.addView(contentView, 0);
        lista.get(4).setIcono(R.drawable.notificaciones_verde);
        adapter.notifyDataSetChanged();
    }
}
