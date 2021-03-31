package com.example.pruebaaplicacion.Mensajes;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.pruebaaplicacion.R;


import java.util.ArrayList;

public class AdapterMensajeEnvia extends RecyclerView.Adapter<AdapterMensajeEnvia.ViewHolder> implements View.OnClickListener{

    LayoutInflater inflater;
    ArrayList<Mensajes> listMensajes;
    RequestQueue request;
    Context context;
    private View.OnClickListener listener;


    public AdapterMensajeEnvia(Context context, ArrayList<Mensajes> listMensajes){
        this.inflater = LayoutInflater.from(context);
        this.listMensajes =listMensajes;
        request= Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.card_view_mensaje_enviado,parent,false);
        view.setOnClickListener(this);
        RecyclerView.LayoutParams layoutParams=new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(layoutParams);
        return new ViewHolder(view);

    }

    public void setonClickListener(View.OnClickListener listener){
        this.listener =listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        String mensaje = listMensajes.get(position).getMensaje();
        String hora = listMensajes.get(position).getHora();
        String imagen= listMensajes.get(position).getImagen();

        if (listMensajes.get(position).getTipo() == 2)
        {
            holder.mensajeEnviado.setVisibility(View.GONE);
            holder.imagenEnviado.setVisibility(View.VISIBLE);




            if (listMensajes.get(position).getImagen()!=null){
                //
                cargarImagenWebService(listMensajes.get(position).getImagen(),holder);
            }else{
                holder.imagenEnviado.setImageResource(R.drawable.ic_launcher_background);
            }
        }
        else if (listMensajes.get(position).getTipo() == 1){
            holder.imagenEnviado.setVisibility(View.GONE);
            holder.mensajeEnviado.setVisibility(View.VISIBLE);

        }
        holder.mensajeEnviado.setText(mensaje);
        holder.horaenviado.setText(hora);
    }

    private void cargarImagenWebService(String rutaImagen, final ViewHolder holder) {

        //String ip=context.getString(R.string.ip);

        String urlImagen=rutaImagen;
        urlImagen=urlImagen.replace(" ","%20");

        ImageRequest imageRequest=new ImageRequest(urlImagen, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                holder.imagenEnviado.setImageBitmap(response);
            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(context,"Error al cargar la imagen",Toast.LENGTH_SHORT).show();
            }
        });
        request.add(imageRequest);
        //VolleySingleton.getIntanciaVolley(context).addToRequestQueue(imageRequest);
    }

    @Override
    public int getItemCount() {
        return listMensajes.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onClick(v);
        }
    }

        public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mensajeEnviado, horaenviado ;
        ImageView imagenEnviado;



            Context context;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mensajeEnviado =itemView.findViewById(R.id.mensaje_envio);
            horaenviado =itemView.findViewById(R.id.hora_envio);
            imagenEnviado = itemView.findViewById(R.id.imagen_mensaje);
        }
    }
}
