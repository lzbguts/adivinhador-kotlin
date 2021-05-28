package com.jogos.adivinhador

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class Adivinhador : AppCompatActivity() {
    private var numero = 0
    private var erros = 0
    private var limiteErros = 0
    private var rangeNumero = 0
    private var debug = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adivinhador)

        if(intent.extras?.getString("difficulty") == "easy") {
            rangeNumero = 5
            limiteErros = 3
            findViewById<EditText>(R.id.edit_num).hint = "Escreva um número entre 1-5"
        }
        else if(intent.extras?.getString("difficulty") == "normal") {
            rangeNumero = 100
            limiteErros = 5
            findViewById<EditText>(R.id.edit_num).hint = "Escreva um número entre 1-100"
        }
        else if(intent.extras?.getString("difficulty") == "hard") {
            rangeNumero = 1000
            limiteErros = 15
            findViewById<EditText>(R.id.edit_num).hint = "Escreva um número entre 1-1000"
        }

        numero = Random.nextInt(1, rangeNumero + 1)
        erros = 0
        findViewById<TextView>(R.id.txt_erros).apply{ text = "Erros: $erros/$limiteErros" }
        if(debug) { findViewById<TextView>(R.id.txt_dnum).text = numero.toString() }
        }

    private fun alertaFunc(str: String) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(str)
                .setCancelable(false)
                .setPositiveButton("REINICIAR") { _, _ ->
                    val intentf = intent
                    finish()
                    startActivity(intentf);
                }
        val alert = builder.create()
        alert.show()
    }

    fun desistirFunc(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("O número era ($numero)!")
            .setCancelable(false)
            .setPositiveButton("OK") { _, _ ->
                startActivity(Intent(this, MainActivity::class.java));
            }
        val alert = builder.create()
        alert.show()
    }

    @SuppressLint("SetTextI18n")
    fun guessClick(view: View) {
        if(findViewById<EditText>(R.id.edit_num).text.toString() != "") {
            if (numero == findViewById<EditText>(R.id.edit_num).text.toString().toInt()) {
                findViewById<TextView>(R.id.txt_valorlogico).apply { text = "" }
                alertaFunc("Você acertou o número ($numero) em " + (erros + 1) +  " tentativa(s)!")
            } else {
                erros++
                findViewById<TextView>(R.id.txt_erros).apply{ text = "Erros: $erros/$limiteErros" }
                if(erros == limiteErros) {
                    alertaFunc("Você não acertou o número! ($numero)")
                }

                if (numero > findViewById<EditText>(R.id.edit_num).text.toString().toInt()) {
                    findViewById<TextView>(R.id.txt_valorlogico).apply { text = ">" }
                } else {
                    findViewById<TextView>(R.id.txt_valorlogico).apply { text = "<" }
                }
            }
        }
        else {
            findViewById<TextView>(R.id.txt_valorlogico).apply { text = "O valor está vazio." }
        }
    }
}