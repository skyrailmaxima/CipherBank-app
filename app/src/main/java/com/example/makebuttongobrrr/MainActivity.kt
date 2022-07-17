package com.example.makebuttongobrrr
import android.content.Context
import android.os.Bundle

import android.util.AttributeSet
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.example.makebuttongobrrr.SSLsocket as SSLsock

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.primaryB)

        val disptxt: TextView = findViewById<TextView>(R.id.maintxt)
        val baseval: EditText =  findViewById<EditText>(R.id.DataInput)

        // set on-click listener
        button.setOnClickListener {
            // your code to perform when the user clicks on the button
            val quoteval: Double = baseval.text.toString().toDouble()
            val quote = SSLsock.getQuote(quoteval)
            println(quote)
            disptxt.text = quote  }
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return super.onCreateView(name, context, attrs)
    }

}