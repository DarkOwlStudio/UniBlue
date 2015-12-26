package com.dos.uniblue;


import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;

import android.os.HandlerThread;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Toast;

import com.unity3d.player.UnityPlayerActivity;

public class MainActivity extends UnityPlayerActivity {
	
	private static final int REQUEST_ENABLE_BT = 1;
	private HandlerThread hThread;
	private Handler hThreadHandler;
	private BluetoothAdapter mBluetoothAdapter;
	private UniBlueCallback mCallback;
	private UniBlueCallback resumeCallback;
	private int stateValue;
	private BroadcastReceiver mReceiver;
	private Menu mArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	
        /*
        hThread = new HandlerThread("_hTreard");
        hThread.start();
        hThreadHandler = new Handler(hThread.getLooper());
    	*/
    }
    
    @Override
	protected void onRestart()
	{
		super.onRestart();
		resumeCallback.run();
	}
    
    public void initUniblue(UniBlueCallback callback){
    	resumeCallback = callback;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBluetoothAdapter == null) {
        	  //Toast.makeText(getApplicationContext(),"Your device does not support Bluetooth",Toast.LENGTH_LONG).show();
          } else {
          	 // Toast.makeText(getApplicationContext(),"Your device support Bluetooth",Toast.LENGTH_LONG).show();
          }
    }
    
    public int getNativeState() {
    	stateValue = mBluetoothAdapter.getState();
		return stateValue;
	}
    
    public void turnOn(UniBlueCallback callback){
    	mCallback = callback;
		if (!mBluetoothAdapter.isEnabled()) {
	        Intent turnOnIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
	        startActivityForResult(turnOnIntent, REQUEST_ENABLE_BT);
	        
	    }else{
	    	
	    }
		
		mCallback.run();

    }
    
    public void findingDevices(UniBlueCallback callback){
    	mCallback = callback;
    	mBluetoothAdapter.startDiscovery(); 
        mReceiver = new BroadcastReceiver() {
        
		public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) 
            {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // Add the name and address to an array adapter to show in a ListView
               mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
            }
          }
        };

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND); 
        registerReceiver(mReceiver, filter);
        Toast.makeText(getApplicationContext(),mArrayAdapter.toString(),Toast.LENGTH_LONG).show();
        mCallback.run();
    }
    
    public void turnOff(UniBlueCallback callback){
    	mCallback = callback;
  	    mBluetoothAdapter.disable();
  	    //hThreadHandler.post(showToast3);
  	    
  	    while(mBluetoothAdapter.getState() == 12){
  	    }
  	    mCallback.run();
  	    while(mBluetoothAdapter.getState() == 13){
  	    }
  	    mCallback.run();
  	    
  	    
     }
    

    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 	   // TODO Auto-generated method stub
 	   if(requestCode == REQUEST_ENABLE_BT){
 		   if(mBluetoothAdapter.isEnabled()) {
 			  //hThreadHandler.post(showToast1);
 		   } else {   
 			  //hThreadHandler.post(showToast2);
 		   }
 		   
 		  mCallback.run();
 	   }
    }
    
    
    /*
    private Runnable showToast1 = new Runnable(){
    	@Override
    	public void run(){		
    		Toast.makeText(getApplicationContext(),"藍牙已開啟" ,
	        		 Toast.LENGTH_LONG).show();
    	}
    };
    
    private Runnable showToast2 = new Runnable(){
    	@Override
    	public void run(){		
	         Toast.makeText(getApplicationContext(),"藍牙已關閉",	 
	        		 Toast.LENGTH_LONG).show();
    	}
    };
    
    private Runnable showToast3 = new Runnable(){
    	@Override
    	public void run(){		
            Toast.makeText(getApplicationContext(),"Bluetooth turned off",
            		  Toast.LENGTH_LONG).show();
    	}
    };
    */
}

