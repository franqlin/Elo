package org.shortlets.simplescalculo;

import org.shortlets.simplescalculo.util.ViewUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.provider.CalendarContract;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.ActionBarSherlock.OnCreateOptionsMenuListener;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

public class Principal extends Activity {

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		configFontTypeFace();
		configImageButtons();

		
	}

	private void configImageButtons() {
		
		RelativeLayout clickPorcentagem = (RelativeLayout)findViewById(R.id.idBntDescSimples);
		clickPorcentagem.setOnClickListener(ViewUtil.getOnClickListenerIntent(getApplicationContext(), DescontoSimples.class, this));
	
		// Botao juros
		RelativeLayout clickJuros =(RelativeLayout) findViewById(R.id.idBntJuros);
        clickJuros.setOnClickListener(ViewUtil.getOnClickListenerIntent(getApplicationContext(), Juros.class, this));
        
        //idBntMeuBoleto
        RelativeLayout clickBoleto =(RelativeLayout) findViewById(R.id.idBntMeuBoleto);
		clickBoleto.setOnClickListener(ViewUtil.getOnClickListenerIntent(getApplicationContext(), MeuBoleto.class, this));
        
		//Calendario 
		RelativeLayout clickAgendamento = (RelativeLayout) findViewById(R.id.idBntgCalc);
		clickAgendamento.setOnClickListener(ViewUtil.getOnClickListenerIntent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI, this));
		
		RelativeLayout clickVerCalendrio = (RelativeLayout) findViewById(R.id.idBntCalendar);
		clickVerCalendrio.setOnClickListener(ViewUtil.openCaledar(this));

	}

	private void configFontTypeFace() {
		Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/DenneShuffleEuroHollow.ttf");
        
        TextView tvPorcentagem = (TextView) findViewById(R.id.tvPorcentagem);
        tvPorcentagem.setTypeface(tf);
		TextView tvJuros = (TextView) findViewById(R.id.tvJuros);
        tvJuros.setTypeface(tf);
        TextView tvBoleto = (TextView) findViewById(R.id.tvBoleto);
        tvBoleto.setTypeface(tf);
        TextView tvAgenda = (TextView) findViewById(R.id.tvAgenda);   
        tvAgenda.setTypeface(tf);
        TextView tvCalendario = (TextView) findViewById(R.id.tvCalendario);
        tvCalendario.setTypeface(tf);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}


	

    



}
