package com.jogos.adivinhador

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startButton(view: View)
    {
        var difficulty = ""
        when(view.id)
        {
            R.id.btn_easy -> difficulty = "easy"
            R.id.btn_normal -> difficulty = "normal"
            R.id.btn_hard -> difficulty = "hard"
            R.id.btn_impossible -> difficulty = "impossible"
        }
        intent = Intent(this, Adivinhador::class.java)
        intent.putExtra("difficulty", difficulty)
        startActivity(intent)
    }

    fun creditsButton(view: View)
    {
        AlertDialog.Builder(this).apply { setMessage("Feito por Gustavo Henrique."); }.show()
    }
}