package br.com.primeiraapp.modelo.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import br.com.primeiraapp.modelo.Aluno;

/**
 * Created by jeanaquino on 24/05/2016.
 */
public class AlunoDAO extends SQLiteOpenHelper{


    public AlunoDAO(Context context) {
        super(context, "Agenda", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE ALUNO (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOME TEXT NOT NULL, ENDERECO TEXT, TELEFONE TEXT, SITE TEXT, EMAIL TEXT, NOTA REAL);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "DROP TABLE IF EXISTS ALUNO";
        db.execSQL(sql);
        onCreate(db);
    }

    public void insere(Aluno aluno){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();
        dados.put("NOME", aluno.getNome());
        dados.put("ENDERECO", aluno.getEndereco());
        dados.put("TELEFONE", aluno.getTelefone());
        dados.put("SITE", aluno.getSite());
        dados.put("NOTA", aluno.getNota());
        dados.put("EMAIL", aluno.getEmail());
        db.insert("ALUNO", null, dados);
    }

    public List<Aluno> buscaAlunos(){
        String sql = "SELECT * FROM ALUNO;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        List<Aluno> alunos = new ArrayList<Aluno>();
        while(cursor.moveToNext()){
            Aluno aluno = new Aluno();
            aluno.setId(cursor.getLong(cursor.getColumnIndex("ID")));
            aluno.setNome(cursor.getString(cursor.getColumnIndex("NOME")));
            aluno.setEndereco(cursor.getString(cursor.getColumnIndex("ENDERECO")));
            aluno.setTelefone(cursor.getString(cursor.getColumnIndex("TELEFONE")));
            aluno.setSite(cursor.getString(cursor.getColumnIndex("SITE")));
            aluno.setNota(cursor.getDouble(cursor.getColumnIndex("NOTA")));
            aluno.setEmail(cursor.getString(cursor.getColumnIndex("EMAIL")));
            alunos.add(aluno);
        }
        cursor.close();
        return alunos;
    }

    public void deletar(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        String[] params = {aluno.getId().toString()};
        db.delete("ALUNO", "ID = ?", params);
    }

    @NonNull
    public void alterar(Aluno aluno) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues dados = new ContentValues();
        dados.put("NOME", aluno.getNome());
        dados.put("ENDERECO", aluno.getEndereco());
        dados.put("TELEFONE", aluno.getTelefone());
        dados.put("SITE", aluno.getSite());
        dados.put("NOTA", aluno.getNota());
        dados.put("EMAIL", aluno.getEmail());

        String [] parametros = {aluno.getId().toString()};
        db.update("ALUNO",dados, "ID = ?", parametros);
    }
}
