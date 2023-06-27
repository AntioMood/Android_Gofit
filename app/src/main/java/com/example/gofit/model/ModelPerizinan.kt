package com.example.gofit.model

class ModelPerizinan (
    var id_perizinan : String,
    var id_instruktur : String,
    var id_jadwalH: String,
    var id_instruktur_pengganti: String,
    var status : String,
    var keterangan : String,
    var tgl_izin : String,
) {
    var id: Long? = null
}