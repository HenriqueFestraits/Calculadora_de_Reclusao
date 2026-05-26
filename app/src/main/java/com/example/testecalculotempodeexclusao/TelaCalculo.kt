package com.example.testecalculotempodeexclusao

import android.icu.util.Calendar
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate
import android.widget.EditText
import android.app.DatePickerDialog
import android.widget.CheckBox
import android.widget.Button
import android.content.Intent
import java.time.format.DateTimeFormatter

class TelaCalculo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tela_calculo)

        //conecta com a caixa de texto da data
        val txtCalendar = findViewById<EditText>(R.id.textCalendar)

        //variáveis da data
        var diaSelecionado = 0
        var mesSelecionado = 0
        var anoSelecionado = 0

        //quando a caixa de texto da data é pressionado
        txtCalendar.setOnClickListener{

            //recebe a data do calendáro
            val calendario = Calendar.getInstance()

            //recebe o dia, mes e ano selecionados no calendário
            val anoInicio = calendario.get(Calendar.YEAR)
            val mesInicio = calendario.get(Calendar.MONTH)
            val diaInicio = calendario.get(Calendar.DAY_OF_MONTH)


            //inicia o calendário
            val datePicker = DatePickerDialog(
                this,
                { _, year, month, dayOfMonth ->

                    diaSelecionado = dayOfMonth
                    mesSelecionado = month + 1
                    anoSelecionado = year


                    txtCalendar.setText(
                        "$diaSelecionado/$mesSelecionado/$anoSelecionado"
                    )

                },
                anoInicio,
                mesInicio,
                diaInicio
            )

            datePicker.show()


        }

        //recebe o tempo de pena concebido dos campos
        val txtAno = findViewById<EditText>(R.id.txtAno)
        val txtMes = findViewById<EditText>(R.id.txtMes)
        val txtDia = findViewById<EditText>(R.id.txtDia)

        //recebe o tipo de pena das checkboxs
        val checkBoxReu = findViewById<CheckBox>(R.id.checkBoxReu)
        val checkBoxHediondo = findViewById<CheckBox>(R.id.checkBoxHediondo)

        //recebe o pressionar do botão de enviar
        val btnEnviar = findViewById<Button>(R.id.btnEnviar)

        //quando o botão de enviar é pressionado
        btnEnviar.setOnClickListener {

            if(anoSelecionado < 1970){//validação de data
                txtCalendar.error = "Selecione uma data válida"
                return@setOnClickListener
            }

            //passando os valores para as variaveis
            val anosPenaTotal = txtAno.text.toString().toDouble()
            val mesesPenaTotal = txtMes.text.toString().toDouble()
            val diasPenaTotal = txtDia.text.toString().toDouble()
            val isPrimario = checkBoxReu.isChecked
            val isHediondo = checkBoxHediondo.isChecked

            //chamada da função que calcula o tempo de reclusão restante
            val resultado = calculaTempoExclusao(anosPenaTotal, mesesPenaTotal, diasPenaTotal,
                anoSelecionado, mesSelecionado, diaSelecionado,
                isHediondo, isPrimario)

            //passando os valores para novas variaveis
            val progressao = resultado.first
            val condicional = resultado.second

            //conectando com nova tela
            val intent = Intent(this, TelaResultado::class.java)
            //enviando os valores para a próxima tela
            intent.putExtra("progressao", progressao)
            intent.putExtra("condicional", condicional)
            //começando a nova tela
            startActivity(intent)
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    fun calculaTempoExclusao(anosPenaTotal: Double, mesesPenaTotal: Double, diasPenaTotal: Double,
                             anoInicio: Int, mesInicio: Int, diaInicio: Int, isHediondo:Boolean,
                             isPrimario: Boolean): Pair<String, String> {

        val dataInicial = LocalDate.of(anoInicio, mesInicio, diaInicio)
        var diasProgressao : Long = 0
        var diasCondicional : Long = 0
        val diasTotais = (anosPenaTotal * 365) + (mesesPenaTotal * 30) + diasPenaTotal
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        if(isHediondo == false){
            if(isPrimario == true){
                diasProgressao = (diasTotais * 0.16).toLong()
                diasCondicional = (diasTotais/3).toLong()

                return Pair(
                    dataInicial.plusDays(diasProgressao).format(formatter),
                    dataInicial.plusDays(diasCondicional).format(formatter)
                )
            }
            else{
                diasProgressao = (diasTotais * 0.20).toLong()
                diasCondicional = (diasTotais * 0.50).toLong()

                return Pair(
                    dataInicial.plusDays(diasProgressao).format(formatter),
                    dataInicial.plusDays(diasCondicional).format(formatter)
                )
            }
        }
        else if(isHediondo == true){
            if(isPrimario == true){
                diasProgressao = (diasTotais * 0.40).toLong()
                diasCondicional = ((diasTotais/3) * 2).toLong()

                return Pair(
                    dataInicial.plusDays(diasProgressao).format(formatter),
                    dataInicial.plusDays(diasCondicional).format(formatter)
                )
            }
            else{
                diasProgressao = (diasTotais * 0.60).toLong()
                return Pair(
                    dataInicial.plusDays(diasProgressao).format(formatter),
                    "Não possui"
                )
            }
        }
        return Pair(
            "Erro", "Erro"
        )
    }
}