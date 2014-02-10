package org.shortlets.elo.addressbook;

import org.shortlets.elo.addressbook.dao.ContatoDao;


import com.example.eloaddressbook.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ListaContatosActivity extends ListActivity 
{
   public static final String ROW_ID = "row_id"; 
   private ListView contactListView;
   private CursorAdapter contactAdapter;

   @Override
   public void onCreate(Bundle savedInstanceState) 
   {
      super.onCreate(savedInstanceState); 
      contactListView = getListView(); 
      contactListView.setOnItemClickListener(viewContactListener);      

      String[] from = new String[] { "nome" };
      int[] to = new int[] { R.id.contactTextView };
      contactAdapter = new SimpleCursorAdapter(ListaContatosActivity.this, R.layout.contact_list_item, null, from, to);
      setListAdapter(contactAdapter); 
   } 

   @Override
   protected void onResume() 
   {
      super.onResume(); 
       new GetContactsTask().execute((Object[]) null);
    }

   @Override
   protected void onStop() 
   {
      Cursor cursor = contactAdapter.getCursor(); 
      
      if (cursor != null) 
         cursor.deactivate(); 
      
      contactAdapter.changeCursor(null);
      super.onStop();
   } 

   private class GetContactsTask extends AsyncTask<Object, Object, Cursor> 
   {
      ContatoDao databaseConnector =  new ContatoDao(ListaContatosActivity.this);
      @Override
      protected Cursor doInBackground(Object... params)
      {
         databaseConnector.open();
         return databaseConnector.findAll(); 
      } 
      @Override
      protected void onPostExecute(Cursor result)
      {
         contactAdapter.changeCursor(result);
         databaseConnector.close();
      }
   } 
      
 
   @Override
   public boolean onCreateOptionsMenu(Menu menu) 
   {
      super.onCreateOptionsMenu(menu);
      MenuInflater inflater = getMenuInflater();
      inflater.inflate(R.menu.add_contact, menu);
      return true;
   }
   
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) 
   {
    
      Intent addNewContact = 
         new Intent(ListaContatosActivity.this, InserirAtualizarContatos.class);
      startActivity(addNewContact);
      return super.onOptionsItemSelected(item); 
   }

   OnItemClickListener viewContactListener = new OnItemClickListener() 
   {
      @Override
      public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
         long arg3) 
      {
         
         Intent viewContact = 
            new Intent(ListaContatosActivity.this, VisualizarContato.class);
         
        
         viewContact.putExtra(ROW_ID, arg3);
         startActivity(viewContact); 
      } 
   }; 
} 