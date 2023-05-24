package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AnsPage extends AppCompatActivity {
    EditText eP, eEP, eSn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ans);
        eP = findViewById(R.id.editPr);
        eEP = findViewById(R.id.editEPr);
        eSn = findViewById(R.id.editSn);
        eP.setText(MainActivity.genStr(1));
        eSn.setText(MainActivity.genStr(0));
        eEP.setText(MainActivity.genStr(2));
        configureEditText(eP);
        configureEditText(eEP);
        configureEditText(eSn);
    }

    private void configureEditText(EditText editText) {
        // Make the EditText uneditable
        editText.setFocusable(false);
        editText.setClickable(true);
        editText.setLongClickable(false);
        editText.setTextIsSelectable(true);
        editText.setEnabled(false);

        // Enable dragging the text if it exceeds the bounds of the EditText
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN
                        && editText.getLayout() != null) {
                    int textWidth = editText.getLayout().getWidth();
                    int viewWidth = editText.getWidth();
                    if (textWidth > viewWidth) {
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }
                return false;
            }
        });
    }

    public void onSteps(View view) {
        Toast.makeText(this, "StepPage", Toast.LENGTH_LONG).show();
        Intent a = new Intent(AnsPage.this, ResultPage.class);
        startActivity(a);
    }
}