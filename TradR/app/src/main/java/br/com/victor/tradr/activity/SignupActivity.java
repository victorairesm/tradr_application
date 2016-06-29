package br.com.victor.tradr.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import br.com.victor.tradr.R;
import butterknife.Bind;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";

    @Bind(R.id.input_name)
    EditText _nameText;
    @Bind(R.id.input_email)
    EditText _emailText;
    @Bind(R.id.input_nickname)
    EditText _nicknameText;
    @Bind(R.id.input_password)
    EditText _passwordText;
    @Bind(R.id.input_cpf)
    EditText _cpfText;
    @Bind(R.id.input_telefone)
    EditText _telefoneText;
    @Bind(R.id.input_cep)
    EditText _cepText;
    @Bind(R.id.input_endereco)
    EditText _enderecoText;
    @Bind(R.id.btn_signup)
    Button _signupButton;
    @Bind(R.id.link_login)
    TextView _loginLink;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Criando a conta...");
        progressDialog.show();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "O Login falhou", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String nickname = _nicknameText.getText().toString();
        String password = _passwordText.getText().toString();
        String cpf = _cpfText.getText().toString();
        String telefone = _telefoneText.getText().toString();
        String cep = _cepText.getText().toString();
        String endereco = _enderecoText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("Seu nome deve ter no mínimo 3 letras");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Ops, parece que seu email está errado");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (nickname.isEmpty() || nickname.length() < 4) {
            _nicknameText.setError("Seu nome de usuário deve ter no mínimo 4 letras");
            valid = false;
        } else {
            _nicknameText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 20) {
            _passwordText.setError("Sua senha deve possuir de 4 a 20 caracteres");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (cpf.isEmpty() || cpf.length() != 11) {
            _cpfText.setError("O Cpf deve conter 11 dígitos");
            valid = false;
        } else {
            _cpfText.setError(null);
        }

        if (telefone.isEmpty() || telefone.length() > 11) {
            _telefoneText.setError("Com DDD, seu telefone deve conter 11 ou menos dígitos");
            valid = false;
        } else {
            _telefoneText.setError(null);
        }
        if (cep.isEmpty() || cep.length() != 8) {
            _cepText.setError("Seu CEP deve possuir 8 dígitos");
            valid = false;
        } else {
            _cepText.setError(null);
        }

        if (endereco.isEmpty() || endereco.length() < 10) {
            _enderecoText.setError("Insira corretamente seu endereço");
            valid = false;
        } else {
            _enderecoText.setError(null);
        }

        return valid;
    }
}