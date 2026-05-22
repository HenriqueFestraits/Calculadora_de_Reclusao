package com.example.testecalculotempodeexclusao

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.content.Intent
import android.widget.TextView
import android.net.Uri
import android.widget.Button

class TelaResultado : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_resultado)


        val progressao = intent.getStringExtra("progressao")
        val condicional = intent.getStringExtra("condicional")

        val txtDataProgressao =
            findViewById<TextView>(R.id.txtDataProgressao)
        val txtDataCondicional =
            findViewById<TextView>(R.id.txtDataCondicional)

        txtDataProgressao.text = progressao ?: ""

        txtDataCondicional.text = condicional ?: ""


        val btnWhatsapp = findViewById<Button>(R.id.btnWhatsapp)

        btnWhatsapp.setOnClickListener {

            val numero = 5511943434495
            val mensagem = "Teste"
            val url = "https://wa.me/$numero?text=${Uri.encode(mensagem)}"

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.setPackage("com.whatsapp")
            startActivity(intent)
        }

        val btnRecalcula = findViewById<Button>(R.id.btnRecalcula)

        btnRecalcula.setOnClickListener{

            val intent2 = Intent(this, TelaCalculo::class.java)

            startActivity(intent2)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}