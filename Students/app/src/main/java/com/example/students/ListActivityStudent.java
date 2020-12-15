package com.example.students;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import android.app.ListActivity;

import java.util.List;

import com.example.students.CustomAdapter;

public class ListActivityStudent extends AppCompatActivity {

    java.util.List<StudentModel> mStudentModelList = new ArrayList<>();
    RecyclerView recyclerview;

    //Crear instancia de FirebaseFirestore
    FirebaseFirestore db;

    CustomAdapter customAdapter;
    RecyclerView.LayoutManager layoutManager;
    ProgressDialog progressDialog;
    Context context;
    FloatingActionButton fabAgregar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ver registros");

        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layoutManager);
        fabAgregar = findViewById(R.id.fabAgregar);

        // Obtener instancia de Firebase
        db = FirebaseFirestore.getInstance();

        verDatos();

        fabAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListActivityStudent.this, MainActivity.class));
                finish();
            }
        });
    }

    public void eliminarRegistro(int index) {
        db.collection("Documents").document(mStudentModelList.get(index).getNoControl())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ListActivityStudent.this, "Registro eliminado", Toast.LENGTH_SHORT).show();
                        verDatos();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ListActivityStudent.this, "No se ha completado la operación" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void verDatos() {
        db.collection("Documents")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        mStudentModelList.clear();
                        for (DocumentSnapshot doc : task.getResult()) {
                            StudentModel studentModel = new StudentModel(
                                    doc.getString("noControl"),
                                    doc.getString("name"),
                                    doc.getString("appaterno"),
                                    doc.getString("apmaterno"),
                                    doc.getString("edad"),
                                    doc.getString("carrera"),
                                    doc.getString("semestre"),
                                    doc.getString("email")
                            );

                            mStudentModelList.add(studentModel);
                        }
                        customAdapter = new CustomAdapter(ListActivityStudent.this, mStudentModelList);
                        recyclerview.setAdapter(customAdapter);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ListActivityStudent.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}