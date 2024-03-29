package com.jogos.adivinhador

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class Adivinhador : AppCompatActivity() {
    private var numero = 0
    private var erros = 0
    private var maxErros = 0
    private var limite = 0

    private var valido = true
    private var debug = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adivinhador)

        limite = 10

        if(intent.extras?.getString("difficulty") == "easy") {
            maxErros = 7
        }
        else if(intent.extras?.getString("difficulty") == "normal") {
            maxErros = 5
        }
        else if(intent.extras?.getString("difficulty") == "hard") {
            findViewById<RelativeLayout>(R.id.row3).visibility = View.VISIBLE
            maxErros = 5
            limite = 15
        }
        else if(intent.extras?.getString("difficulty") == "impossible") {
            findViewById<RelativeLayout>(R.id.row3).visibility = View.VISIBLE
            findViewById<ImageButton>(R.id.btn_hint).apply { isEnabled = false; isClickable = false; visibility = View.INVISIBLE }
            maxErros = 3
            limite = 15
        }

        valido = true;
        numero = Random.nextInt(1, limite + 1)
        erros = 0
        findViewById<TextView>(R.id.txt_erros).apply{ text = "Erros: $erros/$maxErros" }
        for(i in 1..15)
        { findViewById<Button>(resources.getIdentifier("btn_n$i", "id", packageName)).text = i.toString() }
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

    @SuppressLint("SetTextI18n")
    fun guessClick(view: View) {
        if (numero == (findViewById<Button>(view.id).text).toString().toInt()) {
            findViewById<Button>(view.id).setBackgroundColor(Color.GREEN)
            findViewById<TextView>(R.id.txt_valorlogico).apply { text = "" }
            alertaFunc("Você acertou o número ($numero) em " + (erros + 1) +  " tentativa(s)!")
        } else {
            erros++
            findViewById<TextView>(R.id.txt_erros).apply{ text = "Erros: $erros/$maxErros" }
            findViewById<Button>(view.id).setBackgroundColor(Color.RED)
            findViewById<Button>(view.id).isEnabled = false
            findViewById<Button>(view.id).isClickable = false
            if(erros == maxErros) {
                alertaFunc("Você não acertou o número! ($numero)")
            }

            if (numero > findViewById<Button>(view.id).text.toString().toInt()) {
                findViewById<TextView>(R.id.txt_valorlogico).apply { text = ">" }
            } else {
                findViewById<TextView>(R.id.txt_valorlogico).apply { text = "<" }
            }
        }
    }

    fun hintClick(view: View) {
        val msg = (numero + Random.nextInt(0, 5)).toString() + " >= número >= " + (numero - Random.nextInt(0, 5)).toString()
        view.isEnabled = false;
        view.isClickable = false;
        AlertDialog.Builder(this).setMessage(msg).setPositiveButton("OK") { _, _  -> }.show()
    }
}