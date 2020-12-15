package com.example.students;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CustomAdapter extends RecyclerView.Adapter<ViewHolder> {

    ListActivityStudent listActivityStudent;
    List<StudentModel> mstudentModelList;

    public CustomAdapter(ListActivityStudent listActivity, List<StudentModel> studentModelList) {
        this.listActivityStudent = listActivity;
        this.mstudentModelList = studentModelList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.model, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        viewHolder.setOnClickListener(new ViewHolder.ClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String name = mstudentModelList.get(position).getName();
                String apPaterno = mstudentModelList.get(position).getApPaterno();
                String apMaterno = mstudentModelList.get(position).getApMaterno();
                Toast.makeText(listActivityStudent, name + " " + apPaterno + " " + apMaterno, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(listActivityStudent);
                String[] options = {"Actualizar datos", "Eliminar"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            String noControl = mstudentModelList.get(position).getNoControl();
                            String name = mstudentModelList.get(position).getName();
                            String apPaterno = mstudentModelList.get(position).getApPaterno();
                            String apMaterno = mstudentModelList.get(position).getApMaterno();
                            String edad = mstudentModelList.get(position).getEdad();
                            String carrera = mstudentModelList.get(position).getCarrera();
                            String semestre = mstudentModelList.get(position).getSemestre();
                            String email = mstudentModelList.get(position).getEmail();

                            Intent actualizarDatos = new Intent(listActivityStudent, MainActivity.class);
                            actualizarDatos.putExtra("updateNoControl", noControl);
                            actualizarDatos.putExtra("updateName", name);
                            actualizarDatos.putExtra("updateAppaterno", apPaterno);
                            actualizarDatos.putExtra("updateApmaterno", apMaterno);
                            actualizarDatos.putExtra("updateEdad", edad);
                            actualizarDatos.putExtra("updateCarrera", carrera);
                            actualizarDatos.putExtra("updateSemestre", semestre);
                            actualizarDatos.putExtra("updateEmail", email);

                            listActivityStudent.startActivity(actualizarDatos);
                            //
                        }

                        if (which == 1) {
                            listActivityStudent.eliminarRegistro(position);
                        }
                    }
                }).create().show();
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvNombre.setText(
                mstudentModelList.get(i).getName()
                        + " " + mstudentModelList.get(i).getApPaterno()
                        + " " + mstudentModelList.get(i).getApMaterno()
        );
    }

    @Override
    public int getItemCount() {
        return mstudentModelList.size();
    }
}
