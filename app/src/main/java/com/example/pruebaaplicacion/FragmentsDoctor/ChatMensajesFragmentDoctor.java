
package com.example.pruebaaplicacion.FragmentsDoctor;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pruebaaplicacion.Chat.Usuarioschat;
import com.example.pruebaaplicacion.Conexion;
import com.example.pruebaaplicacion.Mensajes.AdapterMensajeEnvia;
import com.example.pruebaaplicacion.Mensajes.AdapterMensajeRecibe;
import com.example.pruebaaplicacion.Mensajes.Mensajes;
import com.example.pruebaaplicacion.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;

public class ChatMensajesFragmentDoctor extends Fragment {

    Uri imagenUri;

    //inicializamos las partes para que se vean los mensajes
    RecyclerView recyclerView;
    ArrayList<Mensajes> listMensajes ;

    //mandamos a llamar los adapters
    AdapterMensajeEnvia adapterMensajeEnvia;
    AdapterMensajeRecibe adapterMensajeRecibe;


    //inicializamos las variables tipos string a la que mandaremos los datos
    TextView nombreUsuario;
    String nombreCliente ="";
    String nombreDoctor ="";
    String nombreTabla="";
    String mensaje ;
    String hora;
    String fecha;
    String tipoU;

    int TOMAR_FOTO = 100;
    int SELEC_IMAGEN = 200;

    String CARPETA_RAIZ = "MisFotosApp";
    String CARPETAS_IMAGENES = "imagenes";
    String RUTA_IMAGEN = CARPETA_RAIZ + CARPETAS_IMAGENES;
    String path;

    //creamso un CountDownTimer
    CountDownTimer count ;

    //iniciaamos un contador para verificar que no se repite los mensaje
    int contador=1;

    //iniciamos un booleano que validada si la tabla existe
    boolean existeTable = false;

    //iniciamos partes para mandar el mansaje
    EditText edtEnviarMensaje;
    FloatingActionButton botonEnviar;
    FloatingActionButton botonEnviarFoto;


    private static final int PHOTO_SEND =1;
    
