package com.example.midterm_rohan_puri;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private List<String> table = new ArrayList<>();
    private ArrayAdapter<String> adapter;
    private ListView listview;
    private Set<Integer> numHistory = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText numInput = findViewById(R.id.numInput);
        Button history = findViewById(R.id.history);
        Button generate = findViewById(R.id.generate);
        ListView listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                table
        );
        listView.setAdapter(adapter);

        history.setOnClickListener(view -> {
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("numHistory", (Serializable) numHistory);
            startActivity(intent);
        });

        generate.setOnClickListener(view -> {
            String input = numInput.getText().toString().trim();
            if (input.isEmpty()) {
                Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
                return;
            }

            int num = Integer.parseInt(input);
            numHistory.add(num);
            table.clear();
            table.addAll(getTable(num));
            adapter.notifyDataSetChanged();
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String item = table.get(position);
            new AlertDialog.Builder(this)
                    .setTitle("Delete Row")
                    .setMessage("Delete " + item + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        table.remove(position);
                        adapter.notifyDataSetChanged();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });
    }

    private List<String> getTable(int num) {
        List<String> table = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            table.add(num + " x " + i + " = " + (num * i));
        }
        return table;
    }
}