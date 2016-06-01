package br.com.primeiraapp.util;

import android.widget.EditText;
import android.widget.RatingBar;

import br.com.primeiraapp.listaalunos.FormularioAlunoActivity;
import br.com.primeiraapp.listaalunos.R;
import br.com.primeiraapp.modelo.Aluno;

/**
 * Created by jeanaquino on 14/04/2016.
 */
public class FormularioHelper {
    private EditText nome, endereco, site, telefone, email;
    private RatingBar nota;

    public FormularioHelper(FormularioAlunoActivity activity){
        this.nome = (EditText) activity.findViewById(R.id.nome);
        this.endereco = (EditText) activity.findViewById(R.id.endereco);
        this.site = (EditText) activity.findViewById(R.id.site);
        this.telefone = (EditText) activity.findViewById(R.id.telefone);
        this.nota = (RatingBar) activity.findViewById(R.id.nota_aluno);
        this.email = (EditText) activity.findViewById(R.id.email);
    }

    public void preencheFormulario(Aluno aluno){
        this.nome.setText(aluno.getNome());
        this.endereco.setText(aluno.getEndereco());
        this.site.setText(aluno.getSite());
        this.telefone.setText(aluno.getTelefone());
        this.nota.setProgress(aluno.getNota().intValue());
        this.email.setText(aluno.getEmail());
    }

    public Aluno getAluno(Aluno aluno){
        aluno.setId(aluno.getId());
        aluno.setNome(nome.getText().toString());
        aluno.setEndereco(endereco.getText().toString());
        aluno.setSite(site.getText().toString());
        aluno.setTelefone(telefone.getText().toString());
        aluno.setNota(Double.valueOf(nota.getProgress()));
        aluno.setEmail(email.getText().toString());
        return aluno;
    }

}
