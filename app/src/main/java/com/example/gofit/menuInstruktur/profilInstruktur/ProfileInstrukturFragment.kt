package com.example.gofit.menuInstruktur.profilInstruktur

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.android.volley.AuthFailureError
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.gofit.api.InstrukturAPI
import com.example.gofit.databinding.FragmentProfileInstrukturBinding
import com.example.gofit.login.LoginActivity
import com.example.gofit.model.ModelInstruktur
import com.example.gofit.model.ModelMember
import com.google.gson.Gson
import org.json.JSONObject
import java.nio.charset.StandardCharsets

class ProfileInstrukturFragment : Fragment() {

    private var _binding: FragmentProfileInstrukturBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var  queue: RequestQueue? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileInstrukturBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id_user = arguments?.getString("id")
        Log.d("ID User: ", id_user.toString())

        queue = Volley.newRequestQueue(context)

        val logOut = binding.button
        logOut.setOnClickListener{
            val bundle = Bundle()
            bundle.clear()
            val pindah = Intent(context, LoginActivity::class.java)
            startActivity(pindah)
        }

        val stringRequest: StringRequest = object :
            StringRequest(Method.GET, InstrukturAPI.GET_BY_ID + id_user, Response.Listener { response ->
                val gson = Gson()
                val jsonObject = JSONObject(response)
                val instruktur = gson.fromJson(
                    jsonObject.getJSONObject("data").toString(), ModelInstruktur::class.java
                )

                if(instruktur != null) {
                    Toast.makeText(context, "Data Berhasil Diambil" + instruktur.nama_instruktur, Toast.LENGTH_SHORT)
                        .show()

                    binding.tvNamaInstruktur.setText(instruktur.nama_instruktur)
                    binding.tvTotalTerlambat.setText(instruktur.total_terlambat)
                    binding.tvNoTelp.setText(instruktur.no_telp)
                    binding.tvTglLahir.setText(instruktur.tgl_lahir)
                    binding.tvEmail.setText(instruktur.email)

                }else{
                    Toast.makeText(context, "Instruktur tidak ditemukan!", Toast.LENGTH_SHORT)
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