package org.shortlets.elo.service.location;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import org.shortlets.elo.addressbook.InserirAtualizarContatos;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

public class GPSUtil extends Service implements LocationListener {

	private final Context contexto;
	private boolean isGPSAtivo = false;
	private boolean isRedeAtiva = false;
	private boolean temAlcance = false;
	private Location location; 
	private double latitude; 
	private double longitude;
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; 

	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

	protected LocationManager locationManager;

	public GPSUtil(Context context) {
		this.contexto = context;
		getLocation();
	}

	public Location getLocation() {
		try {
			locationManager = (LocationManager) contexto.getSystemService(LOCATION_SERVICE);
			isGPSAtivo = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			isRedeAtiva = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			

			 if (isGPSAtivo && isRedeAtiva) {
				this.temAlcance = true;
				if (isRedeAtiva) {
					locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					Log.d("Rede", "REDE ATIVADA");
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
				if (isGPSAtivo) {
					    this.temAlcance = true;
						locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
						location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						latitude = location.getLatitude();
						longitude = location.getLongitude();
						
						Log.d("GPS", "GPS Habilitado");
						if (locationManager != null) {
							location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}
	
	public void pararGPS(){
		if(locationManager != null){
			locationManager.removeUpdates(GPSUtil.this);
		}		
	}
	
	public double getLatitude(){
		return location!=null ?location.getLatitude():0.0;
	}
	
	public double getLongitude(){
		return location != null? location.getLongitude() : 0.0;
	}
	
	public boolean temLocalizacao() {
		return this.temAlcance;
	}
	
	public void exibirMenssagemexibirMenssagemexibirMenssagem(){
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(contexto);
   	    alertDialog.setTitle("Configuração do gps");
        alertDialog.setMessage("GSP não habilitado. Gostaria de configurá-lo?");
        alertDialog.setPositiveButton("Configuração", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            	contexto.startActivity(intent);
            }
        });
 
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
            }
        });
        alertDialog.show();
	}
	
   public String  getEndereco(){
	   Geocoder geocoder = new Geocoder(contexto, Locale.getDefault());
	   List <Address> listaEndereco = null;
	   try {
           listaEndereco = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
           } catch (IOException e) {
               e.printStackTrace();
       }
           if (listaEndereco != null && listaEndereco.size() > 0) {
               Address endereco = listaEndereco.get(0);
               return  endereco.getMaxAddressLineIndex() > 0 ? endereco.getAddressLine(0) : ""+endereco.getLocality()+endereco.getCountryName();
           } else {
             return "nao encontrado";
           }
	   
   }
   
   public static String  getEndereco(Geocoder geocoder,Location l){
	   List <Address> listaEndereco = null;
	   try {
           listaEndereco = geocoder.getFromLocation(l.getLatitude(),l.getLongitude(), 1);
           } catch (IOException e) {
               e.printStackTrace();
       }
           if (listaEndereco != null && listaEndereco.size() > 0) {
               Address endereco = listaEndereco.get(0);
               return  endereco.getMaxAddressLineIndex() > 0 ? endereco.getAddressLine(0) : ""+endereco.getLocality()+endereco.getCountryName();
           } else {
             return "nao encontrado";
           }
	   
   }
   

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

}
