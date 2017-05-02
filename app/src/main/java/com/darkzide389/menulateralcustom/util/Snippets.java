package com.darkzide389.menulateralcustom.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Snippets {

    private static ProgressDialog pDialog;
    private static int hour, min;

    public static void showMessage(Context context, String title, String message, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);
        builderSingle.setCancelable(false);
        builderSingle.setPositiveButton("Aceptar", listener);
        builderSingle.show();
    }

    public static void showMessageWithCancel(Context context, String title, String message, DialogInterface.OnClickListener listener, DialogInterface.OnClickListener listenerCancel) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(context);
        builderSingle.setTitle(title);
        builderSingle.setMessage(message);
        builderSingle.setCancelable(false);
        builderSingle.setPositiveButton("Aceptar", listener);
        builderSingle.setNegativeButton("Cancelar", listenerCancel);
        builderSingle.show();
    }

    public static void createCustomDialog(Context context, int layoutId, ViewGroup root, String titulo, DialogInterface.OnClickListener positiveButton, DialogInterface.OnClickListener negativeButton) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(layoutId, root, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(layout);
        builder.setTitle(titulo);
        builder.setPositiveButton("Aceptar", positiveButton);
        builder.setNegativeButton("Cancelar", negativeButton);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showProgressDialog(Context context, String message){
        pDialog = new ProgressDialog(context);
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.setMessage(message);
        pDialog.show();
    }

    public static void hideProgressDialog(){
        if (pDialog!=null){
            pDialog.dismiss();
        }
    }

    public static String MD5(String md5) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param inputStream objeto de tipo InputStream a convertir
     * @return un String con el contenido recibido del servidor
     * @throws IOException
     */
    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"), 8);
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    /**
     *
     * @param v metodo para expandir un layout  OJO: UTILIZAR SOLO MATCH_PARENT O WRAP_CONTENT EN LAS MEDIDAS,
     *          UTILIZAR MARGENES O PADDING PARA ASPECTO
     * @param duration duracion de la animacion
     */
    public static void expand(final View v, int duration) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration(((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density)) * duration);
        v.startAnimation(a);
    }

    /**
     *
     * @param v metodo para colapsar un layout
     * @param duration duracion de la animacion
     *
     */
    public static void collapse(final View v, int duration) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration(((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density)) * duration);
        v.startAnimation(a);
    }

    public static boolean isHTML(String result) {
        Pattern htmlPattern = Pattern.compile(".*\\<[^>]+>.*", Pattern.DOTALL);
        return htmlPattern.matcher(result).matches();
    }

    /**
     *
     * @param mes numero de mes
     * @return retorna un String con el nombre del mes correspondiente
     */
    public static String getMes(int mes) {
        switch (mes) {
            case 1:
                return "ENERO";
            case 2:
                return "FEBRERO";
            case 3:
                return "MARZO";
            case 4:
                return "ABRIL";
            case 5:
                return "MAYO";
            case 6:
                return "JUNIO";
            case 7:
                return "JULIO";
            case 8:
                return "AGOSTO";
            case 9:
                return "SEPTIEMBRE";
            case 10:
                return "OCTUBRE";
            case 11:
                return "NOVIEMBRE";
            case 12:
                return "DICIEMBRE";
        }
        return "Sin mes";
    }

    /**
     *
     * @param fechaActual fecha actual del dispositivo o fecha mas a futuro
     * @param fecha2 fecha pasada o actual para hacer la resta
     * @return retorna un entero con el total de dias de diferencia entre una fecha y otra
     */
    public static int getDaysOfDifference(String fechaActual, String fecha2) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");

        int diasDif = 0;

        try {
            Date date1 = myFormat.parse(fechaActual);
            Date date2 = myFormat.parse(fecha2);
            long diff = date2.getTime() - date1.getTime();
            diasDif = (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return diasDif;
    }

    /**
     *  Este metodo sirve para cortar en forma circular las imagenes
     * @param bitmap la imagen a recortar
     * @return La imagen ya recortada en circulo
     */
    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return Bitmap.createScaledBitmap(output, 140, 140, false);
        //return output;
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static String getFechaFormateada(String fecha) {
        String nombreMes = "";
        String[] splitFecha = fecha.split("-");

        switch (splitFecha[1]) {
            case "01":
                nombreMes = "enero";
                break;
            case "02":
                nombreMes = "febrero";
                break;
            case "03":
                nombreMes = "marzo";
                break;
            case "04":
                nombreMes = "abril";
                break;
            case "05":
                nombreMes = "mayo";
                break;
            case "06":
                nombreMes = "junio";
                break;
            case "07":
                nombreMes = "julio";
                break;
            case "08":
                nombreMes = "agosto";
                break;
            case "09":
                nombreMes = "septiembre";
                break;
            case "10":
                nombreMes = "octubre";
                break;
            case "11":
                nombreMes = "noviembre";
                break;
            case "12":
                nombreMes = "diciembre";
                break;
        }

        return splitFecha[2] + " de " + nombreMes + " de " + splitFecha[0];
    }

    public static boolean esEmailCorrecto(String email) {
        boolean valido = false;

        // Patrón para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);

        if (mather.find()) {
            valido = true;
        }
        return valido;
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    /*public static String getNombrePais (String code){
        try {
            JSONArray countryArray = new_place JSONArray(Paises.LISTA_PAISES);
            HashMap<String, String> listaPaises = new_place HashMap<>();

            for (int i = 0; i < countryArray.length(); i++) {
                JSONObject jsonObject = countryArray.getJSONObject(i);
                String countryName = jsonObject.getString("name");
                String countryCode = jsonObject.getString("code");

                listaPaises.put(countryCode, countryName);
            }

            if (listaPaises.containsKey(code)){
                return listaPaises.get(code);
            } else {
                return "Sin país";
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    } */

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    public static String getGenderConverted(String gender){
        switch (gender){
            case "m":
                return "Masculino";
            case "f":
                return "Femenino";
            case "o":
                return "Otro";
        }

        return "";
    }

    public static String getGenderConvertedReloaded(int gender){
        switch (gender){
            case 1:
                return "Hombre";
            case 2:
                return "Mujer";
            case 3:
                return "Otro";
            case 4:
                return "Hombre y mujer";
            case 5:
                return "Mujer y otro";
            case 6:
                return "Hombre y otro";
            case 7:
                return "Todos";
        }

        return "";
    }

    public static void getTime(Context context, final TextView v){
        TimePickerDialog Tp = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hora, minutos;
                if (hourOfDay < 10){
                    hora = "0" + hourOfDay;
                } else {
                    hora = String.valueOf(hourOfDay);
                }

                if (minute < 10){
                    minutos = "0" + minute;
                } else {
                    minutos = String.valueOf(minute);
                }
                v.setText(hora + ":" + minutos);
            }
        }, hour, min, false);

        Tp.show();
    }

    public static void imageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }
}
