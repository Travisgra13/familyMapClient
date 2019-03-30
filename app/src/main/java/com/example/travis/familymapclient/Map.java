package com.example.travis.familymapclient;

import android.content.Context;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travis.familymapclient.Model.Event;
import com.example.travis.familymapclient.Model.Person;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class Map extends Fragment implements OnMapReadyCallback {
    private GoogleMap map;
    private DataCache dataCache;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    private TextView personName;
    private TextView personGender;
    private TextView eYear;
    private TextView eType;
    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        dataCache = DataCache.getInstance();
        View view = layoutInflater.inflate(R.layout.fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        personName = view.findViewById(R.id.personName);
        personGender = view.findViewById(R.id.birthGender);
        eYear = view.findViewById(R.id.eventYear);
        eType = view.findViewById(R.id.eventType);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        personGender.setText(getString(R.string.default1));
        eType.setText(getString(R.string.default2));
// Add a marker in Sydney and move the camera

       /* LatLng sydney = new LatLng(-34, 151);
        map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        map.animateCamera(CameraUpdateFactory.newLatLng(sydney));*/
       createAllMarkers();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private void createAllMarkers() {
        final ArrayList<Event> events = dataCache.getEventData();
        for (int i = 0; i < events.size(); i++) {
            final Event currentEvent = events.get(i);
            LatLng eventLoc = new LatLng(currentEvent.getLatitude(), currentEvent.getLongitude());
            Marker myMarker = map.addMarker(new MarkerOptions().position(eventLoc).title("Marker of " + currentEvent.getPerson()));
            map.setOnMarkerClickListener(MarkerClickListener);
            myMarker.setTag(events.get(i));
        }
    }

    private final GoogleMap.OnMarkerClickListener MarkerClickListener = new GoogleMap.OnMarkerClickListener() {

        @Override
        public boolean onMarkerClick(Marker marker) {
            Event currentEvent = (Event) marker.getTag();
            Person currPerson = findPerson(currentEvent.getPerson());
            personName.setText(currPerson.getFirstName() + " " + currPerson.getLastName());
            personGender.setText("Gender: " + currPerson.getGender().toUpperCase());
            eType.setText("Event: " + currentEvent.getEventType());
            eYear.setText("(" + currentEvent.getYear().toString() + ")");
            map.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            return true;
        }
    };


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.familymapmenu, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.searchMenuItem);

    }

    public Person findPerson(String personid) {
        ArrayList <Person> persons = dataCache.getPeopleData();
        for (int i = 0; i < persons.size(); i++) {
            if (personid.equals(persons.get(i).getPersonID())) {
                return persons.get(i);
            }
        }
        return null;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        switch(menu.getItemId()) {
            case R.id.searchMenuItem:
                Toast.makeText(this.getContext(), "Touched search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.filterMenuItem:
                Toast.makeText(this.getContext(), "Touched filters", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.SettingsMenuItem:
                Toast.makeText(this.getContext(), "Touched Settings", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }
}
