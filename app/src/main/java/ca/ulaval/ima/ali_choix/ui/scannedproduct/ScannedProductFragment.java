package ca.ulaval.ima.ali_choix.ui.scannedproduct;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import ca.ulaval.ima.ali_choix.domain.NutriScoreGrade;
import cz.msebera.android.httpclient.Header;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import ca.ulaval.ima.ali_choix.R;
import ca.ulaval.ima.ali_choix.domain.Product;
import ca.ulaval.ima.ali_choix.network.OpenFoodFactRestClient;

import static ca.ulaval.ima.ali_choix.domain.NutriScoreGrade.*;

public class ScannedProductFragment extends Fragment {
    private Product product;
    private ImageView scannedProductImage;
    private TextView scannedProductOrigin;
    private TextView scannedProductCountryImported;
    private TextView scannedProductQuantity;
    private TextView scannedProductName;
    private ImageView nutriScoreDownArrow;
    private ImageView nutriScoreUpArrow;
    private RelativeLayout nutriScoreCollapsible;
    private RelativeLayout nutriScoreLayout;
    private ImageView nutriScoreDrawable;
    private RelativeLayout ingredientsAnalysisCollapsible;
    private ImageView ingredientAnalysisDownArrow;
    private ImageView ingredientAnalysisUpArrow;
    private TextView isVegeterian;
    private TextView isVegan;
    private RelativeLayout ingredientsAnalysisLayout;
    private TextView isPalmOilFree;
    private RelativeLayout nutrientLevelsCollapsible;
    private RelativeLayout nutrientLevelsLayout;
    private ImageView nutrientLevelsDownArrow;
    private ImageView nutrientLevelsUpArrow;
//    private ProductService productService;
    private TextView nutriScoreDescription;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_scanned_product, container, false);
        scannedProductImage = root.findViewById(R.id.scanned_product_image);
        scannedProductOrigin = root.findViewById(R.id.scanned_product_origin);
        scannedProductCountryImported = root.findViewById(R.id.scanned_product_imported_country);
        scannedProductQuantity = root.findViewById(R.id.scanned_product_quantity);
        scannedProductName = root.findViewById(R.id.scanned_product_name);

        nutriScoreCollapsible = root.findViewById(R.id.collapsible_nutri_score_section);
        nutriScoreDrawable = root.findViewById(R.id.nutri_score_drawable);
        nutriScoreDescription = root.findViewById(R.id.nutri_score_description);
        nutriScoreLayout = root.findViewById(R.id.nutri_score_layout);
        nutriScoreDownArrow = root.findViewById(R.id.nutri_score_down_arrow);
        nutriScoreUpArrow = root.findViewById(R.id.nutri_score_up_arrow);

        nutriScoreUpArrow.setVisibility(View.GONE);
        nutriScoreLayout.setVisibility(View.GONE);

        nutriScoreCollapsible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCollapsibleSection(v, nutriScoreUpArrow, nutriScoreDownArrow, nutriScoreLayout);
            }
        });

        ingredientsAnalysisCollapsible = root.findViewById(R.id.collapsible_diet_section);
        ingredientsAnalysisLayout = root.findViewById(R.id.diet_type_layout);
        ingredientAnalysisDownArrow = root.findViewById(R.id.diet_down_arrow);
        ingredientAnalysisUpArrow = root.findViewById(R.id.diet_up_arrow);
        isVegeterian = root.findViewById(R.id.is_vegeterian);
        isVegan = root.findViewById(R.id.is_vegan);
        isPalmOilFree = root.findViewById(R.id.is_palm_oil_free);

        ingredientAnalysisUpArrow.setVisibility(View.GONE);
        ingredientsAnalysisLayout.setVisibility(View.GONE);

        ingredientsAnalysisCollapsible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCollapsibleSection(v, ingredientAnalysisUpArrow, ingredientAnalysisDownArrow, ingredientsAnalysisLayout);
            }
        });

        nutrientLevelsCollapsible = root.findViewById(R.id.collapsible_nutrient_levels_section);
        nutrientLevelsLayout = root.findViewById(R.id.nutrient_levels_layout);
        nutrientLevelsDownArrow = root.findViewById(R.id.nutrient_levels_down_arrow);
        nutrientLevelsUpArrow = root.findViewById(R.id.nutrient_levels_up_arrow);

        nutrientLevelsUpArrow.setVisibility(View.GONE);
        nutrientLevelsLayout.setVisibility(View.GONE);

        nutrientLevelsCollapsible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleCollapsibleSection(v, nutrientLevelsUpArrow, nutrientLevelsDownArrow, nutrientLevelsLayout);
            }
        });

        getInformationsWithOpenFoodFact();

        return root;
    }


    private void getInformationsWithOpenFoodFact() {
        OpenFoodFactRestClient OFFClient = new OpenFoodFactRestClient();
        OFFClient.get("0677210090246", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject dataObject) {
                try {
                    JSONObject productJson = (JSONObject) dataObject.get("product");
                    Gson gson = new Gson();
                    product = gson.fromJson(String.valueOf(productJson), Product.class);
                    showInformations();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void showInformations() {
        String englishName = product.getEnglishName();
        String frenchName = product.getFrenchName();
        String url_front = product.getImage();
        Picasso.get().load(url_front).into(scannedProductImage);
        scannedProductOrigin.setText(product.getOrigin());
        scannedProductCountryImported.setText(product.getCountryImported());
        scannedProductQuantity.setText(product.getQuantity()+'g');
        if (englishName != null && !englishName.trim().equals("")) {
            scannedProductName.setText(englishName);
        }
        if (frenchName != null && !frenchName.trim().equals("")){
            scannedProductName.setText(frenchName);
        }

        NutriScoreGrade nutriScoreGrade = get(product.getNutriScoreGrade());
        nutriScoreDrawable.setBackground(getNutriScoreGradeDrawable(nutriScoreGrade));

        String nutriScoreDescriptionFound = nutriScoreGrade.getDescription();
        nutriScoreDescription.setText(nutriScoreDescriptionFound);
    }

    private void toggleCollapsibleSection(View v, ImageView upArrow, ImageView downArrow, RelativeLayout relativeLayout) {
        upArrow.setVisibility(upArrow.isShown()
                ? View.GONE
                : View.VISIBLE);
        relativeLayout.setVisibility(relativeLayout.isShown()
                ? View.GONE
                : View.VISIBLE);
        downArrow.setVisibility(downArrow.isShown() ? View.GONE : View.VISIBLE);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private Drawable getNutriScoreGradeDrawable(NutriScoreGrade nutriScoreGrade) {
        switch (nutriScoreGrade) {
            case A:
                return getResources().getDrawable(R.drawable.ic_nutriscore_a);
            case B:
                return getResources().getDrawable(R.drawable.ic_nutriscore_b);
            case C:
                return getResources().getDrawable(R.drawable.ic_nutriscore_c);
            case D:
                return getResources().getDrawable(R.drawable.ic_nutriscore_d);
            case E:
                return getResources().getDrawable(R.drawable.ic_nutriscore_e);
            default:
                break;
        }

        return null;
    }
}