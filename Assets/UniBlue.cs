using UnityEngine;
using System.Collections;

public class UniBlue {

	public delegate void IjoingDelegate ();

	class UniBlueInstance{
		#if UNITY_IPHONE

		#elif UNITY_ANDROID
		private static AndroidJavaObject unityActivityContext = null;
		private static IjoingDelegate androidInit = null;




		private class AndroidStateCheckCallback :AndroidJavaProxy{
			public AndroidStateCheckCallback():base("com.dos.uniblue.UniBlueCallback"){}
			public void run(){
				UniBlueInstance.GetInstance.state = unityActivityContext.Call<int>("getNativeState");
			}
		}

		private class AndroidResumeCallback :AndroidJavaProxy{
			public AndroidResumeCallback():base("com.dos.uniblue.UniBlueCallback"){}
			public void run(){
				UniBlueInstance.GetInstance.state = unityActivityContext.Call<int>("getNativeState");
				Debug.Log("ResumeCallback");
			}
		}
		#endif


		private static UniBlueInstance instance = null;


		public static UniBlueInstance GetInstance {
			get {
				if (instance == null) {
					instance = new UniBlueInstance ();
					#if UNITY_IPHONE
					#elif UNITY_ANDROID
					AndroidJavaClass jc = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
					unityActivityContext = jc.GetStatic<AndroidJavaObject>("currentActivity");
//					unityActivityContext.Call("setMenuBtnCallback", new AndroidMenuBtnCallback());
					object[] args = {new AndroidResumeCallback()};
					unityActivityContext.Call("initUniblue", args);
					UniBlueInstance.GetInstance.state = unityActivityContext.Call<int>("getNativeState");

					#endif
				}
				return instance;
			}
		}

		public void turnOn()
		{
			#if UNITY_IPHONE
			#elif UNITY_ANDROID
			//androidInit = callback;
			object[] args = {new AndroidStateCheckCallback()};
			unityActivityContext.Call("turnOn", args);
			#endif
		}

		public void turnOff()
		{
			#if UNITY_IPHONE
			#elif UNITY_ANDROID
			//androidInit = callback;
			object[] args = {new AndroidStateCheckCallback()};
			unityActivityContext.Call("turnOff", args);
			#endif
		}

		public int getState()
		{
			#if UNITY_IPHONE
			#elif UNITY_ANDROID
			return UniBlueInstance.GetInstance.state = unityActivityContext.Call<int>("isEnabled");

			#endif
		}

		public int state = 0;
	}

	public static void turnOnBlueThooth(){
		UniBlueInstance.GetInstance.turnOn();
	}

	public static void turnOffBlueThooth(){
		UniBlueInstance.GetInstance.turnOff();
	}
/*
	public static int getState() { 
		return UniBlueInstance.GetInstance.getState(); 
	}
*/
 	public static int getState { get { return UniBlueInstance.GetInstance.state; }}


}
