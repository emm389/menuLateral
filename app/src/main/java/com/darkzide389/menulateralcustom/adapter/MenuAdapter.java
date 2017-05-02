package com.darkzide389.menulateralcustom.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.darkzide389.menulateralcustom.R;
import com.darkzide389.menulateralcustom.model.MenuPOJO;

import java.util.List;

/**
 * Created by Emmanuel.
 */
/*
    Este adaptador solo lo copian a su carpeta adapter y copian los archivos item_profile,
    item_menu e item_menu_logout en la carpeta layout
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private Context mContext;
    private List<MenuPOJO> lista;
    private LayoutInflater mInflater;

    /*
        Estas constantes son los tipos de vistas que tenemos, en la clase MenuLateral
        se explico brevemente para que servian
     */
    private static final int ITEM_TYPE_PROFILE = 0;
    private static final int ITEM_TYPE_MENU = 1;
    private static final int ITEM_TYPE_LOGOUT = 2;

    public MenuAdapter(Context context, List<MenuPOJO> lista) {
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.lista = lista;
        mContext = context;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = null;
        // Mediante un switch obtenemos el tipo de vista que se debe inflar segun el tipo
        // que seteamos en nuestros objetos MenuPOJO
        switch (viewType) {
            case ITEM_TYPE_PROFILE:
                item = mInflater.inflate(R.layout.item_profile, parent, false);
                break;
            case ITEM_TYPE_MENU:
                item = mInflater.inflate(R.layout.item_menu, parent, false);
                break;
            case ITEM_TYPE_LOGOUT:
                item = mInflater.inflate(R.layout.item_menu_logout, parent, false);
                break;
        }
        return new MenuAdapter.MenuViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final MenuViewHolder holder, int position) {
        /*
            Esta validacion de la posicion es por lo de los tipos de vista que se han inflado,
            En la posicion 0 tenemos los datos del usuario (foto y nombre) y por lo tanto tiene una
            estructura distinta al resto del menu, en las demas posiciones tenemos las opciones del
            menu, que basicamente tienen la misma estructura
            NOTA: sin esta validacion nuestra aplicacion se cerraria inesperadamente debido a las distintas
            estructuras entre la vista de los datos del usuario y los botones del menu, nos daria un
            NullPointerException porque en la posicion 0 estan foto y nombre, pero en la posicion 1 en adelante
            ya no se encuentran esos elementos, sino icono y nombre del boton
         */
        if (position != 0) {
            holder.icono.setImageDrawable(ContextCompat.getDrawable(mContext, lista.get(position).getIcono()));
            holder.nombre.setText(lista.get(position).getNombre());
        } else {
            // Esta validacion es para poner una imagen por default si no hay ninguna foto en el servidor
            if (!lista.get(position).getFoto().equals("")) {
                // Esta estructura de Glide es para mostrar un progressBar mientras la imagen
                // es descargada de internet
                Glide.with(mContext).load(lista.get(position).getFoto()).crossFade().listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).thumbnail(0.5f).override(200, 200).into(holder.fotoPerfil);
            } else {
                holder.progressBar.setVisibility(View.GONE);
                holder.fotoPerfil.setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.foto_perfil_default));
            }
            holder.nombre.setText(lista.get(position).getNombre());
        }
    }

    // Este metodo es importante para saber que tipo de vista inflar,
    // vemos que obtenemos el tipo del array de objetos echo en la clase MenuLateral
    // donde se hizo la observacion del tipo
    @Override
    public int getItemViewType(int position) {
        switch (lista.get(position).getTipo()){
            case 0:
                return ITEM_TYPE_PROFILE;
            case 1:
                return ITEM_TYPE_MENU;
            case 2:
                return ITEM_TYPE_LOGOUT;
            default:
                return position;
        }
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    // Esta clase es para declarar y obtener las referencias de los 3 xml (item_profile, item_menu
    // e item_menu_logout) para su posterior uso
    // NOTA: Todos los recyclerview y listview usan una clase Holder para esto, pero solo en el
    // recyclerview se extiende de RecylerView.ViewHolder
    class MenuViewHolder extends RecyclerView.ViewHolder{
        ImageView icono;
        ImageView fotoPerfil;
        TextView nombre;
        View linea;
        ProgressBar progressBar;

        MenuViewHolder(View itemView) {
            super(itemView);
            linea = itemView.findViewById(R.id.linea);
            icono = (ImageView)itemView.findViewById(R.id.icono);
            fotoPerfil = (ImageView)itemView.findViewById(R.id.imagen_perfil);
            nombre = (TextView)itemView.findViewById(R.id.nombre);
            progressBar = (ProgressBar)itemView.findViewById(R.id.progressBar);
        }
    }

    // Esta implementacion es para darle el evento de clickListener al recyclerview, ya que
    // nativamente no lo tiene, este bloque lo pueden reutilizar en todos sus proyectos donde
    // lo necesiten
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private MenuAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final MenuAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    //************** FIN IMPLEMENTACION DE EVENTO CLICKLISTENER EN RECYCLERVIEW **************
}
