package com.example.gofit.menuMember.profileMember

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
//import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.gofit.DepositKelasActivity
import com.example.gofit.api.MemberAPI
import com.example.gofit.databinding.FragmentProfileMemberBinding
import com.example.gofit.login.LoginActivity
import com.example.gofit.model.ModelMember
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class ProfileMemberFragment : Fragment() {

    private var _binding: FragmentProfileMemberBinding? = null

    private val binding get() = _binding!!
    private var  queue: RequestQueue? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileMemberBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id_user = arguments?.getString("id")
        Log.d("ID User: ", id_user.toString())

        queue = Volley.newRequestQueue(context)

        val btnDepoK = binding.buttonDepositKelas
        btnDepoK.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("id_member", id_user)
            val pindah = Intent(context, DepositKelasActivity::class.java)
            pindah.putExtras(bundle)
            startActivity(pindah)
        }

        val logOut = binding.button
        logOut.setOnClickListener{
            val bundle = Bundle()
            bundle.clear()
            val pindah = Intent(context, LoginActivity::class.java)
            startActivity(pindah)
        }

        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, MemberAPI.GET_BY_ID + id_user, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val member = gson.fromJson(
                    jsonObject.getJSONObject("data").toString(), ModelMember::class.java
                )

                if(member != null) {
                    Toast.makeText(context, "Data Berhasil Diambil " + member.nama_member, Toast.LENGTH_SHORT)
                        .show()

                    var status = member.status
                    var tgl_exp = member.tgl_exp

                    if(status.equals("1")){
                        status = "Aktif"
                    }else{
                        status = "TIDAK AKTIF"
                        tgl_exp = "-"
                    }

                    binding.tvStatusMember.setText(status)
                    binding.tvTglExp.setText("Tanggal Expired: " + tgl_exp)
                    binding.tvNamaMember.setText(member.nama_member)
                    binding.tvNoTelp.setText(member.no_telp)
                    binding.tvTglLahir.setText(member.tgl_lahir)
                    binding.tvEmail.setText(member.email)
                    binding.tvDepositUang.setText("Rp " + member.deposit_uang + ",-")

                }else{
                    Toast.makeText(context, "Member tidak ditemukan!", Toast.LENGTH_SHORT)
                        .show()
                }


            }, Response.ErrorListener { error ->
                try{
                    val responseBody =
                        String(error.networkResponse.data, StandardCharsets.UTF_8)
                    val errors = JSONObject(responseBody)
                    Toast.makeText(
                        context,
                        errors.getString("message"),
                        Toast.LENGTH_SHORT
                    ).show()
                }catch (e: Exception){
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            }) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["Accept"] = "application/json"
                return headers
            }
        }
        queue!!.add(stringRequest)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}