package com.greenland.stepcounter;

import android.os.Bundle;

public interface FragComunicator {
	
//	enum eSHARE_KEY{
//		FRAG_TAG,
//		FRAG_MSGID,
//		FRAG_PHONENUMBER,
//
//		FRAG_ETC
//	}
//
//	interface ShareDataKeyString{
//		String FRAG_TAG 			= "TagName";
//		String FRAG_MSGID 			= "MsgId";
//		String FRAG_PHONENUMBER 	= "PhoneNumber";
//		String FRAG_ETC 			= "Etc";
//	}
//
//	interface FragTagString{
//		String FRAG_ROOT_LIST = "RsvListTab";
//		String FRAG_ROOT_WRITE = "RsvWriteTab";
//
//		String FRAG_LIST = "RsvMsgFrag";
//		String FRAG_WRITE = "WriteMsgFrag";
//		String FRAG_CONTACT = "ContactFrag";
//	}
//
////	final static String FRAG_LIST_ROOT = "RsvListTab";
////	final static String FRAG_WRITE_ROOT = "RsvWriteTab";
////
////	final static String FRAG_LIST = "RsvMsgFrag";
////	final static String FRAG_WRITE = "WriteMsgFrag";
////	final static String FRAG_CONTACT = "ContactFrag";
//
//	void moveTabFocus(String tag);
//	void moveTabFocus(String tag, Bundle data);
//
//	void chgFragment(String showTag, String hideTag);
	
	void shareData(String tag, Bundle data);
}
