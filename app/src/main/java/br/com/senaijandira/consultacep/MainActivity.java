package br.com.senaijandira.consultacep;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    EditText txt_cep;
    TextView txt_cidade;
    TextView txt_logradouro;
    TextView txt_bairro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_cep = findViewById(R.id.txt_cep);
        txt_cidade = findViewById(R.id.txt_cidade);
        txt_logradouro = findViewById(R.id.txt_logradouro);
        txt_bairro = findViewById(R.id.txt_bairro);


    }

    @SuppressLint("StaticFieldLeak")
    public void consultar(View view) {

        String CEP = txt_cep.getText().toString();


        final String URL = "http://viacep.com.br/ws/"+CEP+"/json/ ";



        new AsyncTask<Void, Void, Void>(){

            String retorno;

            @Override
            protected Void doInBackground(Void... voids) {
                retorno = HttpConnection.get(URL);


                Log.d("background", retorno);

                return null;
            }


            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                Toast.makeText(getApplicationContext(), retorno, Toast.LENGTH_LONG).show();

                try {
                    JSONObject retJson = new JSONObject(retorno);

                    String logradouro = retJson.getString("logradouro");
                    String cidade = retJson.getString("localidade");
                    String bairro = retJson.getString("bairro");


                    txt_logradouro.setText("Logradouro: "+logradouro);
                    txt_cidade.setText("Cidade: "+cidade);
                    txt_bairro.setText("Bairro: "+bairro);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }.execute();






    }
}