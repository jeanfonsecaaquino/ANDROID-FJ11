package br.com.primeiraapp.listaalunos;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.primeiraapp.modelo.Aluno;
import br.com.primeiraapp.modelo.dao.AlunoDAO;

public class ListaAlunosActivity extends AppCompatActivity {

    private ListView listaAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_alunos);
        listaAlunos = (ListView) findViewById(R.id.lista_alunos);
        registerForContextMenu(listaAlunos);
        Button novoAluno = (Button) findViewById(R.id.novo_aluno);
        novoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregarLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listaAlunos.getItemAtPosition(info.position);

        MenuItem deletar = menu.add(R.string.menuListaAlunoDeletar);
        MenuItem editar = menu.add(R.string.menuListaAlunoEditar);
        MenuItem site = menu.add(R.string.menuListaAlunoSite);
        MenuItem ligar = menu.add(R.string.menuListaAlunoLigar);
        MenuItem sms = menu.add(R.string.menuListaAlunoSms);
        MenuItem mapa = menu.add(R.string.menuListaAlunoMapa);

        Intent irParaSite = new Intent(Intent.ACTION_VIEW);
        if(!aluno.getSite().startsWith("http://")){
            aluno.setSite("http://" + aluno.getSite());
        }

        irParaSite.setData(Uri.parse(aluno.getSite()));
        site.setIntent(irParaSite);

        editar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent editarAluno = new Intent(ListaAlunosActivity.this, FormularioAlunoActivity.class);
                editarAluno.putExtra("aluno", aluno);
                startActivity(editarAluno);
                return false;
            }
        });
        deletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AlunoDAO alunoDAO = new AlunoDAO(ListaAlunosActivity.this);
                alunoDAO.deletar(aluno);
                Toast.makeText(ListaAlunosActivity.this, "Aluno " + aluno.getNome() + " removido", Toast.LENGTH_SHORT).show();
                carregarLista();
                return false;
            }
        });
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    private void carregarLista(){
        AlunoDAO dao = new AlunoDAO(this);
        List<Aluno> alunos = dao.buscaAlunos();
        dao.close();
        ListView listaAlunos = (ListView)  findViewById(R.id.lista_alunos);
        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this,  android.R.layout.simple_list_item_1, alunos);
        listaAlunos.setAdapter(adapter);
    }
}
