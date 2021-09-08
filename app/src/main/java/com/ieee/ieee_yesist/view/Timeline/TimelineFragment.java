package com.ieee.ieee_yesist.view.Timeline;

import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

//import com.android.volley.RequestQueue;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.JsonArrayRequest;
//import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.ieee.ieee_yesist.R;
import com.ieee.ieee_yesist.adapters.SpeakerAdapter;
import com.ieee.ieee_yesist.adapters.SponsorAdapter;
import com.ieee.ieee_yesist.adapters.TimelineAdapter;
import com.ieee.ieee_yesist.api.Service;
import com.ieee.ieee_yesist.databinding.FragmentTimelineBinding;
import com.ieee.ieee_yesist.model.Event;
import com.ieee.ieee_yesist.model.Speaker;
import com.ieee.ieee_yesist.model.Sponsor;
import com.ieee.ieee_yesist.model.WebinarItem;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimelineFragment extends Fragment implements TimelineAdapter.EventClickListener {

    public TimelineFragment() {
        // Required empty public constructor
    }

    private FragmentTimelineBinding binding;
    private List<Event> eventList;
    private List<Event> dynamicEventList;
    private List<Event> displayList;
    private TimelineAdapter timelineAdapter;
    private Calendar cal;
    private FirebaseFirestore db;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();

        cal = Calendar.getInstance();
        String timezone = cal.getTimeZone().getDisplayName();
        binding.timezone.setText(timezone);

        String displayMonth = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + cal.get(Calendar.YEAR);
        binding.tvMonth.setText(displayMonth);

        binding.calendarLayout.setOnClickListener(v -> {
            if(binding.calendar.getVisibility() == View.VISIBLE) {
                binding.calendar.setVisibility(View.GONE);
                binding.calendarArrow.setImageResource(R.drawable.ic__arrow_down);
            }else {
                binding.calendar.setVisibility(View.VISIBLE);
                binding.calendarArrow.setImageResource(R.drawable.ic_arrow_up);
            }
        });

        timelineAdapter = new TimelineAdapter(this);
        displayList = new ArrayList<>();
        eventList = new ArrayList<>();
        dynamicEventList = new ArrayList<>();

        binding.progressBar.setVisibility(View.GONE);

        try {
            populateList();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        populateListThroughFirebase();
        Log.d("EVENT LIST SIZE", Integer.toString(eventList.size()));

    }

    private void populateDisplayList(CalendarDay date, boolean dateSelected) {
        displayList = new ArrayList<>();
        if(dateSelected) {
            CalendarDay selectedDay = binding.calendar.getSelectedDate();
            assert selectedDay != null;
            String sday = String.valueOf(selectedDay.getDay());
            if(sday.length() == 1) {
                sday = "0" + sday;
            }
            String smonth = String.valueOf(selectedDay.getMonth());
            if(smonth.length() == 1) {
                smonth = "0" + smonth;
            }
            String syear = String.valueOf(selectedDay.getYear());
            String selectedDate = syear + "-" + smonth + "-" + sday;
            Log.d("SELECTED DATE", selectedDate);
            for (int i = 0; i < eventList.size(); i++) {
                Event event = eventList.get(i);
                Date d = event.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String day = sdf.format(d);
                Log.d("DAY", day);
                if (selectedDate.equalsIgnoreCase(day)) {
                    displayList.add(event);
                }
            }
        }else {
            if (date != null) {
                String smonth = String.valueOf(date.getMonth());
                if(smonth.length() == 1) {
                    smonth = "0" + smonth;
                }
                String syear = String.valueOf(date.getYear());
                String selectedDate = syear + "-" + smonth;
                Log.d("SELECTED DATE", selectedDate);
                for (int i = 0; i < eventList.size(); i++) {
                    Event event = eventList.get(i);
                    Date d = event.getDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                    String day = sdf.format(d);
                    if (selectedDate.equalsIgnoreCase(day)) {
                        displayList.add(event);
                    }
                }
            } else {
                Date currentDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
                String currentMonth = sdf.format(currentDate);
                for (int i = 0; i < eventList.size(); i++) {
                    Event event = eventList.get(i);
                    Date d = event.getDate();
                    String month = sdf.format(d);
                    if (currentMonth.equalsIgnoreCase(month)) {
                        displayList.add(event);
                    }
                }
            }
        }
        timelineAdapter.submitList(displayList);
    }

    private void setUpCalendar() {

        binding.timelineRv.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                if(binding.calendar.getVisibility() == View.VISIBLE)
                    return true;
                return false;
            }
        });

        binding.calendar.setOnMonthChangedListener((widget, date) -> {
            String month = new DateFormatSymbols().getMonths()[date.getMonth()-1];
            int year = date.getYear();
            String displayMonth = month + " " + year;
            binding.tvMonth.setText(displayMonth);
            populateDisplayList(date, false);
        });

        binding.calendar.setOnDateChangedListener((widget, date, selected) -> {
            binding.calendar.setVisibility(View.GONE);
            binding.calendarArrow.setImageResource(R.drawable.ic__arrow_down);
            populateDisplayList(date, true);
        });

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int cDay = cal.get(Calendar.DATE);
        int cMonth = cal.get(Calendar.MONTH);
        int cYear = cal.get(Calendar.YEAR);
        Log.d("CALENDAR DAY", CalendarDay.from(cYear, cMonth+1, cDay).toString());
        populateDisplayList(CalendarDay.from(cYear, cMonth+1, cDay), false);
        ArrayList<CalendarDay> currentList = new ArrayList<>();
        ArrayList<CalendarDay> upcomingList = new ArrayList<>();
        ArrayList<CalendarDay> completedList = new ArrayList<>();
        for (int i = 0; i < eventList.size(); i++) {
            Event event = eventList.get(i);
            Date d = event.getDate();
            cal.setTime(d);
            int eDay = cal.get(Calendar.DATE);
            int eMonth = cal.get(Calendar.MONTH);
            int eYear = cal.get(Calendar.YEAR);
            if(eDay == cDay && cMonth == eMonth && eYear == cYear) {
                currentList.add(CalendarDay.from(eYear, eMonth+1, eDay));
            }else if(d.after(date) && eDay > cDay) {
                upcomingList.add(CalendarDay.from(eYear, eMonth+1, eDay));
            }else if(d.before(date)){
                completedList.add(CalendarDay.from(eYear, eMonth+1, eDay));
            }
        }
        if(currentList.size() > 0) {
            binding.calendar.addDecorator(new EventDecorator(Color.parseColor("#2bb884"), currentList));
        }
        if(completedList.size() > 0) {
            binding.calendar.addDecorator(new EventDecorator(Color.parseColor("#01579B"), completedList));
        }
        if(upcomingList.size() > 0) {
            binding.calendar.addDecorator(new EventDecorator(Color.parseColor("#0091EA"), upcomingList));
        }

    }


    private void populateList() throws ParseException {

        String inputStringStart = "20-03-2021 06:30 PM";
        String inputStringEnd = "20-03-2021 07:30 PM";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
        Date sparsed = simpleDateFormat.parse(inputStringStart);
        Date eparsed = simpleDateFormat.parse(inputStringEnd);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
        sdf.setTimeZone(cal.getTimeZone());
        String startDate = sdf.format(sparsed);
        String finishDate = sdf.format(eparsed);
        Date date = new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(startDate);
        Date endDate = new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(finishDate);
        List<Speaker> speakers = new ArrayList<>();
        speakers.add(new Speaker(getString(R.string.speaker1),getString(R.string.sq1), R.drawable.isha_dash));
        eventList.add(new Event(getString(R.string.eventtit1), getString(R.string.eventdes_1),
                getString(R.string.eventurl_1),null, date, endDate, speakers));

        inputStringStart = "02-04-2021 05:30 PM";
        inputStringEnd = "02-04-2021 06:30 PM";
        sparsed = simpleDateFormat.parse(inputStringStart);
        eparsed = simpleDateFormat.parse(inputStringEnd);
        startDate = sdf.format(sparsed);
        finishDate = sdf.format(eparsed);
        date = new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(startDate);
        endDate = new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(finishDate);
        List<Speaker> speakers2 = new ArrayList<>();
        speakers2.add(new Speaker(getString(R.string.speaker2), getString(R.string.sq2), R.drawable.jairo_h_garcia));
        eventList.add(new Event(getString(R.string.eventtit2), getString(R.string.eventdes_2),
                getString(R.string.eventurl_2), null, date, endDate, speakers2));

        inputStringStart = "14-04-2021 06:30 PM";
        inputStringEnd = "14-04-2021 07:30 PM";
        sparsed = simpleDateFormat.parse(inputStringStart);
        eparsed = simpleDateFormat.parse(inputStringEnd);
        startDate = sdf.format(sparsed);
        finishDate = sdf.format(eparsed);
        date = new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(startDate);
        endDate = new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(finishDate);
        List<Speaker> speakers3 = new ArrayList<>();
        speakers3.add(new Speaker(getString(R.string.speaker3), getString(R.string.sq3), R.drawable.uti_anselm));

        eventList.add(new Event(getString(R.string.eventtit3), getString(R.string.eventdes_3),
                 getString(R.string.eventurl_3), null, date, endDate, speakers3));

        inputStringStart = "23-04-2021 06:30 PM";
        inputStringEnd = "23-04-2021 07:30 PM";
        sparsed = simpleDateFormat.parse(inputStringStart);
        eparsed = simpleDateFormat.parse(inputStringEnd);
        startDate = sdf.format(sparsed);
        finishDate = sdf.format(eparsed);
        date = new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(startDate);
        endDate = new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(finishDate);
        List<Speaker> speakers4 = new ArrayList<>();
        speakers4.add(new Speaker(getString(R.string.speaker4), getString(R.string.sq4),  R.drawable.md_koushik_ahamed));

        eventList.add(new Event(getString(R.string.eventtit4), getString(R.string.eventdes_4),
                getString(R.string.eventurl_4),null, date, endDate, speakers4));


        inputStringStart = "21-05-2021 06:30 PM";
        inputStringEnd = "21-05-2021 07:30 PM";
        sparsed = simpleDateFormat.parse(inputStringStart);
        eparsed = simpleDateFormat.parse(inputStringEnd);
        startDate = sdf.format(sparsed);
        finishDate = sdf.format(eparsed);
        date = new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(startDate);
        endDate = new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(finishDate);
        List<Speaker> speakers5 = new ArrayList<>();
        speakers5.add(new Speaker(getString(R.string.speaker5_1), getString(R.string.sq5_2),  R.drawable.ic_male));
        speakers5.add(new Speaker(getString(R.string.speaker5_2), getString(R.string.sq5_2),  R.drawable.ic_female));

        eventList.add(new Event(getString(R.string.eventtit5), getString(R.string.eventdes_5),
                getString(R.string.eventurl_4),"https://docs.google.com/forms/d/e/1FAIpQLSclXKyGGev0-3xEZ3uP-N2-yaBuVzbtHnGiJVpLdX6qWd9AJg/viewform?usp=send_form", date, endDate, speakers5));

    }


    private void populateListThroughFirebase() {
        db.collection("timeline").addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("TIMELINE FIREBASE", "Listen failed.", error);
                return;
            }
            if (!value.isEmpty()) {
                dynamicEventList.clear();
                binding.progressBar.setVisibility(View.VISIBLE);
            }
            for (QueryDocumentSnapshot doc : value) {
                if (doc != null) {
                    String inputStringStart = doc.getString("start_time");
                    String inputStringEnd = doc.getString("end_time");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
                    Date sparsed = null, eparsed = null;
                    try {
                        sparsed = simpleDateFormat.parse(inputStringStart);
                        eparsed = simpleDateFormat.parse(inputStringEnd);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                    sdf.setTimeZone(cal.getTimeZone());
                    String startDate = sdf.format(sparsed);
                    String finishDate = sdf.format(eparsed);
                    Date date = null, endDate = null;
                    try {
                        date = new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(startDate);
                        endDate = new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(finishDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    String eventTitle = doc.getString("title");
                    String eventDesc = doc.getString("description");
                    String eventUrl = "";
                    List<String> speakerNames = (List<String>)doc.get("speaker");
                    String speakerDesc = "";
                    List<Speaker> speakers = new ArrayList<>();
                    for(String speakerName: speakerNames)
                        speakers.add(new Speaker(speakerName, speakerDesc, R.drawable.ic_male));
                    dynamicEventList.add(new Event(eventTitle, eventDesc, eventUrl, null, date, endDate, speakers));
                }
            }
            eventList.addAll(dynamicEventList);
            binding.timelineRv.setAdapter(timelineAdapter);
            setUpCalendar();
            populateDisplayList(null, false);
            binding.progressBar.setVisibility(View.GONE);
        });
    }


