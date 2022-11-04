package com.grayfien.ugd89_a_10603_project1

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.*
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import com.grayfien.gd9_camera_a_10603.CameraView
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private var mCamera: Camera? = null
    private var mCameraView: CameraView? =null
    //on below line we are creating
    //a variable for our proximity sensor
    lateinit var proximitySensor: Sensor
    //on below line we are creating
    //a variable for sensor manager
    lateinit var sensorManager: SensorManager

    var proximitySensorEventListener: SensorEventListener? = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            // method to check accuracy changed in sensor
        }
        override fun onSensorChanged(event: SensorEvent) {
            //check if the sensor type is proximity sensor
            if (event.sensor.type == Sensor.TYPE_PROXIMITY){
                if (event.values[0] ==0f){
                    mCameraView?.switchCamera()
                }else{

                }
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //on below line we are initializing our sensor manager
        sensorManager = getSystemService(Context.SENSOR_SERVICE)as SensorManager

        //on below line we are initializing our proximity sensor available
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY)

        if (proximitySensor == null){
            //on below line we are displaying toast if no sensor is available
            Toast.makeText(this, "No proximity sensor found in device...", Toast.LENGTH_SHORT).show()
            finish()
        }else{
            //on below we are registering
            //our sensor with sensor manager
            sensorManager.registerListener(
                proximitySensorEventListener,
                proximitySensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        try {
            mCamera = Camera.open()
        }catch (e: Exception){
            Log.d("Error","Failed to Get Camera"+ e.message)
        }
        if(mCamera != null){
            mCameraView = CameraView(this@MainActivity, mCamera!!)
            val camera_view = findViewById<View>(R.id.FLCamera) as FrameLayout
            camera_view.addView(mCameraView)
        }
        @SuppressLint("MissingInflatedId", "LocalSuppress") val imageClose = findViewById<View>(R.id.imgClose) as ImageButton
        imageClose.setOnClickListener{view: View? -> System.exit(0)}

    }
}