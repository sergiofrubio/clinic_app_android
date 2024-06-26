package com.sfr.clinic_app.tienda.interactor;

import android.util.Log;

import com.sfr.clinic_app.api.Models.Product;
import com.sfr.clinic_app.api.wsApi.WsApi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.inject.Inject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TiendaInteractorImpl implements TiendaInteractor{

    @Inject
    WsApi wsApi;

    @Inject
    public TiendaInteractorImpl() {}

    @Override
    public void getArticlesFromApi(OnGetProductsCallBacks callBack, OnErrorServer errorServer) {
        wsApi.getAllProducts().enqueue(new Callback<List<Product>>() {

            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    callBack.onSuccessCallBacks(new ArrayList<Product>(response.body()));
                }else{
                    callBack.onErrorCallBacks(response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                errorServer.errorServerMessage(Arrays.toString(t.getStackTrace()));
            }
        });
    }

    @Override
    public void purchase(String product_id, String paciente_id, String fecha_emision, String estado, OnGetPurchaseCallBacks callBack, OnErrorServer onErrorServer) {
        wsApi.confirmPurchase(product_id, paciente_id, fecha_emision, estado).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    callBack.onSuccessCallBacks(response.body());
                }else{
                    callBack.onErrorCallBacks(response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                onErrorServer.errorServerMessage(t.getMessage());
            }
        });
    }
}
