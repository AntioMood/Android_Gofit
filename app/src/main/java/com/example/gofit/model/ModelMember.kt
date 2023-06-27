package com.example.gofit.model

class ModelMember(
    var id_member : String,
    var nama_member : String,
    var tgl_lahir : String,
    var alamat : String,
    var email : String,
    var password : String,
    var no_telp : String,
    var deposit_uang : Double,
    var status : String,
    var tgl_pembuatan : String,
    var tgl_exp : String,
    var jenis_kelamin : String
) {
    var id: Long? = null
}