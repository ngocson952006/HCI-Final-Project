package com.example.tns.realestate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.tns.realestate.models.ApartmentDetail;

public class ApartmentDetailActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_apartment_detail);
        setSupportActionBar(toolbar);
        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = this.getIntent();
        ApartmentDetail detail = intent.getParcelableExtra("detail_apartment");
        SliderLayout sliderLayout = (SliderLayout) this.findViewById(R.id.image_slider);
        // make sure we have valid param
        if (detail != null) {

            // prepare all text data
            this.prepareViewTextData(detail);

            // prepare data from slider
            for (int index = 0; index < detail.getBitmapsResource().length; ++index) {
                TextSliderView textSliderView = new TextSliderView(this);
                // initialize a SliderLayout
                textSliderView
                        .description("")
                        .image(detail.getBitmapsResource()[index])
                        .setScaleType(BaseSliderView.ScaleType.Fit)
                        .setOnSliderClickListener(this);

                //add your extra information
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle()
                        .putString("extra", null);

                sliderLayout.addSlider(textSliderView);
            }
        }

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        // sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setDuration(8000);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Liên hệ với bên cung cấp", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Google maps button
        Button buttonGoogleMaps = (Button) this.findViewById(R.id.button_detail_google_maps);
        buttonGoogleMaps.setOnClickListener(this);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        // doing nothing
    }

    /**
     * Bind view components and set data
     *
     * @param detail : Detail of an apartment to be shown
     */
    private void prepareViewTextData(ApartmentDetail detail) {
        final TextView textViewPrice = (TextView) this.findViewById(R.id.textview_detail_price);
        textViewPrice.setText(detail.getPrice());
        final TextView textViewAddress = (TextView) this.findViewById(R.id.textview_detail_address);
        textViewAddress.setText(detail.getAddress());
        final TextView textViewBedsRooms = (TextView) this.findViewById(R.id.textview_detail_number_of_bedrooms);
        textViewBedsRooms.setText(String.valueOf(detail.getNumberOfBedRooms()));
        final TextView textViewBathRooms = (TextView) this.findViewById(R.id.textview_detail_number_of_bathrooms);
        textViewBathRooms.setText(String.valueOf(detail.getNumberOfBathRooms()));
        final TextView textViewArea = (TextView) this.findViewById(R.id.textview_detail_area);
        textViewArea.setText("Diện tích : " + String.valueOf(detail.getArea()) + "m2");
        final TextView textViewDescription = (TextView) this.findViewById(R.id.textview_detail_description);
        textViewDescription.setText(String.valueOf(detail.getDescription()));
        final AppCompatRatingBar appCompatRatingBar = (AppCompatRatingBar) this.findViewById(R.id.detail_ratingbar);
        appCompatRatingBar.setRating(detail.getRating());
        final TextView textViewFloor = (TextView) this.findViewById(R.id.textview_detail_floor);
        textViewFloor.setText("Lầu : " + detail.getFloor());

        // about agent information
        final TextView textViewAgentName = (TextView) this.findViewById(R.id.textview_agent_name);
        textViewAgentName.setText(detail.getProvider().getName());
        final TextView textViewAgentAddress = (TextView) this.findViewById(R.id.textview_agent_address);
        textViewAgentAddress.setText("Địa chỉ: " + detail.getProvider().getAddress());
        final TextView textViewAgentPhone = (TextView) this.findViewById(R.id.textview_agent_phone);
        textViewAgentPhone.setText("Liên hệ: " + detail.getProvider().getContact());

        // additional information
        final TextView textViewNeighbourhoodInformation = (TextView) this.findViewById(R.id.textview_neighbourhood_information);
        textViewNeighbourhoodInformation.setText(detail.getNeighbourhoodInformation());
        final TextView textViewNearbySchoolInformation = (TextView) this.findViewById(R.id.textview_nearby_school_information);
        textViewNearbySchoolInformation.setText(detail.getNearBySchoolInformation());

    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.button_detail_google_maps) {
            // navigate to google maps android
            Uri googleMapsUri = Uri.parse("geo:37.7749,-122.4194");
            Intent googleMapsIntent = new Intent(Intent.ACTION_VIEW, googleMapsUri);
            googleMapsIntent.setPackage("com.google.android.apps.maps");
            // make sure package is available on device
            if (googleMapsIntent.resolveActivity(this.getPackageManager()) != null) {
                this.startActivity(googleMapsIntent);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // animation when navigating back to main activity
        this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = this.getMenuInflater();
        menuInflater.inflate(R.menu.menu_apartment_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                            // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                    this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
