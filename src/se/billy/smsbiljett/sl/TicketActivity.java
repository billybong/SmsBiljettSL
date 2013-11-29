package se.billy.smsbiljett.sl;

import se.billy.smsbiljett.sl.domain.Ticket;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class TicketActivity extends Activity {

	private Ticket ticket;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ticket);
		
		this.ticket = new Ticket();
		initDiscountSelection();
		initActivationLink();
	}

	private void initActivationLink() {
		TextView mLink = (TextView) findViewById(R.id.discount_terms);
		mLink.setMovementMethod(LinkMovementMethod.getInstance());
		
	}

	private void initDiscountSelection() {
		Button fullPrice = (Button) findViewById(R.id.button_no_discount);
		fullPrice.setPressed(true);
		fullPrice.setSelected(true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.ticket, menu);
		return true;
	}

	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		showInfoDialog(getString(R.string.action_info), getString(R.string.created_by));
		
		return true;
	}

	public void toggleDiscount(View selected){
		
		selected.setPressed(true);
		selected.setSelected(true);
		
		switch (selected.getId()){
		case R.id.button_discount:
			deselectOtherButton((Button) findViewById(R.id.button_no_discount));
			break;
		case R.id.button_no_discount:
			deselectOtherButton((Button) findViewById(R.id.button_discount));
			break;
		}
		
		recalculatePrice(selected);
	}
	
	private void deselectOtherButton(Button button) {
		button.setSelected(false);
		button.setPressed(false);
		
	}

	public void recalculatePrice(View v){
	
		Button discountButton = (Button) findViewById(R.id.button_discount);
		this.ticket.setReducedPrice(discountButton.isSelected());
		
		CheckBox zonA = (CheckBox) findViewById(R.id.zoneA);
		this.ticket.setZoneA(zonA.isChecked());
		
		CheckBox zonB = (CheckBox) findViewById(R.id.zoneB);
		this.ticket.setZoneB(zonB.isChecked());
		
		CheckBox zonC = (CheckBox) findViewById(R.id.zoneC);
		this.ticket.setZoneC(zonC.isChecked());
		
		
		TextView priceText = (TextView) findViewById(R.id.priceText);
		TextView codeText = (TextView) findViewById(R.id.codeText);
		
		priceText.setText(ticket.calculatePrice() + " SEK");
		codeText.setText(ticket.getText());
	}
	
	public void createSms(View v){
		
		if(this.ticket.calculatePrice()==0){
			showInfoDialog(getString(R.string.dialog_empty_price), getString(R.string.markZone));
			return;
		}
		
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", getString(R.string.sl_phone_nr), null));
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("sms_body", this.ticket.getText());

        startActivity(i);
	}
	
	private void showInfoDialog(String title, String infoText) {
		// custom dialog
				final Dialog dialog = new Dialog(this);
				dialog.setContentView(R.layout.dialog);
				dialog.setTitle(title);
		 
					// set the custom dialog components - text, image and button
				TextView text = (TextView) dialog.findViewById(R.id.text);
				text.setText(infoText);
		 
				Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
				// if button is clicked, close the custom dialog
				dialogButton.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}
				});
		 
				dialog.show();
	}
}
