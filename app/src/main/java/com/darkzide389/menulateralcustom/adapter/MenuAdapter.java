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

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private Context mContext;
    private List<MenuPOJO> lista;
    private LayoutInflater mInflater;
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
        if (position != 0) {
            holder.icono.setImageDrawable(ContextCompat.getDrawable(mContext, lista.get(position).getIcono()));
            holder.nombre.setText(lista.get(position).getNombre());
        } else {
            if (!lista.get(position).getFoto().equals("")) {
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
}
