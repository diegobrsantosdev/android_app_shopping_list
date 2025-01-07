package com.example.listadecompras

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class AdicionarItemActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adicionar_item)

        val editTextItemNome: EditText = findViewById(R.id.editTextItemNome)
        val buttonAdicionar: Button = findViewById(R.id.buttonAdicionar)

        buttonAdicionar.setOnClickListener {
            val itemNome = editTextItemNome.text.toString()
            val resultIntent = Intent()
            resultIntent.putExtra("itemNome", itemNome)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}