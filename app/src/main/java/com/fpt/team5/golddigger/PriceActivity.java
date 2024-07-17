package com.fpt.team5.golddigger;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.fpt.team5.golddigger.Model.GoldPrice;
import com.fpt.team5.golddigger.api.goldPriceApi.ApiResponse.GoldPriceResponse;
import com.fpt.team5.golddigger.api.goldPriceApi.ApiResponse.Last;
import com.fpt.team5.golddigger.api.goldPriceApi.ApiResponse.Price;
import com.fpt.team5.golddigger.api.goldPriceApi.DanTriApiServices;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PriceActivity extends AppCompatActivity {
    private TableLayout tableLayoutGoldPrice;
    private NaviagtionBarFragment navigationBarFragment;
    private List<GoldPrice> goldPrices;
    private TextView textViewType;
    private TextView textViewBuyPrice;
    private TextView textViewSellPrice;
    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);
        if (fragment instanceof NaviagtionBarFragment) {
            navigationBarFragment = ((NaviagtionBarFragment) fragment);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_price);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        textViewType = findViewById(R.id.type);
        textViewBuyPrice = findViewById(R.id.buy_price);
        textViewSellPrice = findViewById(R.id.sell_price);

        bindingView();
        getGoldPriceByApi();
        injectFragment();
    }

    private void injectFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.navBarFragment, navigationBarFragment)
                .commit();
    }

    private void bindingView() {
        tableLayoutGoldPrice = findViewById(R.id.tableLayoutGoldPrice);
        if (navigationBarFragment == null) {
            navigationBarFragment = new NaviagtionBarFragment();
        }
    }

    private void getGoldPriceByApi() {
        // call api here
        try {
            DanTriApiServices.getCommentApiEndpoint()
                    .getAlls()
                    .enqueue(new Callback<List<GoldPriceResponse>>() {
                        @Override
                        public void onResponse(Call<List<GoldPriceResponse>> call, Response<List<GoldPriceResponse>> response) {
                            List<GoldPriceResponse> goldPriceResponse = response.body();
                            // map price here
                            goldPrices = mapPrice(goldPriceResponse);
                            populateTable(goldPrices);
                        }

                        @Override
                        public void onFailure(Call<List<GoldPriceResponse>> call, Throwable t) {
                            Toast.makeText(PriceActivity.this, "Can't view gold price right now. Server error!", Toast.LENGTH_SHORT).show();
                        }
                    });

        } catch (Exception e) {
            Toast.makeText(this, "Can't view gold price right now! Unknown error!", Toast.LENGTH_SHORT).show();
        }


    }

    private List<GoldPrice> mapPrice(List<GoldPriceResponse> goldPriceResponses) {
        List<GoldPrice> goldPrices = new ArrayList<>();

        for (GoldPriceResponse response : goldPriceResponses) {
            String logo = response.getLogo();
            Last last = response.getLast();

            if (last != null) {
                List<Price> prices = last.getPrices();
                for (Price price : prices) {
                    GoldPrice goldPrice = getGoldPrice(price, logo);
                    goldPrices.add(goldPrice);
                }
            }
        }

        return goldPrices;
    }

    private static String formatWithThousandSeparator(double value) {
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMaximumFractionDigits(0);
        return numberFormat.format(value / 1000); // đổi sang tỷ lệ nghìn đồng
    }

    private static @NonNull GoldPrice getGoldPrice(Price price, String logo) {
        GoldPrice goldPrice = new GoldPrice();
        goldPrice.setImageLink(mapImageLink(logo));
        goldPrice.setType(price.getType());

        goldPrice.setBuyPrice(formatWithThousandSeparator(price.getBuyPrice()));
        goldPrice.setSellPrice(formatWithThousandSeparator(price.getSellPrice()));
        goldPrice.setBuyPriceChange(formatWithThousandSeparator(price.getBuyPriceChange()));
        goldPrice.setSellPriceChange(formatWithThousandSeparator(price.getSellPriceChange()));

        return goldPrice;
    }

    // Hàm map link logo sang tên file
    private static String mapImageLink(String logo) {
        try {
            final String DAN_TRI_CDN = "https://cdnweb.dantri.com.vn/dist/";
            // Khai báo bản đồ map
            final Map<String, String> fileMapping = new HashMap<>();

            // Khởi tạo bản đồ map
            fileMapping.put("btmc.png", DAN_TRI_CDN + "e2c061426883a3703746.png");
            fileMapping.put("btmh.png", DAN_TRI_CDN + "b701a29f38eddef2770f.png");
            fileMapping.put("doji.png", DAN_TRI_CDN + "ce3e12527138b7fc8033.png");
            fileMapping.put("pnj.png", DAN_TRI_CDN + "3a9a419fd085a7de616b.png");
            fileMapping.put("sjc.png", DAN_TRI_CDN + "8acbf8108fc82f37ca20.png");


            // Tách lấy phần tên file từ đường dẫn
            String[] pathParts = logo.split("/");
            String fileNameWithQuery = pathParts[pathParts.length - 1]; // "sjc.png?v=0101070000"
            String[] fileNameParts = fileNameWithQuery.split("\\?"); // ["sjc.png", "v=0101070000"]
            String fileName = fileNameParts[0]; // "sjc.png"

            String mappedFileName = fileMapping.get(fileName);
            if (mappedFileName != null) {
                return mappedFileName;
            } else {
                return "";
            }
        } catch (Exception e) {
            Log.e("PriceActivity", "mapImageLink: ", e);
        }
        return "";
    }

    private void populateTable(List<GoldPrice> goldPriceList) {
        tableLayoutGoldPrice.removeViews(1, tableLayoutGoldPrice.getChildCount() - 1);
        for (GoldPrice goldPrice : goldPriceList) {
            TableRow tableRow = new TableRow(this);

            // Load ImageLink (URL) into ImageView using Picasso
            ImageView imageView = new ImageView(this);
            Picasso.get().load(goldPrice.getImageLink()).into(imageView);
            imageView.setPadding(8, 8, 8, 8); // Add padding for image
            tableRow.addView(imageView);

            TextView textViewType = new TextView(this);
            textViewType.setText(goldPrice.getType());
            textViewType.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            textViewType.setPadding(8, 8, 8, 8); // Add padding for text view
            textViewType.setTextColor(Color.BLACK); // Set text color
            tableRow.addView(textViewType);



            TextView textViewBuyPrice = new TextView(this);
            setPriceAndChange(goldPrice, textViewBuyPrice, goldPrice.getBuyPrice(), goldPrice.getBuyPriceChange() );
            textViewBuyPrice.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            textViewBuyPrice.setPadding(8, 8, 8, 8); // Add padding for text view
            tableRow.addView(textViewBuyPrice);

            TextView textViewSellPrice = new TextView(this);
            setPriceAndChange(goldPrice, textViewSellPrice, goldPrice.getSellPrice(), goldPrice.getSellPriceChange());
            textViewSellPrice.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
            textViewSellPrice.setPadding(8, 8, 8, 8); // Add padding for text view
            tableRow.addView(textViewSellPrice);

            // Set giao diện xen kẽ
            if (tableLayoutGoldPrice.getChildCount() % 2 == 0) {
                tableRow.setBackgroundColor(Color.parseColor("#f0f0f0"));
            }
            tableLayoutGoldPrice.addView(tableRow);
        }
    }

    // Tăng: xanh lá, Giảm: đỏ, Ngang: vàng
    private static void setPriceAndChange(GoldPrice goldPrice, TextView textViewPrice,String price, String priceChange) {
        double buyPriceChange = Double.parseDouble(priceChange.replace(",", ""));

        if (buyPriceChange > 0) {
            textViewPrice.setText(String.format("%s\n(▲%s)", price, (int) buyPriceChange));
            textViewPrice.setTextColor(Color.rgb(0,153,25)); // Dark green
        } else if (buyPriceChange < 0) {
            textViewPrice.setText(String.format("%s\n(▼%s)", price, (int) Math.abs(buyPriceChange)));
            textViewPrice.setTextColor(Color.rgb(230,0,0)); // Dark red
        } else {
            textViewPrice.setText(String.format("%s", price));
            textViewPrice.setTextColor(Color.rgb(204,102,0)); // Dark yellow
        }
    }


    // Phương thức xử lý sự kiện onClick cho nút
    public void onFindMapButtonClick(@NonNull View view) {
        try {
            // Tạo truy vấn tìm kiếm
            String query = "Tiệm vàng";

            // Tạo implicit intent để mở ứng dụng bản đồ với truy vấn tìm kiếm
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(query));
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);

            // Kiểm tra xem có ứng dụng nào có thể xử lý intent này không
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                // Tạo chooser để người dùng chọn ứng dụng
                Intent chooser = Intent.createChooser(mapIntent, "Chọn ứng dụng để mở bản đồ");
                startActivity(chooser);
            } else {
                // Nếu không tìm thấy ứng dụng bản đồ, mở trình duyệt web
                Uri webIntentUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=" + Uri.encode(query));
                Intent webIntent = new Intent(Intent.ACTION_VIEW, webIntentUri);
                // Tạo chooser để người dùng chọn ứng dụng
                Intent chooser = Intent.createChooser(webIntent, "Select the application to open the map");
                if (webIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(chooser);
                } else {
                    // Hiển thị thông báo nếu không có ứng dụng nào có thể xử lý intent
                    Toast.makeText(this, "No map application and browser found", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    public void onTypeClick(View view) {
        try {
            sortByType();
        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void sortByType() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if(!textViewType.getText().equals(getString(R.string.typeDown))) {
                goldPrices.sort(Comparator.comparing(GoldPrice::getType));
                resetTextViews();
                textViewType.setText(R.string.typeDown);
                Toast.makeText(this, "Sort by type A-Z", Toast.LENGTH_SHORT).show();
            } else {
                goldPrices.sort((goldPrice1, goldPrice2) -> goldPrice2.getType().compareTo(goldPrice1.getType()));
                resetTextViews();
                textViewType.setText(R.string.typeUp);
                Toast.makeText(this, "Sort by type Z-A", Toast.LENGTH_SHORT).show();
            }
            populateTable(goldPrices);
        }
    }

    public void onSellPriceClick(View view) {
        try {
            sortBySellPrice();
        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void sortBySellPrice() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if(!textViewSellPrice.getText().equals(getString(R.string.sellPriceDown))) {
                goldPrices.sort((goldPrice1, goldPrice2) -> goldPrice2.getSellPrice().compareTo(goldPrice1.getSellPrice()));
                resetTextViews();
                textViewSellPrice.setText(R.string.sellPriceDown);
                Toast.makeText(this, "Sort by decreasing sell price", Toast.LENGTH_SHORT).show();
            }
            else {
                goldPrices.sort(Comparator.comparing(GoldPrice::getSellPrice));
                resetTextViews();
                textViewSellPrice.setText(R.string.sellPriceUp);
                Toast.makeText(this, "Sort by increasing sell price", Toast.LENGTH_SHORT).show();
            }
            populateTable(goldPrices);
        }
    }

    public void onBuyPriceClick(View view) {
        try {
            sortByBuyPrice();
        } catch (Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void sortByBuyPrice() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if(!textViewBuyPrice.getText().equals(getString(R.string.buyPriceDown))) {
                goldPrices.sort((goldPrice1, goldPrice2) -> goldPrice2.getBuyPrice().compareTo(goldPrice1.getBuyPrice()));
                resetTextViews();
                textViewBuyPrice.setText(R.string.buyPriceDown);
                Toast.makeText(this, "Sort by decreasing buy price", Toast.LENGTH_SHORT).show();
            }
            else {
                goldPrices.sort(Comparator.comparing(GoldPrice::getBuyPrice));
                resetTextViews();
                textViewBuyPrice.setText(R.string.buyPriceUp);
                Toast.makeText(this, "Sort by increasing buy price", Toast.LENGTH_SHORT).show();
            }
            populateTable(goldPrices);
        }
    }

    private void resetTextViews() {
        textViewType.setText(getString(R.string.type));
        textViewBuyPrice.setText(getString(R.string.buy_price));
        textViewSellPrice.setText(getString(R.string.sell_price));
    }
}