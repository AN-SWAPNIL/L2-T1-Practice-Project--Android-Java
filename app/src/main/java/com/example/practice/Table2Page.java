package com.example.practice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Table2Page extends Fragment {
    private LinearLayout layout;

    public Table2Page() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_table2, container, false);
        layout = view.findViewById(R.id.lin_layout);

        ScrollView scrollView = new ScrollView(getActivity());

        // Set the layout parameters for the ScrollView
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        scrollView.setLayoutParams(params);

        TableLayout tableLayout = new TableLayout(getActivity());

        TableLayout.LayoutParams tabParams = new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT
        );

        tableLayout.setLayoutParams(tabParams);
        TableRow.LayoutParams cellParams = new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT
        );
        String mins[] = MainActivity.genStr(1).split(",");
        for (int row = 0; row < MainActivity.preprimes.size() + 2; row++) {
            TableRow tableRow = new TableRow(getActivity());
            for (int col = 0; col < MainActivity.preterms.size() + 1; col++) {
                TextView cell = new TextView(getActivity());
                String ss="";
                // Set the layout parameters for the cell
                cell.setLayoutParams(cellParams);
                cell.setTextSize(20);
                cell.setGravity(Gravity.CENTER);

                if (row == 0) {
                    if (col==0) ss=" Prime Im. ";
                    else ss=Integer.toString(MainActivity.preterms.get(col-1));
                }
                else if(row==MainActivity.preprimes.size()+1){
                    if(col==0) ss=" Covered : ";
                    else{
                        for(String s: MainActivity.esprimes){
                            if(MainActivity.termstrs.get(MainActivity.preterms.get(col-1)).contains(s)) {
                                ss="  ✓  ";
                                break;
                            }
                        }
                    }
                }
                else{
                    if(col==0)
                        ss="  "+mins[row-1].trim()+"  ";
                    else{
                        if(MainActivity.termstrs.get(MainActivity.preterms.get(col-1)).contains(MainActivity.preprimes.get(row-1))) {
                            if(MainActivity.termstrs.get(MainActivity.preterms.get(col-1)).size()==1) ss="  (X)  ";
                            else ss="   X   ";
                        }
                        else ss="  ";
                    }
                }
                cell.setText(ss);
                tableRow.addView(cell);
            }
            tableLayout.addView(tableRow);
        }
        scrollView.addView(tableLayout);

        // Add the ScrollView to the LinearLayout
        layout.addView(scrollView);

        return view;
    }
}