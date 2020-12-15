package com.example.students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    EditText etNombre, etAPaterno, etAMaterno, etEdad, etCarrera, etSemestre, etEmail;
    FloatingActionButton fabGuardar, fabListar;

    ProgressDialog progressDialog;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String updateNoControl, updateName, updateAppaterno, updateApmaterno, updateEdad, updateCarrera, updateSemestre, updateEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNombre = findViewById(R.id.etNombre);
        etAPaterno = findViewById(R.id.etAPaterno);
        etAMaterno = findViewById(R.id.etAMaterno);
        etEdad = findViewById(R.id.etEdad);
        etCarrera = findViewById(R.id.etCarrera);
        etSemestre = findViewById(R.id.etSemestre);
        etEmail = findViewById(R.id.etEmail);

        fabGuardar = findViewById(R.id.fabGuardar);
        fabListar = findViewById(R.id.fabListar);

        progressDialog = new ProgressDialog(this);

        db = FirebaseFirestore.getInstance();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Agregar registro");


        final Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            actionBar.setTitle("Actualizar Datos");

            updateNoControl = bundle.getString("updateNoControl");
            updateName = bundle.getString("updateName");
            updateAppaterno = bundle.getString("updateAppaterno");
            updateApmaterno = bundle.getString("updateApmaterno");
            updateEdad = bundle.getString("updateEdad");
            updateCarrera = bundle.getString("updateCarrera");
            updateSemestre = bundle.getString("updatesemestre");
            updateEmail = bundle.getString("updateEmail");

            etNombre.setText(updateNoControl);
            etAPaterno.setText(updateAppaterno);
            etAMaterno.setText(updateApmaterno);
            etEdad.setText(updateEdad);
            etCarrera.setText(updateCarrera);
            etSemestre.setText(updateSemestre);
            etEmail.setText(updateEmail);

        } else {
            actionBar.setTitle("Agregar");
        }


        fabGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle bundle1 = getIntent().getExtras();
                if (bundle1 != null) {
                    String noControl = updateNoControl;
                    String name = etNombre.getText().toString().trim();
                    String appaterno = etAPaterno.getText().toString().trim();
                    String apmaterno = etAMaterno.getText().toString().trim();
                    String edad = etEdad.getText().toString().trim();
                    String carrera = etCarrera.getText().toString().trim();
                    String semestre = etSemestre.getText().toString().trim();
                    String email = etEmail.getText().toString().trim();

                    actualizarDatos(noControl, name, appaterno, apmaterno, edad, carrera, semestre, email);

                } else {
                    String name = etNombre.getText().toString().trim();
                    String appaterno = etAPaterno.getText().toString().trim();
                    String apmaterno = etAMaterno.getText().toString().trim();
                    String edad = etEdad.getText().toString().trim();
                    String carrera = etCarrera.getText().toString().trim();
                    String semestre = etSemestre.getText().toString().trim();
                    String email = etEmail.getText().toString().trim();

                    cargarDatos(name, appaterno, apmaterno, edad, carrera, semestre, email);
                }
            }
        });


        fabListar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ListActivityStudent.class));
                finish();
            }
        });

    }


    private void cargarDatos(String name, String appaterno, String apmaterno, String edad, String carrera, String semestre, String email) {
        progressDialog.setTitle("Agregar datos");
        progressDialog.show();
        String noControl = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("noControl", noControl);
        doc.put("name", name);
        doc.put("appaterno", appaterno);
        doc.put("amaterno", apmaterno);
        doc.put("sexo", edad);
        doc.put("direccion", carrera);
        doc.put("facebook", semestre);
        doc.put("instagram", email);


        db.collection("Documents").document(noControl).set(doc).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Datos almacenados con éxito...", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Ha ocurrido un error..." + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void actualizarDatos(String noControl, String name, String appaterno, String apmaterno, String edad, String carrera, String semestre, String email) {
        progressDialog.setTitle("Actualizando datos a Firebase");
        progressDialog.show();

        /*
        String id = UUID.randomUUID().toString();

        Map<String, Object> doc = new HashMap<>();
        doc.put("id", id);
        doc.put("nombre", nombre);
        doc.put("apaterno", apaterno);
        doc.put("amaterno", amaterno);
        doc.put("sexo", sexo);
        doc.put("direccion", direccion);
        doc.put("facebook", facebook);
        doc.put("instagram", instagram);

         */


        db.collection("Documents")
                .document(noControl).update(
                "nombre", name,
                "appaterno", appaterno,
                "apmaterno", apmaterno,
                "edad", edad,
                "carrera", carrera,
                "semestre", semestre,
                "email", email
        )
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Actualización exitosa...", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Ha ocurrido un error..." + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}