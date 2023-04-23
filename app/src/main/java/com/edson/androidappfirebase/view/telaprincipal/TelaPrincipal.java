package com.edson.androidappfirebase.view.telaprincipal;

import com.edson.androidappfirebase.R;
import com.edson.androidappfirebase.databinding.ActivityTelaPrincipalBinding;
import com.edson.androidappfirebase.view.formCadastro.FormCadastro;
import com.edson.androidappfirebase.view.formLogin.FormLogin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.ktx.Firebase;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class TelaPrincipal extends AppCompatActivity {
private ActivityTelaPrincipalBinding binding ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityTelaPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnDeslogar.setOnClickListener(view->{
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, FormLogin.class);
            startActivity(intent);
            finish();
        });
    }
}