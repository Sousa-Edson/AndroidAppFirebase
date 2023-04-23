package com.edson.androidappfirebase.view.telaprincipal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.edson.androidappfirebase.databinding.ActivityTelaPrincipalBinding;
import com.edson.androidappfirebase.view.formLogin.FormLogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TelaPrincipal extends AppCompatActivity {
    private ActivityTelaPrincipalBinding binding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTelaPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnDeslogar.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, FormLogin.class);
            startActivity(intent);
            finish();
        });

        binding.btnGravarDadosDB.setOnClickListener(view -> {
            Map<String, Object> usuarioMap = new HashMap<>();
            usuarioMap.put("nome", "Edson");
            usuarioMap.put("sobrenome", "Sousa");
            usuarioMap.put("idade", 37);

            db.collection("Usuarios").document("Edson")
                    .set(usuarioMap).addOnCompleteListener(task -> {
                        Log.d("db", "Sucesso ao salvar dados de usuario");
                    }).addOnFailureListener(exception -> {
                    });
        });

        binding.btnLerDadosDB.setOnClickListener(view -> {
            db.collection("Usuarios").document("Maria").addSnapshotListener((documento, error) -> {
                if (documento != null) {
                    Long idade = documento.getLong("idade");
                    binding.txtResultadoNome.setText(documento.getString("nome"));
                    binding.txtResultadoSobrenome.setText(documento.getString("sobrenome"));
                    binding.txtResultadoIdade.setText(idade.toString());
                }
            });
        });
        binding.btnAtualizarDadosDB.setOnClickListener(view -> {
            db.collection("Usuarios").document("Maria").update("sobrenome", "mudado").addOnCompleteListener(task -> {
                Log.d("db", "Sucesso ao atualizar dados de usuario");
            });

        });
    }
}