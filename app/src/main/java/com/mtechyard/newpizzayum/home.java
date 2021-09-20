package com.mtechyard.newpizzayum;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.mtechyard.newpizzayum.adapters.homeProductsAdapter;
import com.mtechyard.newpizzayum.adapters.homeMenuAdapter;
import com.mtechyard.newpizzayum.adapters.productToppingListAdapter;
import com.mtechyard.newpizzayum.project_rec.GlobalFunctions;
import com.mtechyard.newpizzayum.project_rec.TinyDB;
import com.mtechyard.newpizzayum.project_rec.MyDialog;
import com.mtechyard.newpizzayum.project_rec.RequestResponse;
import com.mtechyard.newpizzayum.project_rec.EditComponents;
import com.mtechyard.newpizzayum.project_rec.Toppings;
import com.mtechyard.newpizzayum.project_rec.UserOrderList;
import com.mtechyard.newpizzayum.project_rec.myLinks;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class home extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    private static Activity myActivity;
    private static int productSizeRate;
    private static String productSize;
    private static List<Toppings> productToppingList;
    private static int productToppingTotal;
    private static boolean toppingAvailable;
    private static int toppingPosition;
    @SuppressLint("StaticFieldLeak")
    private static View productBottomSheetView;

    //init vars
    @SuppressLint("StaticFieldLeak")
    protected static MyDialog myDialog;
    ImageView menu_btn;
    NavigationView nv;
    FusedLocationProviderClient fusedLocationProviderClient;
    protected static GlobalFunctions globalFunctions;
    View hView;
    TextView UserName, UserMobile;
    TinyDB tinyDB;
    EditComponents ec;
    BottomSheetDialog bottomSheetDialog;
    public static RecyclerView recyclerView;
    Gson gson;
    Context context;

    // DO NOT REMOVE THIS VARIABLE
    public static boolean MENU_SHOWING;

    public String SaveDataKey = "key01";
    protected static String Filter;


    protected static int dpWidth;
    protected static int itemCount;

    // Do not remove this json array because this hold json array response
    protected static JSONArray ProductJsonArray;

    // Do not remove this array list because this hold display product data
    protected static List<RequestResponse> pData;

    @SuppressLint("StaticFieldLeak")
    protected static Activity thisActivity;

    protected RecyclerView homeMenuRecyclerView;
    private Spinner spinner;
    private String[] spinnerData;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        // init function
        myDialog = new MyDialog(this);
        globalFunctions = new GlobalFunctions(this);
        tinyDB = new TinyDB(this);
        ec = new EditComponents(home.this);
        gson = new Gson();

        thisActivity = this;
        // init list
        pData = new ArrayList<>();
        ProductJsonArray = new JSONArray();

        context = getApplicationContext();
        myActivity = home.this;

        spinner = findViewById(R.id.select_locality);

        homeMenuRecyclerView = findViewById(R.id.homeMenuRecyclerView);
        homeMenuRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        homeMenuRecyclerView.setAdapter(new homeMenuAdapter());


        //
        bottomSheetDialog = new BottomSheetDialog(home.this, R.style.bottom_sheet_with_round_corner_theme);

        if (tinyDB.getBucketItemCount() > 0) {
            ec.setLinearLayoutVisibility(R.id.bucket_count_dot, View.VISIBLE);
            ec.setText(R.id.bucket_count, String.valueOf(tinyDB.getBucketItemCount()));
        } else {
            ec.setLinearLayoutVisibility(R.id.bucket_count_dot, View.GONE);
        }


        // init views
        recyclerView = findViewById(R.id.product_recyclerView);

        //location api
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        //menu button
        menu_btn = findViewById(R.id.open_menu_icon);

        // finding drawer layout
        DrawerLayout d = (DrawerLayout) findViewById(R.id.home_drawer);
        menu_btn.setOnClickListener(v -> d.openDrawer(GravityCompat.START));


        nv = findViewById(R.id.home_nav_bar);
        hView = nv.getHeaderView(0);

        UserName = (TextView) hView.findViewById(R.id.nav_user_name_text);
        UserMobile = (TextView) hView.findViewById(R.id.nav_user_mobile_text);

        UserName.setText(tinyDB.getUserName());
        UserMobile.setText(tinyDB.getUserMobileNo());

        nv.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.nav_home:
                    //Toast.makeText(home.this, "Home Clicked", Toast.LENGTH_SHORT).show();
                    break;