//
//    private void populateListThroughAPI() throws ParseException {
//
//        Call<JSONArray> webinarData = Service.api.getWebinarDetails();
//        webinarData.enqueue(new Callback<JSONArray>() {
//            @Override
//            public void onResponse(Call<JSONArray> call, Response<JSONArray> response) {
//                try {
//                    Log.d("TRY GHSUH RHA H", "fuck off");
//                    Log.d("RESPONSE", String.valueOf(response.body()));
//                    JSONArray webinars = response.body();
//                    for (int i = 0; i < webinars.length(); i++) {
//                        JSONObject jsonObject = webinars.getJSONObject(i);
////                        String inputStringStart = webinarItem.getWebinarStartTime();
////                        String inputStringEnd = webinarItem.getWebinarEndTime();
////                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
////                        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
////                        Date sparsed = null, eparsed = null;
////                        try {
////                            sparsed = simpleDateFormat.parse(inputStringStart);
////                            eparsed = simpleDateFormat.parse(inputStringEnd);
////                        } catch (ParseException e) {
////                            e.printStackTrace();
////                        }
////                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
////                        sdf.setTimeZone(cal.getTimeZone());
////                        String startDate = sdf.format(sparsed);
////                        String finishDate = sdf.format(eparsed);
////                        Date date = null, endDate = null;
////                        try {
////                            date = new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(startDate);
////                            endDate = new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(finishDate);
////                        } catch (ParseException e) {
////                            e.printStackTrace();
////                        }
////
////                        String eventTitle = webinarItem.getWebinarTitle();
////                        String eventDesc = webinarItem.getWebinarDesc();
////                        String eventUrl = webinarItem.getWebinarLink();
////                        String speakerName = webinarItem.getWebinarSpeaker();
////                        String speakerDesc = webinarItem.getWebinarSpeakerDes();
////                        List<Speaker> speakers = new ArrayList<>();
////                        speakers.add(new Speaker(speakerName, speakerDesc, R.drawable.ic_male));
////                        eventList.add(new Event(eventTitle, eventDesc, eventUrl, null, date, endDate, speakers));
//                    }
//
//                }catch (Exception e){
//                    Log.d("Webinar Data", e.toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<JSONArray> call, Throwable t) {
//                Log.d("Webinar Data", "onFailure", t);
//            }
//        });
//
//    }

