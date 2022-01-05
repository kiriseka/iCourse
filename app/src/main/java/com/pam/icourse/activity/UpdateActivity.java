package com.pam.icourse.activity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.pam.icourse.R;
import com.pam.icourse.database.DatabaseHelper;
import com.pam.icourse.session.SessionManager;

import java.util.Calendar;
import java.util.HashMap;

public class UpdateActivity extends AppCompatActivity {

    protected Cursor cursor;
    DatabaseHelper dbHelper;
    SQLiteDatabase db;
    Spinner spinJudul, spinLevel, spinKelas, spinJam;
    SessionManager session;
    String email;
    int id_book;
    public String sJudul, sLevel, sTanggal, sKelas, sJam;
    int jmlDewasa, jmlAnak;
    int hargaDewasa, hargaAnak;
    int hargaTotalDewasa, hargaTotalAnak, hargaTotal;
    private EditText etTanggal;
    private DatePickerDialog dpTanggal;
    Calendar newCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();
        String selection = intent.getStringExtra("selection");

        dbHelper = new DatabaseHelper(UpdateActivity.this);
        db = dbHelper.getReadableDatabase();

        final String[] judul = {"Android", "Web", "Machine Learning", "Cloud", "Sql"};
        final String[] level = {"Beginner", "Intermediate", "Experienced", "Advanced", "Expert"};
        final String[] kelas = {"A", "B", "C", "D", "E", "F"};
        final String[] jam = {"10:00", "13:00", "14:00", "16:40"};



        spinJudul = findViewById(R.id.update_judul);
        spinLevel = findViewById(R.id.update_level);
        spinKelas = findViewById(R.id.update_kelas);
        spinJam = findViewById(R.id.update_jam);

        ArrayAdapter<CharSequence> adapterJudul = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, judul);
        adapterJudul.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinJudul.setAdapter(adapterJudul);

        ArrayAdapter<CharSequence> adapterLevel = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, level);
        adapterLevel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinLevel.setAdapter(adapterLevel);

        ArrayAdapter<CharSequence> adapterKelas = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, kelas);
        adapterKelas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinKelas.setAdapter(adapterKelas);

        ArrayAdapter<CharSequence> adapterJam = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, jam);
        adapterJam.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinJam.setAdapter(adapterJam);

        spinJudul.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sJudul = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sLevel = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinKelas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sKelas = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinJam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sJam = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btnBook = findViewById(R.id.update);

        etTanggal = findViewById(R.id.update_tanggal_berangkat);
        etTanggal.setInputType(InputType.TYPE_NULL);
        etTanggal.requestFocus();
        session = new SessionManager(getApplicationContext());
        HashMap<String, String> user = session.getUserDetails();
        email = user.get(SessionManager.KEY_EMAIL);
        setDateTimeField();

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sJudul != null && sLevel != null && sTanggal != null && sKelas != null) {
                    AlertDialog dialog = new AlertDialog.Builder(UpdateActivity.this)
                            .setTitle("Anda yakin ingin mengubah ?")
                            .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    try {
                                        ContentValues cv = new ContentValues();
                                        cv.put("judul","Web");
//                                        cv.put("Field2","19");
//                                        db.update("TB_BOOK", cv, "_id = ?", new String[]{id});

                                        db.execSQL("UPDATE TB_BOOK SET judul = '" +sJudul +"',"+
                                                " level = '" +sLevel +"',"+
                                                " tanggal = '" +sTanggal +"'," +
                                                " kelas = '" +sKelas + "' ," +
                                                " jam = '" +sJam + "' WHERE id_book = '" + selection + "'");


                                        cursor = db.rawQuery("SELECT id_book FROM TB_BOOK ORDER BY id_book DESC", null);
                                        cursor.moveToLast();
                                        if (cursor.getCount() > 0) {
                                            cursor.moveToPosition(0);
                                            id_book = cursor.getInt(0);
                                        }
                                        Toast.makeText(UpdateActivity.this, "Update Berhasil", Toast.LENGTH_LONG).show();
                                        finish();
                                    } catch (Exception e) {
                                        Toast.makeText(UpdateActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                            .setNegativeButton("Tidak", null)
                            .create();
                    dialog.show();

                } else {
                    Toast.makeText(UpdateActivity.this, "Mohon isi tanggal mulai !", Toast.LENGTH_LONG).show();
                }
            }
        });

        setupToolbar();

    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.tbKrl);
        toolbar.setTitle("Form Update");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setDateTimeField() {
        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dpTanggal.show();
            }
        });

        dpTanggal = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                String[] bulan = {"Januari", "Februari", "Maret", "April", "Mei",
                        "Juni", "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
                sTanggal = dayOfMonth + " " + bulan[monthOfYear] + " " + year;
                etTanggal.setText(sTanggal);

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
}