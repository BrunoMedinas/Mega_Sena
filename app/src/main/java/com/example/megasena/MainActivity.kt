package com.example.megasena

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.lang.ref.Cleaner
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var prefs : SharedPreferences

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Shared = compartilhado/a



        val editNumber : EditText = findViewById(R.id.edit_number)
        val textResult : TextView = findViewById(R.id.txt_result)
        val btnGenerate : Button = findViewById(R.id.btn_generate)
        val btn_clean : Button = findViewById(R.id.btn_clean)

        prefs = getSharedPreferences("db", Context.MODE_PRIVATE)
        val result = prefs.getString("result", null)

        // um jeito de fazer o que o if faz só que mais eficiente
        result?.let {
            textResult.text = "Ultima aposta: $it"
        }

        btnGenerate.setOnClickListener {

            val text = editNumber.text.toString()

            numberGenerator(text, textResult)
        }

        // isso é só um teste pode apagar depois
        btn_clean.setOnClickListener{
              textResult.text = null

        }
    }

    @SuppressLint("SuspiciousIndentation")
    private fun numberGenerator(text : String, textResult : TextView){

        // codigo para erro se o campo estiver em branco

        if(text.isEmpty()){
            Toast.makeText(this,"Não deixe o campo em branco, digite um numero entre 1 a 15!!!", Toast.LENGTH_LONG).show()
            return
        }

        //codigo de erro se os número for fora da ordem de 6 a 15

        val qtd = text.toInt()
          if (qtd  < 6 || qtd  > 15){
              Toast.makeText(this,"Não deixe o campo em branco, digite um numero entre 1 a 15!!!", Toast.LENGTH_LONG).show()
                  return
              }

        //codigo sucesso para execução do codigo

                  val numbers = mutableSetOf<Int>()
                  val random =  Random

                  while (true){
                      val number = random.nextInt(60) // 0 a 59
                      numbers.add(number + 1) // tem que fazer isso aqui pq se nao ele nao vai midar o numero 1 e 60

                      if (numbers.size == qtd){
                          break
                      }
                  }

                  textResult.text = numbers.joinToString(" - ")

                 /**  val editor = prefs.edit()
                    editor.putString("result", textResult.text.toString())
                    editor.apply()
                  **/

        //Um jeito de fazer isso com codigo mais limpo e profissional
                   prefs.edit().apply {
                       putString("result", textResult.text.toString())
                       apply()
                   }
          }
    }
