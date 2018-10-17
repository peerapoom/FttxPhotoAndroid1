package com.codewithdio.fttxphotoandroid1;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

import static com.codewithdio.fttxphotoandroid1.R.id.tvLatLng;
import static com.codewithdio.fttxphotoandroid1.R.id.view_root;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    private Button buttonSiteName;
    private Button buttonSprTyp;
    private Button buttonSprName;
    private Button buttonGrid;
    private Button buttonRemark;
    private TextView textView5LatLng;
    private TextView textViewDate;
    private ImageButton camera_btn;
    private ImageButton save_btn;
    private ImageView imageView1;
    private ImageView img;
    private ImageView img1;
    private ViewGroup rootLayout;
    private int _xDelta;
    private int _yDelta;
    private static long UPDATE_INTERVAL = 5000;
    private static long FASTEST_INTERVAL = 1000;
    private GoogleApiClient mApiClient;
    private LocationRequest mRequest;

    static final int CAM_REQUEST = 1;
    private String mCurrentLocStr;

    public static void main(String[] args) {
        String text0 = "Site Name";

        String text1 = "Splitter Type";

        String text2 = "Splitter Name";

        String text3 = "Drawing Grid";

        String text4 = "Latitude";

        String text5 = "Longitude";

        String text6 = "Remark";

        String[] arrayText = {text0, text1, text2, text3, text4, text5, text6};

        String activityCheckCase = "SiteNameCase";


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();


        camera_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                // Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //  File file = getFile();

                //   camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

                //  startActivityForResult(camera_intent,CAM_REQUEST);

                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, CAM_REQUEST);
                }
            }
        });

        save_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                takeScreenshot();
                String imagePath = null;
                Bitmap imageBitmap = screenShot(rootLayout);
                if (imageBitmap != null) {
                    imagePath = MediaStore.Images.Media.insertImage(getContentResolver(), imageBitmap, "title", null);

                }
            }
        });


    }

    public Bitmap screenShot(View view) {
        if (view != null) {
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(),
                    view.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            return bitmap;
        }
        return null;
    }
    @Override
    protected void onResume() {
        super.onResume();
        checkRuntimePermission();
        MainActivity.requestOpenGPS(this);
        getLastLocation();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mApiClient != null) {
            mApiClient.disconnect();
        }

    }

    public static void requestOpenGPS(Context context) {
        String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { //if gps is disabled
            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            Toast.makeText(context, "Please turn on Location Tracking", Toast.LENGTH_SHORT).show();
        }
    }

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    private void initInstances() {

        int scrWidth = getScreenWidth();

        int scrHight = getScreenHeight();

        String text0 = "Site Name";

        String text1 = "Splitter Type";

        String text2 = "Splitter Name";

        String text3 = "Drawing Grid";

        String text4 = "Lat";

        String text5 = "Long";

        String text6 = "Remark";

        String[] arrayText = {text0, text1, text2, text3, text4, text5, text6};
        sp = getSharedPreferences("PREFNAME", Context.MODE_PRIVATE);
        arrayText[0] = sp.getString("SiteNameSaved", "SiteName");
        arrayText[1] = sp.getString("SplitterTypeSaved", "SplitterType");
        arrayText[2] = sp.getString("SplitterNameSaved", "SplitterName");
        arrayText[3] = sp.getString("DrawingGridSaved", "DrawingGrid");
        arrayText[4] = sp.getString("Lat","Latitude");
        arrayText[5] = sp.getString("Long", "Longitude");
        arrayText[6] = sp.getString("RemarkSaved", "ระบุหมายเหตุ(ถ้ามี)");
        buttonSiteName = (Button) findViewById(R.id.btnSiteName);
        buttonSiteName.setText("Site Name : " + arrayText[0]);
        buttonSprTyp = (Button) findViewById(R.id.btnSplitterType);
        buttonSprTyp.setText("Splitter Type : " + arrayText[1]);
        buttonSprName = (Button) findViewById(R.id.btnSplitterName);
        buttonSprName.setText("Splitter Name : " + arrayText[2]);
        buttonGrid = (Button) findViewById(R.id.btnDwgGrid);
        buttonGrid.setText("Dwg Grid : " + arrayText[3]);
        buttonRemark = (Button) findViewById(R.id.btnRemark);
        buttonRemark.setText("Remark : " + arrayText[6]);
        textView5LatLng = (TextView) findViewById(tvLatLng);
        textView5LatLng.setText("Lat : " + arrayText[4] + ", Long : " + arrayText[5]);
        textViewDate = (TextView) findViewById(R.id.tvDate);


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss a");
        dateFormat.setTimeZone(Calendar.getInstance().getTimeZone());
        Log.d("date:%s", dateFormat.format(System.currentTimeMillis()));


        textViewDate.setText(dateFormat.format(System.currentTimeMillis()));
        // textViewDate.setTextColor(0xFFF06D2F);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/DS-DIGI.TTF");
        textViewDate.setTypeface(font);


        camera_btn = (ImageButton) findViewById(R.id.btnCamera);
        imageView1 = (ImageView) findViewById(R.id.imageView0);
        save_btn = (ImageButton)findViewById(R.id.btnSave);
        rootLayout = (ViewGroup) findViewById(view_root);
        img = (ImageView) rootLayout.findViewById(R.id.imgGreenArrow1);
        img1 = (ImageView) rootLayout.findViewById(R.id.imgRedArrow1);

        //  RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(135, 135);
        //  img.setLayoutParams(layoutParams);
        //   layoutParams.setMargins(21, 1395, 0, 0);



        img1.setOnTouchListener(new ChoiceTouchListener());

        RelativeLayout.LayoutParams layoutParams1 = new RelativeLayout.LayoutParams(135, 135);
        //  layoutParams1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //layoutParams1.setMargins(240,0,0,0);
        img1.setLayoutParams(layoutParams1);
        //  int valueWidth1 = (int) (Math.round( (0.1*scrWidth) * 100.0 ) / 100.0);
        // int finalValueWidth1 = (int) valueWidth1;
        int redx = (int) Math.round(0.25 * scrWidth);
        int redy = (int) Math.round(0.9 * scrHight);
        layoutParams1.setMargins(redx, redy, 0, 0);

        img.setOnTouchListener(new ChoiceTouchListener());


        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(135, 135);
        // layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        //  int valueWidth = (int) (Math.round( (0.25*scrWidth) * 100.0 ) / 100.0);
        //int finalValueWidth =  (int) valueWidth;

        int greenx = (int) Math.round(0.022 * scrWidth);
        int greeny = (int) Math.round(0.9 * scrHight);
        layoutParams.setMargins(greenx, greeny, 0, 0);

     /*   Toast.makeText(getApplicationContext(),
                "scrWidth: " +
                        scrWidth + "scrHeight:" + scrHight,
                Toast.LENGTH_SHORT).show();*/
        img.setLayoutParams(layoutParams);
    }


    private File getFile() {
        File folder = new File("sdcard/fttxOnsitePhoto");
        if (!folder.exists()) {
            folder.mkdir();
        }

        File image_file = new File(folder, "camera_image.jpg");
        return image_file;
    }

    public void SiteName(View view) {
        Button btn_sn = (Button) findViewById(R.id.btnSiteName);
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);
        finish();
    }

    public void SplitterType(View view) {
        Button btn_sptyp = (Button) findViewById(R.id.btnSplitterType);
        Intent intent = new Intent(MainActivity.this, Main6Activity.class);
        startActivity(intent);
        finish();
    }

    public void SplitterName(View view) {
        Button btn_sptr = (Button) findViewById(R.id.btnSiteName);
        Intent intent = new Intent(MainActivity.this, Main3Activity.class);
        startActivity(intent);
        finish();
    }

    public void DrawingGrid(View view) {
        Button btn_dwg = (Button) findViewById(R.id.btnDwgGrid);
        Intent intent = new Intent(MainActivity.this, Main4Activity.class);
        startActivity(intent);
        finish();
    }

    public void Remark(View view) {
        Button btn_rmk = (Button) findViewById(R.id.btnRemark);
        Intent intent = new Intent(MainActivity.this, Main5Activity.class);
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();


            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView1.setImageBitmap(imageBitmap);
            //  imageView1.setImageDrawable(imageBitmap);
        }

        //   String path = "sdcard/fttxOnsitePhoto/camera_image.jpg";
        //  imageView1.setImageDrawable(Drawable.createFromPath(path));
    }

    private final class ChoiceTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent event) {


            final int X = (int) event.getRawX();
            final int Y = (int) event.getRawY();
            switch (event.getAction() & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:
                    RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                    _xDelta = X - lParams.leftMargin;
                    _yDelta = Y - lParams.topMargin;
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                case MotionEvent.ACTION_POINTER_DOWN:
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    break;
                case MotionEvent.ACTION_MOVE:
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view
                            .getLayoutParams();
                    layoutParams.leftMargin = X - _xDelta;
                    layoutParams.topMargin = Y - _yDelta;
                    layoutParams.rightMargin = -250;
                    layoutParams.bottomMargin = -250;
                    view.setLayoutParams(layoutParams);
                    break;
            }
            rootLayout.invalidate();
            return true;
        }
    }

    private void checkRuntimePermission() {
        Nammu.init(this);
        String[] permissions = new String[]{Manifest.permission.ACCESS_FINE_LOCATION};
        Nammu.askForPermission(this, permissions, new PermissionCallback() {
            @Override
            public void permissionGranted() {
                startLocationTracking();
            }

            @Override
            public void permissionRefused() {
                finish();
            }
        });
    }

    public void startLocationTracking() {

       // mMapView.setMyLocationEnabled(true);

        mApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {


                        // get last location
                        getLastLocation();

                        // set request
                        mRequest = LocationRequest.create();
                        mRequest.setInterval(UPDATE_INTERVAL);
                        mRequest.setFastestInterval(FASTEST_INTERVAL);
                        mRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        LocationServices.FusedLocationApi.requestLocationUpdates(mApiClient, mRequest, new LocationListener() {
                            @Override
                            public void onLocationChanged(Location location) {

                                updateLocationTextView(location);
                            }
                        });
                    }

                    @Override
                    public void onConnectionSuspended(int i) {
                        Toast.makeText(getApplicationContext(), "Connection is susppended!", Toast.LENGTH_LONG).show();

                    }
                }).build();

        mApiClient.connect();

    }

    @SuppressWarnings({"MissingPermission"})
    public Location getLastLocation() {
        Location location = LocationServices.FusedLocationApi.getLastLocation(
                mApiClient);
        if (location != null) {
            updateLocationTextView(location);
        }
        return location;
    }

    private void updateLocationTextView(Location location) {
        http:
//192.168.1.13/uploads/up.php

        if (location != null) {

            DecimalFormat formatter = new DecimalFormat("#,###.00000000");
            final String lat = formatter.format(location.getLatitude());
            final String lng = formatter.format(location.getLongitude());

            mCurrentLocStr = String.format("Lat: %s°, Long: %s°", lat, lng);
            textView5LatLng.setText(mCurrentLocStr);
        }
    }

    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
        
    }

    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }


}
