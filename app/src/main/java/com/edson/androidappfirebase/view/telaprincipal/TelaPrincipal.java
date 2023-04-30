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
                    usuarioMap.put("nome", "Maria");
                    usuarioMap.put("sobrenome", "Rosa");
                    usuarioMap.put("idade", 41);

                    db.collection("Usuarios").document("Maria")
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
                        Log.d("db_update", "Sucesso ao atualizar dados de usuario");
                    });

                });
                binding.btnDeletararDadosDB.setOnClickListener(view -> {
                    db.collection("Usuarios").document("Maria").delete().addOnCompleteListener(task -> {
                                Log.d("db_delete", "Sucesso ao deletar dados de usuario");
                            }
                    );
                });
            }
        }