    //iniciamos la clase que establece la conexion
    Conexion cn = new Conexion();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_mensajes_fragment_doctor,container,false);

        //inicializamos los componentes
        nombreUsuario = view.findViewById(R.id.nombreUsuario);
        botonEnviar =view.findViewById(R.id.botonEnviarDoctor);
        botonEnviarFoto= view.findViewById(R.id.botonEnviarFotoDoctor);
        edtEnviarMensaje = view.findViewById(R.id.mensajeDoctor);
        recyclerView = view.findViewById(R.id.chatRecyclerViewDoctor);
        listMensajes = new ArrayList<Mensajes>();


        //almacenamos la hora que se envio el mensaje
        Date anotherCurDate = new Date();
        hora = anotherCurDate.getHours()+":"+anotherCurDate.getMinutes()+":"+anotherCurDate.getSeconds();


        //almacenamos el dia que se envio el mensaje
        Time hoy = new Time(Time.getCurrentTimezone());
        hoy.setToNow();
        fecha= hoy.year+"-"+ (hoy.month + 1)+"-"+ hoy.monthDay;

        //enviamos a la variable mensaje el mensaje
        mensaje =edtEnviarMensaje.getText()+"";
        Bundle objetoChatUsuario = getArguments();
        Usuarioschat usuarioschat= null;

        //enviamos en una variable tipo de usario que soy
        tipoU = MostrarDatosUsuarioActual("tipo");



        //iniciamos un temporizador que durara 2 segundo
        count = new CountDownTimer(2000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                //al terminar el temporizador  verificara llamara los metodos para mostrar los mensajes
                cargarLista();
                mostrarDatos();

            }
        };


        if(objetoChatUsuario != null){

            usuarioschat = (Usuarioschat) objetoChatUsuario.getSerializable("doctor");

            if (tipoU.equals("doctor")){
                nombreCliente = usuarioschat.getUsuario();
                nombreDoctor = MostrarDatosUsuarioActual("usuario");
            }
            else if (tipoU.equals("usuario")){
                nombreDoctor = usuarioschat.getUsuario();
                nombreCliente = MostrarDatosUsuarioActual("usuario");
            }

            nombreTabla= nombreDoctor+nombreCliente;
            nombreUsuario.setText(usuarioschat.getNombre()+" "+usuarioschat.getApellidos());

        }



        verificarTabla();
        if (existeTable){
        cargarLista();
        mostrarDatos();}

        botonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            if (!edtEnviarMensaje.getText().toString().isEmpty()) {
                if (tipoU.equals("doctor")) {

                    if (existeTable) {
                        mandarMensaje();
                    } else {
                        crearTabla();

                        if (existeTable) {
                            mandarMensaje();
                        }
                    }
                } else {

                    if (existeTable) {
                        mandarMensaje();
                    }
                }
            }
            else {
                Toast.makeText(getContext(),"inserte un mensaje",Toast.LENGTH_SHORT).show();
            }

            }
        });

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) view.getContext(),
                    new String[]{Manifest.permission.CAMERA,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        botonEnviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opcionesFoto();

            }
        });

        return view;
    }

    //verificameos si la sala del chat existe
    public void verificarTabla(){
        try {


            Statement stm = cn.conexionBD().createStatement();
            ResultSet verificarTabla = stm.executeQuery("select * from chats where nombrechat ='"+nombreTabla+"'");

            if (verificarTabla.next()){
                existeTable = true;
            }
            else {
                existeTable=false;
            }
        }catch (SQLException e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    //enviamos los datos a la tabla de mensajes
    public void mandarMensaje(){
        try {

            Statement stm = cn.conexionBD().createStatement();


            ResultSet EnviarMensaje = stm.executeQuery("INSERT INTO " + nombreTabla + "(usuarioenvia, mensajes, hora , dia, tipo) VALUES ('"+nombreDoctor+"','"+edtEnviarMensaje.getText()+"','"+hora+"','"+fecha+"','"+1+"')");

            cargarLista();
            mostrarDatos();


            }catch (SQLException e){

        }
    }

    //creamos una tabla metiendo datos en la tabla chat si no existe
    public void crearTabla(){
        try {
            existeTable =true;
            Statement stm = cn.conexionBD().createStatement();
            ResultSet crearTabla =stm.executeQuery("INSERT INTO chats  (nombrechat, doctor,cliente) VALUES ('"+nombreTabla+"','"+nombreDoctor+"','"+nombreCliente+"')");

        }catch (SQLException e){

        }
    }

    //encargado de mostrar los datos del usuario que no usa la aplicaccion
    public String MostrarDatosUsuarioActual(String key){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("datos_usuario", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,"false");
    }

    //guardamos los datos de la base de datos a la lista de mensajes
    public void cargarLista(){
        try {
            Statement stm = cn.conexionBD().createStatement();
            ResultSet rs =stm.executeQuery("select * FROM "+nombreTabla);

            while (rs.next()){


                int id = rs.getInt("id");
                String usuarioEnvia= rs.getString("usuarioenvia");
                String mensaje= rs.getString("mensajes");
                String hora= rs.getString("hora");
                String dia= rs.getString("dia");
                String imagen = rs.getString("imagenes");
                int tipo = rs.getInt("tipo");
                SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
                Date date = new Date();
                hora = dateFormat.format(date);

                if (contador == id) {
                    listMensajes.add(new Mensajes(id, usuarioEnvia, mensaje, hora, imagen, dia,tipo));
                    contador++;
                }


            }



        }catch (SQLException e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    //mostramos los datos de la base de datos en el reviewtext
    public void mostrarDatos(){


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapterMensajeEnvia = new AdapterMensajeEnvia(getContext(), listMensajes);
        recyclerView.setAdapter(adapterMensajeEnvia);


            count.start();


    }

    public void seleccionarImagen() {
        Intent galeria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(galeria, SELEC_IMAGEN);
    }

    public void tomarFoto() {
        String nombreImagen = "";
        File fileImagen = new File(Environment.getExternalStorageDirectory(), RUTA_IMAGEN);
        boolean isCreada = fileImagen.exists();

        if(isCreada == false) {
            isCreada = fileImagen.mkdirs();
        }

        if(isCreada == true) {
            nombreImagen = (System.currentTimeMillis() / 1000) + ".jpg";
        }

        path = Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGEN+File.separator+nombreImagen;
        File imagen = new File(path);

        Intent intent = null;
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authorities = getContext().getPackageName()+".provider";
            Uri imageUri = FileProvider.getUriForFile(getContext(), authorities, imagen);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        }

        startActivityForResult(intent, TOMAR_FOTO);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == SELEC_IMAGEN) {
            imagenUri = data.getData();



            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imagenUri);


                Statement stm = cn.conexionBD().createStatement();
                ResultSet EnviarMensaje = stm.executeQuery("INSERT INTO " + nombreTabla + "(usuarioenvia, imagenes, hora , dia, tipo) VALUES ('"+nombreDoctor+"','"+imagenUri+"','"+hora+"','"+fecha+"','"+2+"')");



            }catch (SQLException e){

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(resultCode == RESULT_OK && requestCode == TOMAR_FOTO) {
            MediaScannerConnection.scanFile(getContext(), new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String s, Uri uri) {

                }
            });

            Bitmap bitmap = BitmapFactory.decodeFile(path);

            try {
                Statement stm = cn.conexionBD().createStatement();
                ResultSet EnviarMensaje = stm.executeQuery("INSERT INTO " + nombreTabla + "(imagenes,tipo) VALUES ('"+bitmap+"','2')");


            }catch (SQLException e){
                Toast.makeText(getContext(), e.getErrorCode(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void opcionesFoto(){


        new  AlertDialog.Builder(getContext())
                .setTitle("Enviar una foto")
                .setMessage("Elija una opción")
                .setPositiveButton("Elegir", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        seleccionarImagen();
                    }
                })
                .setNegativeButton("Tomar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tomarFoto();
                    }
                })
                .show();
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        if (bitmap!=null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            return stream.toByteArray();
        }
        return null;
    }


}
