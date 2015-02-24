package com.bootstrap.utils;

import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;

public final class Intents {
  
  public static Intent addContact(){
    return new Intent(ContactsContract.Intents.SHOW_OR_CREATE_CONTACT, Uri.parse("tel: "))
      .putExtra(ContactsContract.Intents.EXTRA_FORCE_CREATE, true)
      .putExtra("finishActivityOnSaveCompleted", true);
  }
  
}