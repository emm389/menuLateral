package com.darkzide389.menulateralcustom;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.darkzide389.menulateralcustom.adapter.MenuAdapter;
import com.darkzide389.menulateralcustom.model.MenuPOJO;

import java.util.ArrayList;

public class MenuLateral extends AppCompatActivity {

    // nuestro FrameLayout lo pusimos protected para poder accesar a ella
    // desde las clases donde hayamos extendido sin necesidad de hacer varias
    // instancias de nuestro FrameLayout
    protected FrameLayout frame;
    private RecyclerView mDrawerList;
    private DrawerLayout drawer;
    protected ArrayList<MenuPOJO> lista;
    protected MenuAdapter adapter;
    protected MenuPOJO profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_lateral);
        /*
            Para poder usar esta toolbar debes ir al archivo styles en la carpeta values
            y cambiar la linea de codigo <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
            por <style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
            solo basta con cambiar la parte de DarkActionBar por NoActionBar
         */
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Esta parte es para darle el efecto de abrir y cerrar el menu
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // Esta parte es para ponerle sombra abajo del menu cuando se abre
        drawer.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);

        // Creamos un array para agregar opciones al menu
        lista = new ArrayList<>();

        /*
            Creamo un pojo llamado MenuPOJO y creamos varios objetos segun las opciones que
            lleve el menu, el primer objeto sera para la foto y el nombre del usuario,
            posteriormente podemos hacer un web service para obtener el nombre y la foto o
            guardar la info del usuario en sharedpreferences
            NOTA: donde dice profile.setTipo(0) es para saber que tipo es, por ejemplo
            en este caso el 0 es de la info del usuario, el tipo 1 es para las opciones del menu
            y la opcion 2 es para cerrar sesion, en el adaptador veremos el porque de esto.
         */
        profile = new MenuPOJO();
        profile.setIcono(0);
        profile.setNombre("Hackerman");
        profile.setFoto("https://pbs.twimg.com/profile_images/700753841495486464/aJgO79C9.png");
        profile.setTipo(0);
        lista.add(profile);

        // Creamos objetos segun el numero de opciones de nuestro menu y las vamos agregando
        // a nuestro array de objetos
        MenuPOJO principal = new MenuPOJO();
        principal.setIcono(R.drawable.mapa_gris);
        principal.setNombre("Mapa");
        principal.setTipo(1);
        lista.add(principal);

        MenuPOJO perfil = new MenuPOJO();
        perfil.setIcono(R.drawable.mi_perfil_gris);
        perfil.setNombre("Mi perfil");
        perfil.setTipo(1);
        lista.add(perfil);

        MenuPOJO cupones = new MenuPOJO();
        cupones.setIcono(R.drawable.cupones_gris);
        cupones.setNombre("Mis cupones");
        cupones.setTipo(1);
        lista.add(cupones);

        MenuPOJO notificaciones = new MenuPOJO();
        notificaciones.setIcono(R.drawable.notificaciones_gris);
        notificaciones.setNombre("Notificaciones");
        notificaciones.setTipo(1);
        lista.add(notificaciones);

        MenuPOJO cerrarSesion = new MenuPOJO();
        cerrarSesion.setIcono(R.drawable.cerrar_sesion_gris);
        cerrarSesion.setNombre("Cerrar sesión");
        cerrarSesion.setTipo(2);
        lista.add(cerrarSesion);

        // Creamos una instancia de nuestro adaptador, le pasamos el contexto de la activity
        // y nuestro array de objetos
        adapter = new MenuAdapter(MenuLateral.this, lista);
        // Obtenemos la referencia de nuestro RecyclerView
        mDrawerList = (RecyclerView) findViewById(R.id.listaMenu);
        // NOTA: si no le agregamos un LayoutManager al recyclerview no funcionara,
        // En cualquier caso que se necesite usar un recyclerview tenemos que crear un
        // LayoutManager
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(MenuLateral.this, 1);
        mDrawerList.setLayoutManager(mLayoutManager);
        mDrawerList.setItemAnimator(new DefaultItemAnimator());
        mDrawerList.setAdapter(adapter);

        // El metodo addOnItemTouchListener no lo trae por default el RecyclerView, asi que
        // se hizo uno personalizado, en el adaptador se explica, regularmente cuando se ocupe
        // obtener el evento de click en un elemento del recyclerview solo es copiar el codigo
        // que viene en el adaptador y listo
        mDrawerList.addOnItemTouchListener(new MenuAdapter.RecyclerTouchListener(MenuLateral.this, mDrawerList, new MenuAdapter.ClickListener() {
            @Override
            public void onClick(View view, final int position) {
                // Con un switch sabemos que boton se ha presionado en el menu
                // y asi direccionar al usuario a la activity deseada
                switch (position){
                    case 1:
                        lista.get(position).setIcono(R.drawable.mapa_verde);
                        mDrawerList.getAdapter().notifyDataSetChanged();
                        Intent mapa = new Intent(MenuLateral.this, MainActivity.class);
                        mapa.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mapa);
                        finish();
                        break;

                    case 2:
                        lista.get(position).setIcono(R.drawable.mi_perfil_verde);
                        mDrawerList.getAdapter().notifyDataSetChanged();
                        Intent perfil = new Intent(MenuLateral.this, MiPerfil.class);
                        perfil.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(perfil);
                        finish();
                        break;
                    case 3:
                        lista.get(position).setIcono(R.drawable.cupones_verde);
                        mDrawerList.getAdapter().notifyDataSetChanged();
                        Intent cupones = new Intent(MenuLateral.this, Cupones.class);
                        cupones.putExtra("opcion", 0);
                        cupones.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cupones);
                        finish();
                        break;
                    case 4:
                        lista.get(position).setIcono(R.drawable.notificaciones_verde);
                        mDrawerList.getAdapter().notifyDataSetChanged();
                        Intent notificaciones = new Intent(MenuLateral.this, Notificaciones.class);
                        notificaciones.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(notificaciones);
                        finish();
                        break;
                    case 5:
                        lista.get(position).setIcono(R.drawable.cerrar_sesion_verde);
                        mDrawerList.getAdapter().notifyDataSetChanged();

                        break;

                }

                drawer.closeDrawer(mDrawerList);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        // En esta linea se le setea el color de fondo, aqui se puede cambiar al gusto
        mDrawerList.setBackgroundResource(R.drawable.gradient_drawer_background_shop);
        // Aqui es donde decidimos que ancho tendra el menu, por default esta en 130dp
        mDrawerList.getLayoutParams().width = (int) getResources()
                .getDimension(R.dimen.drawer_width_shop);

        // Obtenemos la referencia de nuestro FrameLayout
        frame = (FrameLayout)findViewById(R.id.frameVistas);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