//                case R.id.nav_profile:
//                    startActivity(new Intent(home.this,user_profile.class));
//                    Toast.makeText(home.this, "Profile Clicked", Toast.LENGTH_SHORT).show();
//                    break;
//
//                case R.id.nav_wallet:
//                    startActivity(new Intent(home.this,user_profile.class));
//                    Toast.makeText(home.this, "Wallet Clicked", Toast.LENGTH_SHORT).show();
//                    break;

                case R.id.nav_order:
                    startActivity(new Intent(home.this, user_order.class));
                    //Toast.makeText(home.this, "Order Clicked", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.nav_address:
                    startActivity(new Intent(home.this, user_address.class));
                    ///Toast.makeText(home.this, "Address Clicked", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_rateapp:
                    //Toast.makeText(home.this, "Rate App Clicked", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.nav_shareapp:
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Hii Use this app to order a pizza :)");
                    sendIntent.setType("text/plain");
                    Intent.createChooser(sendIntent, "Share via");
                    startActivity(sendIntent);
                    break;
                case R.id.nav_contact_us:
                    Uri uriUrl = Uri.parse("https://newpizzayum.in/contactus.html");
                    Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                    startActivity(launchBrowser);
                    break;

                case R.id.nav_about_us:
                    Uri uriUrl1 = Uri.parse("https://newpizzayum.in/aboutus.html");
                    Intent launchBrowser1 = new Intent(Intent.ACTION_VIEW, uriUrl1);
                    startActivity(launchBrowser1);
                    break;

                case R.id.nav_login:
                    //Toast.makeText(home.this, "Log-in Clicked", Toast.LENGTH_SHORT).show();
                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                    firebaseAuth.signOut();
                    tinyDB.ClearDB1();
                    finish();
                    break;

            }

            return true;
        });

        if (savedInstanceState == null) {
            checkUserLocation();
        }
        manageOprations(savedInstanceState);

        setSpinner();


    }

    private void manageOprations(Bundle savedInstanceState) {

        MENU_SHOWING = true;

        if (savedInstanceState != null) {
            ProductJsonArray = new JSONArray();
            RequestResponse[] json = gson.fromJson(savedInstanceState.getString(SaveDataKey), RequestResponse[].class);
            for (RequestResponse requestResponse : json) {
                JSONObject data = new JSONObject();
                try {
                    data.put("productId", requestResponse.getProductId());
                    data.put("productName", requestResponse.getProductName());
                    data.put("productImage", requestResponse.getProductImage());
                    data.put("category", requestResponse.getCategory());
                    data.put("dPrice", requestResponse.getDisplayPrice());
                    data.put("customizable", requestResponse.getCustomizable());
                    data.put("sPrice", requestResponse.getsPrice());
                    data.put("mPrice", requestResponse.getmPrice());
                    data.put("lPrice", requestResponse.getlPrice());
                    data.put("productTax", requestResponse.getProductTax());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ProductJsonArray.put(data);

            }

        } else {
            if (!MENU_SHOWING) {

                // showing loading dialog
                myDialog.showLoadingDialog();
                loadProductsDataFromServers();
            }
        }


    }

    public void checkUserLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                myDialog.dismissLoadingDialog(1);
                ActivityCompat.requestPermissions(home.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                getLocationParams();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0) {
            getLocationParams();
        }
    }

    @SuppressLint("MissingPermission")
    public void getLocationParams() {

        //Initialize location manager
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // check condition

        assert locationManager != null;
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {

                    Location location = task.getResult();
                    if (location != null) {
                        double lat = location.getLatitude();
                        double longt = location.getLongitude();

                        getUserAddress(lat, longt);


                    } else {
                        //
                        LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);

                        LocationCallback locationCallback = new LocationCallback() {
                            @Override
                            public void onLocationResult(@NonNull LocationResult locationResult) {
                                Location location1 = locationResult.getLastLocation();

                                if (location1 != null) {
                                    double lat = location1.getLatitude();
                                    double longt = location1.getLongitude();

                                    getUserAddress(lat, longt);
                                } else {
                                    Toast.makeText(home.this, "Something in getting location", Toast.LENGTH_SHORT).show();
                                }

                            }
                        };

                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Objects.requireNonNull(Looper.myLooper()));
                    }

                }
            });
        } else {
            Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
        }

    }

    public void getUserAddress(double lat, double longt) {

        try {
            Geocoder geocoder = new Geocoder(home.this, Locale.getDefault());
            List<Address> address = geocoder.getFromLocation(lat, longt, 1);

            Address ADDRESS = address.get(0);


            TinyDB tinyDB = new TinyDB(home.this);

            tinyDB.saveUserLocality(ADDRESS.getLocality());

            checkUserLocationAvailable(ADDRESS.getLocality());

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void checkUserLocationAvailable(String UserLocality) {

        TextView t = findViewById(R.id.userLocality);
        t.setText(UserLocality);

        ConstraintLayout l = findViewById(R.id.noService);

        StringRequest localityCheckRequest = new StringRequest(Request.Method.POST, myLinks.AREA_CHECK, response -> {

            Gson gson = new Gson();
            RequestResponse data = gson.fromJson(response, RequestResponse.class);

            if (data.getResult().equals("success")) {
                if (!data.getLocalityCheckResponse()) {
                    l.setVisibility(View.VISIBLE);

                } else {
                    l.setVisibility(View.GONE);
                }

            } else {
                Log.e("localityCheck", "Check Response Comes Failed" + data.getResult());
            }

            myDialog.dismissLoadingDialog(1);

        }, error -> {

            Log.e("localityCheck", "Locality check request failed because => " + error.getMessage());
            myDialog.dismissLoadingDialog(1);

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("locality", UserLocality);
                return params;
            }

        };

        RequestQueue loadUiRequest = Volley.newRequestQueue(home.this);
        loadUiRequest.add(localityCheckRequest);

        myDialog.dismissLoadingDialog(1);


    }

    public void openHomeRestaurantMenu(View view) {

        View menuView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.home_menu_bottom_sheet_view,
                        findViewById(R.id.home_menu_bottom_sheet_view));

        // close bottom sheet dialog form close btn  
        menuView.findViewById(R.id.closeBtn).setOnClickListener(v -> bottomSheetDialog.dismiss());

        ListView listView = menuView.findViewById(R.id.categoryList);

        String[] category = {"Pizzas", "Pizza Mania", "Tandoori Flavour Pizza", "Masala Pizza", "Beverages", "Side"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, category);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            setAndShowProducts(category[position].toUpperCase());
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.setContentView(menuView);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.show();

    }

    private static void loadProductsDataFromServers() {

        String url = "https://mtechyard.com/newpizzayum-app/products.php";
        if (ProductJsonArray.length() == 0) {
            //Toast.makeText(myActivity, "calling to server", Toast.LENGTH_SHORT).show();
            JsonArrayRequest jsonObjectRequest = new JsonArrayRequest
                    (Request.Method.GET, url, null, response -> {
                        //TODO: Handle success response
                        if (response.length() <= 0) {
                            //TODO: show no data to show layout
                        } else {
                            ProductJsonArray = response;
                            setAndShowProducts(Filter);

                        }

                    }, error -> {
                        // TODO: Handle error
                        myDialog.dismissLoadingDialog(1);
                        Toast.makeText(thisActivity, "Something went wrong,Please after some time!", Toast.LENGTH_SHORT).show();
                    });

            RequestQueue loadUiRequest = Volley.newRequestQueue(thisActivity);
            loadUiRequest.add(jsonObjectRequest);
        }

    }


    public static void setAndShowProducts(String filter) {


        MENU_SHOWING = false;

        pData = new ArrayList<>();
        Filter = filter.trim();

        if (ProductJsonArray.length() >= 1) {
            dpWidth = globalFunctions.clintWidth();
            itemCount = 2;
            if (dpWidth > 540) {
                itemCount = 3;
            }
            if (dpWidth > 720) {
                itemCount = 4;
            }

            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(itemCount, StaggeredGridLayoutManager.VERTICAL));

            for (int i = 0; i < ProductJsonArray.length(); i++) {
                try {
                    JSONObject data = ProductJsonArray.getJSONObject(i);

                    RequestResponse productData = new RequestResponse(
                            data.getInt("productId"),
                            data.getString("productName"),
                            data.getString("productImage"),
                            data.getString("category"),
                            data.getInt("dPrice"),
                            data.getBoolean("customizable"),
                            data.getInt("sPrice"),
                            data.getInt("mPrice"),
                            data.getInt("lPrice"),
                            data.getInt("productTax"));


                    if (Filter.toUpperCase().equals(data.getString("category"))) {
                        pData.add(productData);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(thisActivity, "Some Error With Massage " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


            Collections.sort(pData, (o1, o2) -> o1.getDisplayPrice().compareTo(o2.getDisplayPrice()));


            recyclerView.setAdapter(new homeProductsAdapter(thisActivity, pData));
            myDialog.dismissLoadingDialog(1000);

        } else {
            myDialog.dismissLoadingDialog(1000);
            loadProductsDataFromServers();
        }


        EditComponents.setLinearLayoutVisibility(thisActivity, R.id.home_menu, View.GONE);
        EditComponents.setConstraintLayoutVisibility(thisActivity, R.id.__product, View.VISIBLE);

    }

    @Override
    public void onBackPressed() {

        if (!MENU_SHOWING) {
            EditComponents.setLinearLayoutVisibility(this, R.id.home_menu, View.VISIBLE);
            EditComponents.setConstraintLayoutVisibility(this, R.id.__product, View.GONE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SaveDataKey, ProductJsonArray.toString());
    }

    public static void changeBucketCountInUi() {

        TextView v = myActivity.findViewById(R.id.bucket_count);
        int c = new TinyDB(myActivity.getApplicationContext()).getBucketItemCount();
        if (c == 0) {
            new EditComponents(myActivity).setLinearLayoutVisibility(R.id.bucket_count_dot, View.GONE);
        } else {
            new EditComponents(myActivity).setLinearLayoutVisibility(R.id.bucket_count_dot, View.VISIBLE);
            v.setText(String.valueOf(c));
        }

    }

    int r;

    @SuppressLint("SetTextI18n")
    public static void toggleBottomSheetDialogForProductEdit(int productId, String productName, String productImage, int dPrice, int s, int m, int l, int productTax) {

        BottomSheetDialog bsd = new BottomSheetDialog(myActivity, R.style.bottom_sheet_with_round_corner_theme);
        View view = LayoutInflater.from(myActivity.getApplicationContext())
                .inflate(R.layout.home_product_edit_bottom_sheet_dialog,
                        myActivity.findViewById(R.id.home_menu_bottom_sheet_view));

        ImageView cBtn = view.findViewById(R.id.closeBtn);
        cBtn.setOnClickListener(v1 -> {
            bsd.dismiss();
        });

        view.findViewById(R.id.showToppings).setOnClickListener(v -> {
            showTopping(view, productSize);
        });


        // setting default value in variables
        toppingAvailable = false;
        toppingPosition = -1;

        productSizeRate = dPrice;
        productSize = "small";
        productToppingTotal = 0;
        productBottomSheetView = view;
        productToppingList = new ArrayList<>();
        EditComponents.setTextViewText(productBottomSheetView, R.id.total, "Total ₹ " + String.valueOf(productSizeRate));
        EditComponents.setTextViewText(productBottomSheetView, R.id.product_name, productName);


        TextView st, mt, lt;
        LinearLayout sl, ml, ll, toppingParentLayout, sizeParentLayout;


        st = view.findViewById(R.id.sText);
        mt = view.findViewById(R.id.mText);
        lt = view.findViewById(R.id.lText);
        sl = view.findViewById(R.id.sLayout);
        ml = view.findViewById(R.id.mLayout);
        ll = view.findViewById(R.id.lLayout);
        toppingParentLayout = view.findViewById(R.id.toppingParentLayout);
        sizeParentLayout = view.findViewById(R.id.size_layout);

        if (s == 0 && m == 0 && l == 0) {

            toppingParentLayout.setVisibility(View.GONE);
            sizeParentLayout.setVisibility(View.GONE);
            productSize = "No Size";

        } else {
            toppingParentLayout.setVisibility(View.VISIBLE);
            sizeParentLayout.setVisibility(View.VISIBLE);
            if (s >= 1) {
                st.setText("Small\n₹ " + String.valueOf(s));
                sl.setVisibility(View.VISIBLE);
            } else {
                sl.setVisibility(View.GONE);
            }
            if (m >= 1) {
                mt.setText("Medium\n₹ " + String.valueOf(m));
                ml.setVisibility(View.VISIBLE);
            } else {
                ml.setVisibility(View.GONE);
            }
            if (l >= 1) {
                lt.setText("Large\n₹ " + String.valueOf(l));
                ll.setVisibility(View.VISIBLE);
            } else {
                ll.setVisibility(View.GONE);
            }

            st.setOnClickListener(v -> {
                setProductSize(view, "small", s, st, sl);
            });
            mt.setOnClickListener(v -> {
                setProductSize(view, "medium", m, mt, ml);
            });
            lt.setOnClickListener(v -> {
                setProductSize(view, "large", l, lt, ll);
            });

            sl.setOnClickListener(v -> {
                setProductSize(view, "small", s, st, sl);
            });
            ml.setOnClickListener(v -> {
                setProductSize(view, "medium", m, mt, ml);
            });
            ll.setOnClickListener(v -> {
                setProductSize(view, "large", l, lt, ll);
            });

        }


        bsd.setContentView(view);
        bsd.setCancelable(false);
        bsd.show();

        view.findViewById(R.id.addProductInCardBtn).setOnClickListener(v -> {

            if (productToppingList.size() > 0) {
                new TinyDB(myActivity).addInBucket(new UserOrderList(productName, productImage, productSize, 1, productSizeRate, productTax, productId, productToppingList, productToppingTotal));
            } else {
                new TinyDB(myActivity).addInBucket(new UserOrderList(productName, productImage, productSize, 1, productSizeRate, productTax, productId));
            }
            Toast.makeText(myActivity, "Item Added To Your Cart", Toast.LENGTH_SHORT).show();
            changeBucketCountInUi();
            bsd.dismiss();
        });

    }

    // DO NOT REMOVE THIS FUNCTION
    // BECAUSE THE CONTROL PRODUCT EDIT FUNCTIONALITY
    @SuppressLint({"ResourceAsColor", "UseCompatLoadingForDrawables"})
    private static void setProductSize(View v, String size, int sizeRate, TextView textView, LinearLayout linearLayout) {


        TextView ts = v.findViewById(R.id.sText);
        LinearLayout ls = v.findViewById(R.id.sLayout);
        switch (productSize) {
            case "small":
                ts = v.findViewById(R.id.sText);
                ls = v.findViewById(R.id.sLayout);

                break;
            case "medium":
                ts = v.findViewById(R.id.mText);
                ls = v.findViewById(R.id.mLayout);

                break;
            case "large":
                ts = v.findViewById(R.id.lText);
                ls = v.findViewById(R.id.lLayout);

                break;

        }

        ts.setTextColor(myActivity.getResources().getColor(R.color.light_gray));
        ls.setBackground(myActivity.getResources().getDrawable(R.drawable.round_corner_layout));
        textView.setTextColor(myActivity.getResources().getColor(R.color.white));
        linearLayout.setBackground(myActivity.getResources().getDrawable(R.drawable.round_corner_layout_with_main));

        productSize = size;
        productSizeRate = sizeRate;

        if (productToppingList.size() > 0) {
            showTopping(v, size);
        }
        EditComponents.setTextViewText(productBottomSheetView, R.id.total, "Total ₹ " + String.valueOf(productSizeRate));


    }

    public static boolean toggleTopping(String tName, int tRate) {

        // setting default value in variables
        toppingAvailable = false;
        toppingPosition = -1;
        //CHECK TOPPING AVAILABILITY IN TOPPING LIST
        for (int i = 0; i < productToppingList.size(); i++) {
            Toppings testData = productToppingList.get(i);
            if (tName.equals(testData.getToppingName())) {
                toppingAvailable = true;
                toppingPosition = i;
            }

        }


        if (toppingAvailable) {

            productToppingList.remove(toppingPosition);
            productToppingTotal = productToppingTotal - tRate;
            EditComponents.setTextViewText(productBottomSheetView, R.id.total, "Total ₹ " + String.valueOf((productToppingTotal + productSizeRate)));
            return false;

        } else {
            Toppings toppings = new Toppings(tRate, tName);
            productToppingTotal = productToppingTotal + tRate;
            EditComponents.setTextViewText(productBottomSheetView, R.id.total, "Total ₹ " + String.valueOf((productToppingTotal + productSizeRate)));
            productToppingList.add(toppings);
            return true;
        }

    }

    private static void showTopping(View view, String Size) {
        //
        productToppingList = new ArrayList<>();
        productToppingTotal = 0;
        RecyclerView recyclerView = view.findViewById(R.id.toppingRecyclerView);
        LinearLayout waitLayout = view.findViewById(R.id.toppingLoadingPleaseWait);
        recyclerView.setVisibility(View.GONE);
        waitLayout.setVisibility(View.VISIBLE);


        List<Toppings> toppingsList = new ArrayList<>();


        StringRequest request = new StringRequest(Request.Method.POST, myLinks.TOPPING_LINKS, response -> {
            Gson gson = new Gson();
            Toppings[] toppings = gson.fromJson(response, Toppings[].class);

            for (Toppings topping : toppings) {
                toppingsList.add(new Toppings(topping.getToppingPrice(), topping.getToppingName()));

            }

            // productToppingList = toppingsList;

            recyclerView.setVisibility(View.VISIBLE);
            waitLayout.setVisibility(View.GONE);
            recyclerView.setAdapter(new productToppingListAdapter(myActivity.getApplicationContext(), toppingsList));


        }, error -> {
            Toast.makeText(myActivity, "some error coming", Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("size", Size);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(myActivity);
        requestQueue.add(request);

    }

    // OPENING CART
    public void openMyCart(View view) {
        startActivity(new Intent(home.this, user_cart.class));
    }


    public static void setMenu(String filter) {
        myDialog.showLoadingDialog();
        setAndShowProducts(filter);
    }

    private void setSpinner() {
        // Selection of the spinner
        String url = "https://mtechyard.com/newpizzayum-app/locality.php";
        StringRequest localityRequest = new StringRequest(Request.Method.POST, url, response -> {
            if (GlobalFunctions.isJSONValid(response)) {
                Gson gson = new Gson();
                String[] data = gson.fromJson(response, String[].class);
                spinnerData = data;
                // Application of the Array to the Spinner
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                spinner.setAdapter(spinnerArrayAdapter);
            }

        }, error -> {

        });

        RequestQueue re = Volley.newRequestQueue(this);
        re.add(localityRequest);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                if (position > 0) {
                    checkUserLocationAvailable(spinnerData[position]);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
}