//
//    private void populateListThroughAPI() throws ParseException {
//
//        RequestQueue queue = Volley.newRequestQueue(getActivity());
//        String webinarAPI = "https://ieeeyesist12.org/phpApp/webinar.php";
//
//        JsonArrayRequest request = new JsonArrayRequest(webinarAPI,
//                jsonArray -> {
//                    Log.d("Response", String.valueOf(jsonArray));
//                    for(int i = 0; i < jsonArray.length(); i++) {
//                        try {
//                            JSONObject webinarItem = jsonArray.getJSONObject(i);
//                            String inputStringStart = webinarItem.getString("webinar_start_time");
//                            String inputStringEnd = webinarItem.getString("webinar_end_time");
//                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
//                            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT+5:30"));
//                            Date sparsed = null, eparsed = null;
//                            try {
//                                sparsed = simpleDateFormat.parse(inputStringStart);
//                                eparsed = simpleDateFormat.parse(inputStringEnd);
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//                            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
//                            sdf.setTimeZone(cal.getTimeZone());
//                            String startDate = sdf.format(sparsed);
//                            String finishDate = sdf.format(eparsed);
//                            Date date = null, endDate = null;
//                            try {
//                                date = new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(startDate);
//                                endDate = new SimpleDateFormat("dd-MM-yyyy hh:mm a").parse(finishDate);
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//
//                            String eventTitle = webinarItem.getString("webinar_title");
//                            String eventDesc = webinarItem.getString("webinar_desc");
//                            String eventUrl = webinarItem.getString("webinar_link");
//                            String speakerName = webinarItem.getString("webinar_speaker");
//                            String speakerDesc = webinarItem.getString("webinar_speaker_iurl");
//                            List<Speaker> speakers = new ArrayList<>();
//                            speakers.add(new Speaker(speakerName, speakerDesc, R.drawable.ic_male));
//                            eventList.add(new Event(eventTitle, eventDesc, eventUrl, null, date, endDate, speakers));
//                        } catch(JSONException e) {
//                                Log.e("Error", e.toString());
//                        }
//                    }
//                },
//                volleyError -> Log.e("Error", volleyError.toString()));
//
//        queue.add(request);
//
//    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentTimelineBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onEventClick(Event event) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext(), R.style.bottomSheetDialogTheme);
        View bottomSheetView = LayoutInflater.from(requireContext())
                .inflate(R.layout.bottom_sheet, bottomSheetDialog.findViewById(R.id.constraint_layout));

        TextView eventName = bottomSheetView.findViewById(R.id.event_name);
        RecyclerView speakerRv = bottomSheetView.findViewById(R.id.speaker_rv);
        TextView about = bottomSheetView.findViewById(R.id.about_content);
        TextView eventDate = bottomSheetView.findViewById(R.id.event_date);
        TextView eventTime = bottomSheetView.findViewById(R.id.event_time);

        eventName.setText(event.getEventName());
        about.setText(event.getAboutEvent());
        SpeakerAdapter speakerAdapter = new SpeakerAdapter(event.getSpeakers());
        speakerRv.setAdapter(speakerAdapter);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
        String date = sdf.format(event.getDate());
        eventDate.setText(date);

        sdf = new SimpleDateFormat("hh:mm");
        String time = sdf.format(event.getDate());
        time += " - ";
        sdf = new SimpleDateFormat("hh:mm aa");
        time += sdf.format(event.getEndDate());
        eventTime.setText(time);

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }
}