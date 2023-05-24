package com.example.practice;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolvePage extends AppCompatActivity {
    EditText var, min, dont;
    TextInputEditText editMins, editDonts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solve);
        var = findViewById(R.id.editVar);
        min = findViewById(R.id.editMin);
        dont = findViewById(R.id.editDont);
        editMins = findViewById(R.id.editMinterms);
        editDonts = findViewById(R.id.editDontCares);
    }

    public void onData(View view) {
        MainActivity.terms.clear();
        MainActivity.esprimes.clear();
        MainActivity.primes.clear();
        MainActivity.strterms.clear();
        MainActivity.table.clear();
        MainActivity.termstrs.clear();
        if(TextUtils.isEmpty(var.getText())||TextUtils.isEmpty(min.getText().toString())||TextUtils.isEmpty(dont.getText().toString())) {
            Toast.makeText(this, "Wrong Input", Toast.LENGTH_SHORT).show();
            return;
        }
        MainActivity.max = Integer.parseInt(var.getText().toString());
        MainActivity.maxt = (int) Math.pow(2, MainActivity.max);
        int mins = Integer.parseInt(min.getText().toString());
        String mterms[] = editMins.getText().toString().split("\\s+");
        int donts = Integer.parseInt(dont.getText().toString());
        if(mins>MainActivity.maxt||mins!=mterms.length){
            Toast.makeText(this, "Wrong Input", Toast.LENGTH_SHORT).show();
            return;
        }
        List<Integer> termsAll = new ArrayList<>();
        for(int i=0; i<mins; i++) {
            Integer tt = Integer.parseInt(mterms[i]);
            if (tt >= MainActivity.maxt || tt<0 || termsAll.contains(tt)) {
                Toast.makeText(this, "Wrong Input", Toast.LENGTH_SHORT).show();
                return;
            }
            MainActivity.terms.add(tt);
            termsAll.add(tt);
        }
        Collections.sort(MainActivity.terms);
        MainActivity.preterms=new ArrayList<>(MainActivity.terms);
        if(donts>0) {
            String dterms[] = editDonts.getText().toString().split("\\s+");
            if(donts>MainActivity.maxt||donts!=dterms.length){
                Toast.makeText(this, "Wrong Input", Toast.LENGTH_SHORT).show();
                return;
            }
            for (int i = 0; i < donts; i++) {
                Integer tt = Integer.parseInt(dterms[i]);
                if (tt >= MainActivity.maxt || tt < 0 || termsAll.contains(tt)) {
                    Toast.makeText(this, "Wrong Input", Toast.LENGTH_SHORT).show();
                    return;
                }
                termsAll.add(tt);
            }
        }
        MainActivity.tableUpdate(0);
        for (int a : termsAll) {
            String bin = MainActivity.binary(a);
            MainActivity.primes.add(bin);
            MainActivity.table.get(0).get(MainActivity.count).add(bin);
            List<Integer> ll = new ArrayList<>();
            ll.add(a);
            MainActivity.strterms.put(bin, ll);
        }
        MainActivity.buildTable(0);
        Toast.makeText(this, "Answer Page", Toast.LENGTH_LONG).show();
        Intent a = new Intent(SolvePage.this, AnsPage.class);
        startActivity(a);
    }
}