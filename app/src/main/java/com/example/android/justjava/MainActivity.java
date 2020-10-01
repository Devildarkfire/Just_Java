package com.example.android.justjava;


import java.util.*;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
//    Scanner sc = new Scanner(System.in);
//    String name = sc.nextLine();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    int quantity=1;
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        int costOfQuantity=0;
        int costOfWhippedCream=50,costOfChocolate=75;
        EditText nameValue = (EditText) findViewById(R.id.name_field);
        String name = nameValue.getText().toString();

        EditText address = (EditText)findViewById(R.id.address_field);
        String Address=address.getText().toString();

        CheckBox checkedWhipped = (CheckBox)findViewById(R.id.check_box);
        boolean hasWhippedCream =checkedWhipped.isChecked();

        CheckBox checked = (CheckBox)findViewById(R.id.check_box1);
        boolean hasChocolate =checked.isChecked();

        if(hasWhippedCream && hasChocolate)
            costOfQuantity= costOfWhippedCream + costOfChocolate;
        else if(hasWhippedCream)
            costOfQuantity= costOfWhippedCream;
        else if(hasChocolate)
            costOfQuantity= costOfChocolate;
        else
            costOfQuantity=25;

        int price= quantity * costOfQuantity;
        String priceMessage = createOrderSummary(name,price,hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java for " + name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage + Address);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    private String createOrderSummary(String name,int price,boolean hasWhippedCream,boolean hasChocolate){
        String priceMessage= "Name - "+name;
        if(hasWhippedCream) {
            priceMessage = priceMessage + "\nExtra Whipped Cream";
        }
        if(hasChocolate)
            priceMessage = priceMessage+"\nExtra Chocolate Cream ";
        priceMessage=priceMessage + "\nQuantity - " + quantity;
        priceMessage = priceMessage +"\n"+"Total amount " + "Rs." +price;
        priceMessage = priceMessage + "\n" + getString(R.string.thank_you);
        priceMessage = priceMessage + "\nAddress - ";

        return priceMessage;
    }

    public void increment(View view){
        //int quantity = 2;
        if(quantity<10)
        quantity += 1;
        else
            Toast.makeText(this,"You cannot ordered more than 10 coffee.",Toast.LENGTH_SHORT).show();
        displayQuantity(quantity);
    }

    public void decrement(View view){
        //int quantity = 2;
        if(quantity>1)
        quantity -=1;
        else
        {
            Toast.makeText(this,"You cannot ordered less than 1 coffee.",Toast.LENGTH_SHORT).show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


}