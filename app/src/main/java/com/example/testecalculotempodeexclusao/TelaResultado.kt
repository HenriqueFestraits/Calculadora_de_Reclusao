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

        //recebe os valores do cálculo da tela anterior
        val progressao = intent.getStringExtra("progressao")
        val condicional = intent.getStringExtra("condicional")

        //conecta a variavel com o TextView
        val txtDataProgressao =
            findViewById<TextView>(R.id.txtDataProgressao)
        val txtDataCondicional =
            findViewById<TextView>(R.id.txtDataCondicional)

        
        //passa os valores para o TextView
        txtDataProgressao.text = progressao ?: ""

        txtDataCondicional.text = condicional ?: ""

        //recebe o pressionar do botão de contato
        val btnWhatsapp = findViewById<Button>(R.id.btnWhatsapp)

        //quando o botão de entrar em contato com um advogado é pressionado
        btnWhatsapp.setOnClickListener {

            val numero = 5511943434495
            val mensagem = "Teste"
            val url = "https://wa.me/$numero?text=${Uri.encode(mensagem)}"


            //conectando com a url do whatsapp
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            intent.setPackage("com.whatsapp")
            //entra na conversa com o advogado
            startActivity(intent)
        }

        //recebe o pressionar do botão de recalcular
        val btnRecalcula = findViewById<Button>(R.id.btnRecalcula)

        //quando o botão de recalcular é pressionado
        btnRecalcula.setOnClickListener{

            //conetca com a tela de recalculo
            val intent2 = Intent(this, TelaCalculo::class.java)
            //inicia a nova tela
            startActivity(intent2)
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}