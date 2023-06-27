package com.example.gofit.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.gofit.InstrukturBotNavActivity
import com.example.gofit.MOBotNavActivity
import com.example.gofit.MemberBotNavActivity
import com.example.gofit.api.UserAPI
import com.example.gofit.databinding.ActivityLoginBinding
import com.example.gofit.instruktur.HomeInstrukturActivity
import com.example.gofit.member.HomeMemberAvtivity
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class LoginActivity : AppCompatActivity() {
    private var queue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val inputUsername = binding.userInput
        val inputPassword = binding.passInput

        val btnLogin = binding.loginButton

        queue = Volley.newRequestQueue(this)

        val jsonobj = JSONObject()

        btnLogin.setOnClickListener(View.OnClickListener {
            val inputEmail = inputUsername.getEditText()?.getText().toString()
            val inputPassword = inputPassword.getEditText()?.getText().toString()
            jsonobj.put("email", inputEmail)
            jsonobj.put("password", inputPassword)
            val request = JsonObjectRequest(
                Request.Method.POST, UserAPI.BASE_URL,jsonobj,
                { response ->
                    Log.d("Responsenya: ", response["message"].toString())
                    Toast.makeText(
                        this@LoginActivity,
                        response["message"].toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                    var bundle = Bundle()
                    var pindah: Intent

                    if(response["id"].toString().contains('I')){
                        pindah = Intent(this, InstrukturBotNavActivity::class.java) //pindah ke MO activity hrsnya
                        bundle.putString("id_user", response["id"].toString())
                        Log.d("ID User: ", response["id"].toString())
                        pindah.putExtras(bundle)
                        startActivity(pindah)
                    }else if(response["id"].toString().contains('P')){
                        if(response["role"].toString() == "2"){
                            pindah = Intent(this, MOBotNavActivity::class.java) //pindah ke MO activity hrsnya
                            bundle.putString("id_user", response["id"].toString())
                            Log.d("ID User: ", response["id"].toString())
                            Log.d("ID Role: ", response["role"].toString())
                            pindah.putExtras(bundle)
                            startActivity(pindah)
                        }else{
                            Toast.makeText(this@LoginActivity,"Anda Bukan MO", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        pindah = Intent(this, MemberBotNavActivity::class.java)
                        bundle.putString("id_user", response["id"].toString())
                        Log.d("ID User: ", response["id"].toString())
                        pindah.putExtras(bundle)
                        startActivity(pindah)
                    }



                }, { error->
                    try{
                        val errorData = String(error.networkResponse.data, StandardCharsets.UTF_8)
                        val errorJson = JSONObject(errorData)
                        Toast.makeText(
                            this@LoginActivity,
                            errorJson["message"].toString(),
                            Toast.LENGTH_SHORT
                        ).show()

                    }catch (e: Exception){
                        Log.d("Error Login", e.message.toString())
                        Toast.makeText(this@LoginActivity,"exception: " + e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            )
            queue!!.add(request)

        })
    }
}