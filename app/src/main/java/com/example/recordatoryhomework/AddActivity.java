package com.example.recordatoryhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class AddActivity extends AppCompatActivity {

    private String URL = "http://192.168.0.105/recordatorydb/add.php";

    EditText et_recordatory, et_date;
    Button btn_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        et_recordatory = findViewById(R.id.et_Recordatory);
        et_date = findViewById(R.id.et_Date);
        btn_add = findViewById(R.id.insert_button);

        btn_add.setOnClickListener(view -> {
            addRecordatory(URL, et_recordatory.getText().toString(), et_date.getText().toString());
            finish();
        });
    }

    void addRecordatory(String url, String message, String date){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {

                    if (!response.isEmpty()){

                        Toast.makeText(AddActivity.this, "Recordatory added successfully!", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(AddActivity.this, "Recordatory not added", Toast.LENGTH_SHORT).show();
                    }

                }, error -> Toast.makeText(AddActivity.this, error.toString(), Toast.LENGTH_LONG).show()){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("message", message);
                params.put("date", date);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(AddActivity.this);
        requestQueue.add(stringRequest);
    }
}