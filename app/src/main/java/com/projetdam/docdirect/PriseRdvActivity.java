package com.projetdam.docdirect;

import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.projetdam.docdirect.adapter.AdapterPriseRdv;
import com.projetdam.docdirect.commons.AppSingleton;
import com.projetdam.docdirect.commons.ModelDayPlanner;
import com.projetdam.docdirect.commons.ModelDoctor;
import com.projetdam.docdirect.commons.HelperTime;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

@RequiresApi(api = Build.VERSION_CODES.O)
public class PriseRdvActivity extends AppCompatActivity {
    private static final String TAG = "TEST TEST";

    RecyclerView rcvTimeSlots;
    TextView tvDocName;
    ImageView ivDocPhoto;

    ModelDoctor doctor = AppSingleton.getInstance().getPickedDoctor();
    AdapterPriseRdv adapter;

    // créneaux ouverts
    ArrayList<ModelDayPlanner> daySlots = new ArrayList<>();
    // créneaux indisponibles
    HashMap<LocalDate, ArrayList<String>> offSlots = new HashMap<>();

    Query query = AppSingleton.consultations.document(doctor.getDoctorId()).collection("slots")
            .whereGreaterThan("rdvDate", Timestamp.now()).orderBy("rdvDate");

    void init() {
        tvDocName = findViewById(R.id.tvDocName);
        ivDocPhoto = findViewById(R.id.ivDocPhoto);

        rcvTimeSlots = findViewById(R.id.rcvTimeSlots);
        rcvTimeSlots.setLayoutManager(new LinearLayoutManager(this));
        rcvTimeSlots.setHasFixedSize(true);
        adapter = new AdapterPriseRdv(this, daySlots);
        rcvTimeSlots.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prise_rdv);

        init();

//        doctor = getIntent().getParcelableExtra("doctor");
        tvDocName.setText(String.format("Dr %s %s", doctor.getFirstname(), doctor.getName()));
    }

    @Override
    protected void onStart() {
        super.onStart();

        getQueryData(new QueryCallback() {
            @Override
            public void onCallback(ArrayList<Timestamp> list) {
                LocalDate lastDate = LocalDate.of(2020, 1, 1);
                for (Timestamp t : list) {
                    LocalDateTime dateTime = HelperTime.getDateTime(t);
                    LocalDate date = dateTime.toLocalDate();
                    LocalTime time = dateTime.toLocalTime();

                    if (date.isAfter(lastDate)) {
                        offSlots.put(date, new ArrayList<>());
                        lastDate = date;
                    }
                    offSlots.get(date).add(time.toString());
                }
                setTimetable();
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * Planning factice de test
     */
    private void setTimetable() {
        LocalDate dayDate = LocalDate.now();
        do {
            if (dayDate.getDayOfWeek() == DayOfWeek.SATURDAY) dayDate = dayDate.plusDays(1);
            if (dayDate.getDayOfWeek() == DayOfWeek.SUNDAY) dayDate = dayDate.plusDays(1);
            ModelDayPlanner dayPlanner = new ModelDayPlanner(
                    dayDate, "09:30", "17:00", 30, offSlots.get(dayDate), dayDate.isEqual(LocalDate.now()));
            dayDate = dayDate.plusDays(1);
            if (dayPlanner.getOpenSlots().isEmpty()) continue;
            daySlots.add(dayPlanner);
        } while (daySlots.size() < 5);
    }

    /**
     * Utilisation des callbacks
     **/
    private void getQueryData(QueryCallback queryCallback) {
        query.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        ArrayList<Timestamp> list = new ArrayList<>();
                        for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                            list.add(snapshot.getTimestamp("rdvDate"));
                        }
                        queryCallback.onCallback(list);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    private interface QueryCallback {
        void onCallback(ArrayList<Timestamp> list);
    }
}