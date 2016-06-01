package br.com.primeiraapp.listaalunos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.primeiraapp.modelo.Aluno;
import br.com.primeiraapp.modelo.dao.AlunoDAO;
import br.com.primeiraapp.util.FormularioHelper;

public class FormularioAlunoActivity extends AppCompatActivity {

    private FormularioHelper helper;
    private Aluno aluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_aluno);
        helper = new FormularioHelper(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        preencherForm();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.formulario_aluno, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvar_aluno:
                if(aluno==null){
                    aluno = new Aluno();
                }
                aluno = helper.getAluno(aluno);
                AlunoDAO alunoDao = new AlunoDAO(this);
                if(aluno.getId()!=null){
                    alunoDao.alterar(aluno);
                }else{
                    alunoDao.insere(aluno);
                }
                alunoDao.close();
                Toast.makeText(FormularioAlunoActivity.this, "Aluno " + aluno.getNome() + " Salvo com sucesso!", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void preencherForm(){
        Intent intent = getIntent();
        aluno = (Aluno) intent.getSerializableExtra("aluno");
        if(aluno!=null){
            helper.preencheFormulario(aluno);
        }
    }

}
