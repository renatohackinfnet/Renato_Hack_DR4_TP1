package com.renatohack.renato_hack_dr4_tp1.PrimeiraAtividade

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import java.io.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainViewModel(context: Context, activity: MainActivity) : ViewModel(){

    val contextMainActivity = context
    val mainActivity = activity

    var latitude = ""
    var longitutde = ""
    val REQUEST_PERMISSIONS_CODE = 128


    // GEOLOCALIZACAO XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    fun callAccessLocation() {
        val permissionAFL = ContextCompat.checkSelfPermission(contextMainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionACL = ContextCompat.checkSelfPermission(contextMainActivity, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (permissionAFL != PackageManager.PERMISSION_GRANTED && permissionACL != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mainActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                callDialog("É preciso liberar ACCESS_FINE_LOCATION", arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
            }
            else {
                ActivityCompat.requestPermissions(mainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSIONS_CODE)
            }
        }
        else {
            readMyCurrentCoordinates()
        }
    }

    private fun readMyCurrentCoordinates() {
        val locationManager = contextMainActivity.getSystemService(LOCATION_SERVICE) as LocationManager
        val isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

        if (!isGPSEnabled && !isNetworkEnabled) {
            Log.d("Permissao", "Ative os serviços necessários")
        }
        else {
            if (isGPSEnabled) {
                try {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        2000L, 0f, locationListener)
                } catch(ex: SecurityException) {
                    Log.d("Permissao", "Security Exception")
                }
            }
            else if (isNetworkEnabled) {
                try {
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        2000L, 0f, locationListener)
                } catch(ex: SecurityException) {
                    Log.d("Permissao", "Security Exception")
                }
            }
        }
    }

    private val locationListener: LocationListener = object : LocationListener {

            override fun onLocationChanged(location: Location) {
                latitude = location.latitude.toString()
                longitutde = location.longitude.toString()
            }

            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
            override fun onProviderEnabled(provider: String) {}
            override fun onProviderDisabled(provider: String) {}

        }



    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX



    // ESCRITA NA MEMORIA XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    fun callWriteOnSDCard() {
        val checkPermission = ContextCompat.checkSelfPermission(contextMainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (checkPermission != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(mainActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                callDialog("É preciso liberar WRITE_EXTERNAL_STORAGE", arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            }
            else {
                ActivityCompat.requestPermissions(mainActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_PERMISSIONS_CODE)
            }
        } else {
            writeFile()
        }
    }

    private fun writeFile() {
        val file = File(contextMainActivity.getExternalFilesDir(null), getDateAndHour()+".crd")
        if (latitude != "" && longitutde != ""){
            try {
                val os: OutputStream = FileOutputStream(file)
                val stringToPrint = "Latitude: $latitude\nLongitude: $longitutde\n\n"
                os.write((stringToPrint).toByteArray())
                os.close()
            } catch (e: IOException) {
                Log.d("Permissao", "Erro de escrita em arquivo")
            }
        }
    }
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX


    // LEITURA NA MEMORIA XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    fun callReadFromSDCard(): Boolean {
        if (ContextCompat.checkSelfPermission(contextMainActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(mainActivity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                callDialog(
                    "É preciso a liberar READ_EXTERNAL_STORAGE",
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE))

                return false
            }
            else {
                ActivityCompat.requestPermissions(mainActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_PERMISSIONS_CODE)

                return false
            }
        }

        else {
            return true
        }
    }
    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX


    // UTILIDADE XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    private fun callDialog(mensagem: String, permissions: Array<String>) {
        var mDialog = AlertDialog.Builder(contextMainActivity)
            .setTitle("Permissão")
            .setMessage(mensagem)
            .setPositiveButton("Ok")
            { dialog, id ->
                ActivityCompat.requestPermissions(
                    mainActivity, permissions,
                    REQUEST_PERMISSIONS_CODE)
                dialog.dismiss()
            }
            .setNegativeButton("Cancel")
            { dialog, id ->
                dialog.dismiss()
            }
        mDialog.show()
    }

    private fun getDateAndHour(): String {
        val currentDataHour = LocalDateTime.now()

        return "Data: " + currentDataHour.format(DateTimeFormatter.ISO_DATE) +
                " Hora: " + currentDataHour.format(DateTimeFormatter.ISO_TIME)
    }

    // XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
}
