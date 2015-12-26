using UnityEngine;
using System.Collections;

public class Demo : MonoBehaviour {
	public string state = "sssssss";
	// Use this for initialization
	//
	public float updateInterval = 0.5F;
	private double lastInterval;
	private int frames = 0;
	private float fps;
	void Start () {
		//
		lastInterval = Time.realtimeSinceStartup;
		frames = 0;
		//
	}
	
	// Update is called once per frame
	void Update () {
		//
		++frames;
		float timeNow = Time.realtimeSinceStartup;
		if (timeNow > lastInterval + updateInterval)
		{
			fps = (float)(frames / (timeNow - lastInterval));
			frames = 0;
			lastInterval = timeNow;
		}
		//
	}

	void OnGUI(){
		GUILayout.Label(" " + fps.ToString("f2"));
		switch(UniBlue.getState){
			case 10:
				state = "STATE_OFF";
				break;
			case 11:
				state = "STATE_TURNING_ON";
				break;
			case 12:
				state = "STATE_ON";
				break;
			case 13:
				state = "STATE_TURNING_OFF";
				break;




		}
		if (GUI.Button(new Rect(10, 10, 300, 100), "turn on")){
			UniBlue.turnOnBlueThooth();

		}


		if (GUI.Button(new Rect(10, 310, 300, 100), "turn off")){
			UniBlue.turnOffBlueThooth();

		}

		GUI.Label(new Rect(10, 500, 100, 100), state);

		//GUI.Label(new Rect(10, 650, 100, 50), (1.0/Time.deltaTime).ToString());



	}
}
