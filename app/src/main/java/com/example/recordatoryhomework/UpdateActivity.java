package com.example.recordatoryhomework;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.HashMap;
import java.util.Map;

public class UpdateActivity extends AppCompatActivity {

    private String URL = "http://192.168.0.105/recordatorydb/getdate.php";
    private String URL_UPDATE = "http://192.168.0.105/recordatorydb/update.php";
    private String URL_DELETE = "http://192.168.0.105/recordatorydb/delete.php";

    EditText et_update_recordatory, et_update_date;
    Button btn_update, btn_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();
        String message = intent.getStringExtra("message");

        et_update_recordatory = findViewById(R.id.update_recordatory);
        et_update_date = findViewById(R.id.date_update);

        et_update_recordatory.setText(message);
        getDataFromMessage(URL, et_update_recordatory.getText().toString());

        btn_update = findViewById(R.id.update_button);
        btn_update.setOnClickListener(view -> {
            updateRecordatory(URL_UPDATE, et_update_recordatory.getText().toString(), et_update_date.getText().toString());
            finish();
        });

        btn_delete = findViewById(R.id.delete_button);
        btn_delete.setOnClickListener(view -> {
            deleteRecordatory(URL_DELETE, et_update_recordatory.getText().toString());
            finish();
        });
    }

    void getDataFromMessage(String url, String message){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                response -> {

                try{

                    JSONObject object = new JSONObject(response);
                    String data = object.getString("date");
                    et_update_date.setText(data);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                }, error -> Toast.makeText(UpdateActivity.this, error.toString(), Toast.LENGTH_SHORT).show()){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("message", message);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    void updateRecordatory(String url, String message, String date){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,

                response -> Toast.makeText(UpdateActivity.this, "Recordatory updated successfully!", Toast.LENGTH_SHORT).show(),

                error -> Toast.makeText(UpdateActivity.this, error.toString(), Toast.LENGTH_SHORT).show()){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("message", message);
                params.put("date", date);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    void deleteRecordatory(String url, String message){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,

                response -> Toast.makeText(UpdateActivity.this, "Recordatory deleted successfully!", Toast.LENGTH_SHORT).show(),

                error -> Toast.makeText(UpdateActivity.this, error.toString(), Toast.LENGTH_SHORT).show()){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("message", message);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}