package com.darkzide389.menulateralcustom;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public class MiPerfil extends MenuLateral {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_mi_perfil, null, false);
        frame.addView(contentView, 0);
        lista.get(2).setIcono(R.drawable.mi_perfil_verde);
        adapter.notifyDataSetChanged();
    }
}
