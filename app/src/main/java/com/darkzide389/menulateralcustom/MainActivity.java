package com.darkzide389.menulateralcustom;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

// Cada vez que queramos utilizar el menu simplemente lo extendemos y ponemos las lineas
// de codigo de abajo
public class MainActivity extends MenuLateral {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Reemplazamos la linea setContentView(R.layout.activity_main); por el fragmento de
        // codigo de abajo.

        // NOTA: Cada vez que quieras implementar el menu lateral debes incluir este codigo

        // Creamos una instancia de LayoutInflater para inflar nuestro xml de la clase
        // en este caso es activity_main
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        /*
            Obtenemos toda la vista del xml para poder accesar a los id's
         */
        View contentView = inflater.inflate(R.layout.activity_main, null, false);
        // Una vez que tenemos nuestra vista en la variable contentView se la pasamos a
        // nuestro FrameLayout para mostrarla cuando presionamos el boton correspondiente
        // de esta activity en el menu
        frame.addView(contentView, 0);
        // Esta linea es para cambiarle el color al icono del menu para que aparezca seleccionado
        lista.get(1).setIcono(R.drawable.mapa_verde);
        // Le avisamos a nuestro adaptador de que la vista cambio para que se muestre el icono verde
        adapter.notifyDataSetChanged();


        //********** DE AQUI PARA ABAJO VA LO DEMAS DE LA ACTIVITY (OBTENER REFERENCIAS, HACER WEBSERCICES, ETC.)
    }
}
