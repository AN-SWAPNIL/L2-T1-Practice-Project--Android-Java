package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private boolean isExitConfirmed = false;
    static int max, maxt, count, last, only;
    static List<String> primes = new ArrayList<>(), preprimes;
    static List<String> esprimes = new ArrayList<>();
    static List<Integer> terms = new ArrayList<>(), preterms;
    static HashMap<String, List<Integer>> strterms = new HashMap<>();
    static HashMap<Integer, List<String>> termstrs = new HashMap<>();
    static List<List<List<String>>> table = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onAbout(View view){
        Toast.makeText(this, "About Page", Toast.LENGTH_LONG).show();
        Intent a = new Intent(MainActivity.this, AboutPage.class);
        startActivity(a);
    }

    public void onSolve(View view){
        Toast.makeText(this, "Solve Page", Toast.LENGTH_LONG).show();
        Intent a = new Intent(MainActivity.this, SolvePage.class);
        startActivity(a);
    }

    static void tableUpdate(int col) {
        table.add(new ArrayList<>());
        for (int i = 0; i < max + 1; i++) {
            table.get(col).add(new ArrayList<>());
        }
    }

    static String binary(int tt) {
        String s = "";
        count = 0;
        while (tt != 0) {
            if (tt % 2 == 1) {
                s += '1';
                count++;
            } else s += '0';
            tt /= 2;
        }
        while (s.length() != max) s += '0';
        return s;
    }

    static int compare(String a, String b) {
        int flag = 1, pos = -2;
        for (int i = 0; i < max; i++) {
            if (a.charAt(i) != b.charAt(i)) {
                if (flag == 1) {
                    flag = 0;
                    pos = i;
                } else {
                    return -1;
                }
            }
        }
        return pos;
    }

    static void buildTable(int col) {
        tableUpdate(col + 1);
        int tcount = 1;
        for (int i = 0; i < max; i++) {
            List<String> temp1 = table.get(col).get(i);
            List<String> temp2 = table.get(col).get(i + 1);
            if (temp2.size() == 0) continue;
            for (int j = 0; j < temp1.size(); j++) {
                for (int k = 0; k < temp2.size(); k++) {
                    tcount = 0;
                    int out = compare(temp1.get(j), temp2.get(k));
                    if (out < 0) continue;
                    char[] charArray = temp1.get(j).toCharArray();
                    charArray[out] = '-';
                    String ss = new String(charArray);
                    primes.remove(temp1.get(j));
                    primes.remove(temp2.get(k));
                    if (primes.contains(ss)) continue;
                    primes.add(ss);
                    List<Integer> temp = new ArrayList<>();
                    temp.addAll(strterms.get(temp1.get(j)));
                    temp.addAll(strterms.get(temp2.get(k)));
                    strterms.put(ss, temp);
                    table.get(col + 1).get(i).add(ss);
                }
            }
        }
        if (tcount == 1) {
            last = col;
            preprimes=new ArrayList<>(primes);
            return;
        }
        buildTable(col + 1);
    }

    static List<String> getDone(List<String> ll, int pos) {
        if (terms.size() == 0) return new ArrayList<>();
        if (pos == primes.size()) {
            List<Integer> tt = new ArrayList<>(terms);
            for (String s : ll) {
                for (Integer a : strterms.get(s)) tt.remove(a);
            }
            if (tt.size() == 0) return ll;
            return new ArrayList<>();
        }
        List<String> tt1 = new ArrayList<>(ll), tt2 = new ArrayList<>(ll);
        tt2.add(primes.get(pos));
        tt1 = getDone(tt1, pos + 1);
        tt2 = getDone(tt2, pos + 1);
        if (tt1.size() != 0 && (tt1.size() < tt2.size() || tt2.size() == 0)) ll = tt1;
        else ll = tt2;
        return ll;
    }

    static List<String> getSOP() {
        List<String> ans = new ArrayList<>();
        for (String s : primes) {
            for (int a : strterms.get(s)) {
                List<String> tt = new ArrayList<>();
                if (termstrs.containsKey(a))
                    tt = termstrs.get(a);
                tt.add(s);
                termstrs.put(a, tt);
            }
        }
        for (Integer a : terms) {
            if (termstrs.get(a).size() == 1) {
                if (!esprimes.contains(termstrs.get(a).get(0))) esprimes.add(termstrs.get(a).get(0));
                primes.remove(termstrs.get(a).get(0));
            }
        }
        for (String s : esprimes) {
            for (Integer a : strterms.get(s)) terms.remove(a);
        }
        ans.addAll(esprimes);
        List<String> others = getDone(new ArrayList<>(), 0);
        if(others.size()==0) only=1;
        else only=0;
        ans.addAll(others);
        return ans;
    }

    static String genStr(int p) {
        List<String> ss;
        if(p==1) ss = preprimes;
        else if(p==2) ss = esprimes;
        else ss = getSOP();
        String ans = "";
        String term = "";
        for (int i = max - 1; i >= 0; i--) term += (char) ('A' + i);
        for (String s : ss) {
            int cn = 0;
            for (int i = max - 1; i >= 0; i--) {
                if (s.charAt(i) == '1') ans+=term.charAt(i);
                else if (s.charAt(i) == '0') ans+=term.charAt(i) + "'";
                else cn++;
            }
            if (cn == max) System.out.print(1);
            else if (!ss.get(ss.size() - 1).equals(s)) {
                if(p==1||p==2) ans+=", ";
                else ans+=" + ";
            }
        }
        return ans;
    }

    @Override
    public void onBackPressed() {
        if (isExitConfirmed) {
            super.onBackPressed();
        } else {
            showExitConfirmationDialog();
        }
    }

    private void showExitConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                isExitConfirmed = true;
                onBackPressed();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }
}
// Input Example:
// 5
// 14 = 2 4 7 10 12 9 15 17 18 20 25 26 31 29
// 5 = 3 13 19 27 28