package com.example.paddycropdisease;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.text.HtmlCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SolutionsActivity extends AppCompatActivity {

    String blast = "<h5>Chemical control</h5>" +
            "    <ul>" +
            "      <li>Spray after observing initial infection of the disease</li>" +
            "      <li>Carbendazim 50WP @ 500g/ha (or)</li>" +
            "      <li>Tricyclozole 75 WP @ 500g/ha (or)</li>" +
            "      <li>Metominostrobin 20 SC @ 500ml/ha (or) 47</li>" +
            "      <li>Azoxystrobin 25 SC @ 500 ml/ha</li>" +
            "    </ul>" +
            "    <h5>Biological control</h5>" +
            "    <ul>" +
            "      <li>" +
            "        Seed Treatment with TNAU Pf 1liquid formulation @ 10 ml/kg of seeds" +
            "      </li>" +
            "      <li>" +
            "        Seedling root dipping with TNAU Pf 1liquid formulation (500 ml for one" +
            "        hectare seedlings)" +
            "      </li>" +
            "      <li>Soil application with TNAU Pf 1liquid formulation (500ml/ha)</li>" +
            "      <li>Foliar spray with TNAU Pf 1liquid formulation @ 5ml/lit</li>" +
            "    </ul>";

    String brownSpot = "<h5>Chemical control</h5>" +
            "    <ul>" +
            "      <li>Brands that contain Tebuconazole 50.0% WG Trifloxystrobin 25% WG</li>" +
            "      <li>Brands that contain Hexaconazole 5.0% SC</li>" +
            "      <li>Brands that contain Captan 75% WP</li>" +
            "      <li>Brands that contain Carbendazim 25% WS, Mancozeb 50% WS</li>" +
            "    </ul>" +
            "    <h5>Biological control</h5>" +
            "    <ul>" +
            "      <li>" +
            "        To be sure the seeds are not contaminated, a seed bath in hot" +
            "        water(53-54)&degC for 10 to 12 minutes is recomended" +
            "      </li>" +
            "      <li>" +
            "        To improve the results, place the seeds for 8 hours in cold water before" +
            "        the hot water" +
            "      </li>" +
            "    </ul>";

    String hispa = "<h5>Chemical control</h5>" +
            "    <ul>" +
            "      <li>Brands that contain Quinalphos 25.0% EC</li>" +
            "      <li>Brands that contain Lambda-Cyhalothrin 2.5% EC</li>" +
            "      <li>Brands that contain Chlorpyrifos 20.0% EC</li>" +
            "      <li>Brands that contain Lambda-Cyhalothrin 2.5% EC</li>" +
            "    </ul>" +
            "    <h5>Biological control</h5>" +
            "    <ul>" +
            "      <li>The biological control of these pest is still under study.</li>" +
            "      <li>" +
            "        The larval parasitoid, Eulophus femorails has been introduced in India" +
            "        and Bangladesh may reduce hispa problem" +
            "      </li>" +
            "    </ul>";

    String bacterialBlight = "<h5>Chemical control</h5>\n" +
            "  <ul>\n" +
            "    <li>Brands that contain Streptomycin Sulfate 90.0% SP, Tetracyline hydrochloride 10.0% SP</li>\n" +
            "    <li>Brands that contain Copper Hydroxide 53.8% DF</li>\n" +
            "  </ul>\n" +
            "  <h5>Biological control</h5>\n" +
            "  <ul>\n" +
            "    <li>\n" +
            "      Spray fresh cow dung extract 20% twice (starting from initial appearance of the disease and another at fortnightly\n" +
            "      interval)\n" +
            "    </li>\n" +
            "    <li>Neem oil 60 EC 3% (or) NSKE 5% is recommended for the control of sheath rot, sheath blight, grain discolouration\n" +
            "      and bacterial blight</li>\n" +
            "  </ul>";

    String tungro = "<h5>Chemical control</h5>\n" +
            "  <ul>\n" +
            "    <li>Spray Two rounds of any one of the following insecticides</li>\n" +
            "    <li>Thiamethoxam 25 WDG 100g/ha</li>\n" +
            "    <li>Imidacloprid 17.8 SL 100ml/ha</li>\n" +
            "  </ul>\n" +
            "  <h5>Biological control</h5>\n" +
            "  <ul>\n" +
            "    <li>\n" +
            "      Light traps can be used to attract and control the green leafhopper vectors\n" +
            "    </li>\n" +
            "    <li>\n" +
            "      In the early morning, the population of leafhopper alighting near the light trap should be caught and disposed\n" +
            "    </li>\n" +
            "  </ul>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solutions);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String result = getIntent().getStringExtra("result");
        showSolution(result);

    }

    public void showSolution(String res) {

        TextView disease = findViewById(R.id.tvDisease);
        TextView solution = findViewById(R.id.tvSolution);

        switch (res) {
            case "1":
                disease.setText("Bacterial Leaf Blight");
                solution.setText(HtmlCompat.fromHtml(bacterialBlight, 0));
                break;
            case "2":
                disease.setText("Leaf Blast");
                solution.setText(HtmlCompat.fromHtml(blast, 0));
                break;
            case "3":
                disease.setText("Brown Spot");
                solution.setText(HtmlCompat.fromHtml(brownSpot, 0));
                break;
            case "4":
                disease.setText("Tungro");
                solution.setText(HtmlCompat.fromHtml(tungro, 0));
                break;
        }

        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}