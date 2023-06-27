package com.example.gofit.instruktur

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.gofit.MainActivity
import com.example.gofit.R
import com.example.gofit.databinding.ActivityHomeInstrukturBinding

class HomeInstrukturActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeInstrukturBinding = ActivityHomeInstrukturBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val btn = binding.izinBtn
        val bundle = intent.extras
        val id_user = bundle?.getString("id_user")


        btn.setOnClickListener(View.OnClickListener {
            var mbundle = Bundle()
            var pindah: Intent
            pindah = Intent(this, MainActivity::class.java)
            mbundle.putString("id", id_user)
            pindah.putExtras(mbundle)
            startActivity(pindah)
        })

    }
}