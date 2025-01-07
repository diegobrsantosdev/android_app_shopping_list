package com.example.listadecompras

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var listViewCompras: ListView
    private lateinit var buttonAddItem: Button
    private val comprasList = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    // Inicializando o Firebase Firestore
    private val db = FirebaseFirestore.getInstance()

    private val getResultLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val data: Intent? = result.data
            val itemNome = data?.getStringExtra("itemNome")
            if (!itemNome.isNullOrEmpty()) {
                // Salvar o item no Firestore
                val item = hashMapOf("nome" to itemNome)
                db.collection("compras").add(item)

                // Adicionar à lista localmente
                comprasList.add(itemNome)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listViewCompras = findViewById(R.id.listViewCompras)
        buttonAddItem = findViewById(R.id.buttonAddItem)

        adapter = ItemAdapter(this, R.layout.item_lista, comprasList)
        listViewCompras.adapter = adapter

        buttonAddItem.setOnClickListener {
            val intent = Intent(this, AdicionarItemActivity::class.java)
            getResultLauncher.launch(intent)
        }

        // Carregar os itens salvos no Firestore ao iniciar o app
        db.collection("compras")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val itemNome = document.getString("nome")
                    if (itemNome != null) {
                        comprasList.add(itemNome)
                    }
                }
                adapter.notifyDataSetChanged()
            }
    }
}