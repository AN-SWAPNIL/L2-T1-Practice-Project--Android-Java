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

import java.util.List;
import java.util.stream.Collectors;

public class Table1Page extends Fragment {
    private LinearLayout layout;

    public Table1Page() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_table1, container, false);
        layout = view.findViewById(R.id.lin_layout);


        for (int i = 0; i < MainActivity.table.size(); i++) {
            boolean flag=true;
            for(int j=0; j<MainActivity.table.get(i).size(); j++) {
                if(MainActivity.table.get(i).get(j).size()!=0) {
                    flag=false;
                    break;
                }
            }
            if(flag) continue;

            ScrollView scrollView = new ScrollView(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            scrollView.setLayoutParams(params);

            TableLayout tableLayout = new TableLayout(getActivity());
            TableLayout.LayoutParams tabParams = new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.WRAP_CONTENT
            );
            tableLayout.setLayoutParams(tabParams);

            TableRow.LayoutParams cellParams = new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.WRAP_CONTENT
            );
            for (int row = 0; row < MainActivity.table.get(i).size(); row++) {
                for (int cells = -1; cells < MainActivity.table.get(i).get(row).size(); cells++) {

                    if(row!=0&&cells==-1) continue;
                    TableRow tableRow = new TableRow(getActivity());

                    for (int col = 0; col < 3; col++) {
                        TextView cell = new TextView(getActivity());
                        String ss;
                        cell.setLayoutParams(cellParams);
                        cell.setTextSize(20);

                        switch (col) {
                            case 0:
                                if(cells!=-1){
                                    ss="| ";
                                    List<Integer> sterms = MainActivity.strterms.get(MainActivity.table.get(i).get(row).get(cells));
                                    ss+= sterms.stream().map(Object::toString).collect(Collectors.joining(", "));
                                } else ss= "| Col. "+(i+1);
                                cell.setText(ss);
                                break;
                            case 1:
                                if(cells!=-1) {
                                    ss = "   ";
                                    String s = MainActivity.table.get(i).get(row).get(cells);
                                    for (int j = s.length() - 1; j >= 0; j--) {
                                        if (s.charAt(j) == '-' || s.charAt(j) == '0' || s.charAt(j) == '1')
                                            ss += s.charAt(j);
                                    }
                                } else ss = "  ";
                                cell.setGravity(Gravity.CENTER);
                                cell.setText(ss);
                                break;
                            case 2:
                                if(cells!=-1) {
                                    if (!MainActivity.preprimes.contains(MainActivity.table.get(i).get(row).get(cells)))
                                        ss="   ✓ |";
                                    else ss="     |";
                                } else ss="     |";
                                cell.setGravity(Gravity.END);
                                cell.setText(ss);
                                break;
                            default:
                                break;
                        }
                        tableRow.addView(cell);
                    }
                    tableLayout.addView(tableRow);
                }
                TableRow last = new TableRow(getActivity());
                TextView clast = new TextView(getActivity());
                clast.setLayoutParams(cellParams);
                clast.setTextSize(20);
                clast.setText(" ");
                last.addView(clast);
                tableLayout.addView(last);
            }
            scrollView.addView(tableLayout);
            layout.addView(scrollView);
        }

        return view;
    }
}