package com.example.gofit.member

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.gofit.R
import com.example.gofit.databinding.ActivityHomeInstrukturBinding
import com.example.gofit.databinding.ActivityHomeMemberAvtivityBinding

class HomeMemberAvtivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeMemberAvtivityBinding = ActivityHomeMemberAvtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        val btn = binding.izinBtn
        val bundle = intent.extras
        val id_user = bundle?.getString("id_user")


//        btn.setOnClickListener(View.OnClickListener {
//            var mbundle = Bundle()
//            var pindah: Intent
//            pindah = Intent(this, PerizinanActivity::class.java)
//            mbundle.putString("id", id_user)
//            pindah.putExtras(mbundle)
//            startActivity(pindah)
//        })

    }
}