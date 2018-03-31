package com.example.calculator;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText mResult, mNewNumber;
    private TextView mOperation;

    private static final String STATE_PENDING_OPERATION = "PendingOperation";
    private static final String STATE_OPERAND1 = "Operand1";

    // Variables to hold the operands and type of calculations
    private Double operand1 = null;
    private String pendingOperation = "=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Results and current calculations
        mResult = findViewById(R.id.editTxt_result);
        mNewNumber = findViewById(R.id.editTxt_newNumber);
        mOperation = findViewById(R.id.txt_operation);

        // Number Buttons
        Button mBtn0 = findViewById(R.id.btn_0);
        Button mBtn1 = findViewById(R.id.btn_1);
        Button mBtn2 = findViewById(R.id.btn_2);
        Button mBtn3 = findViewById(R.id.btn_3);
        Button mBtn4 = findViewById(R.id.btn_4);
        Button mBtn5 = findViewById(R.id.btn_5);
        Button mBtn6 = findViewById(R.id.btn_6);
        Button mBtn7 = findViewById(R.id.btn_7);
        Button mBtn8 = findViewById(R.id.btn_8);
        Button mBtn9 = findViewById(R.id.btn_9);
        Button mBtnDot = findViewById(R.id.btn_dot);

        // Operation selector (buttons)
        Button mBtnDivide = findViewById(R.id.btn_divide);
        Button mBtnMultiply = findViewById(R.id.btn_multiply);
        Button mBtnEquals = findViewById(R.id.btn_equals);
        Button mBtnPlus = findViewById(R.id.btn_plus);
        Button mBtnMinus = findViewById(R.id.btn_minus);

        Button mbtnNeg = findViewById(R.id.btn_neg);

        mbtnNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = mNewNumber.getText().toString();
                if (value.length() == 0) {
                    mNewNumber.setText("-");
                } else {
                    try {
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        mNewNumber.setText(doubleValue.toString());
                    } catch (NumberFormatException e) {
                        // mNewNumber was "-" or ".", so clear it
                        mNewNumber.setText("");
                    }
                }
            }
        });

        Button mbtnClear = findViewById(R.id.btn_clear);

        mbtnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNewNumber.setText("");
                mResult.setText("");
                mOperation.setText("");
                operand1 = null;
            }
        });

        // Listener for the numbers
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                mNewNumber.append(b.getText().toString());
            }
        };

        // Button listeners for the numbers and the Dot
        mBtn0.setOnClickListener(listener);
        mBtn1.setOnClickListener(listener);
        mBtn2.setOnClickListener(listener);
        mBtn3.setOnClickListener(listener);
        mBtn4.setOnClickListener(listener);
        mBtn5.setOnClickListener(listener);
        mBtn6.setOnClickListener(listener);
        mBtn7.setOnClickListener(listener);
        mBtn8.setOnClickListener(listener);
        mBtn9.setOnClickListener(listener);
        mBtnDot.setOnClickListener(listener);

        // Listener for the operation selectors
        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = mNewNumber.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                } catch (NumberFormatException e) {
                    mNewNumber.setText("");
                }
                pendingOperation = op;
                mOperation.setText(pendingOperation);
            }
        };

        // Button listeners for the operators
        mBtnEquals.setOnClickListener(opListener);
        mBtnDivide.setOnClickListener(opListener);
        mBtnMultiply.setOnClickListener(opListener);
        mBtnMinus.setOnClickListener(opListener);
        mBtnPlus.setOnClickListener(opListener);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Saving the information for screen rotations
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if (operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        // Retrieving the information when the screen rotates
        super.onRestoreInstanceState(savedInstanceState);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        mOperation.setText(pendingOperation);
    }

    // Operation calculation method
    private void performOperation(Double value, String operation) {
        if (null == operand1) {
            operand1 = value;
        } else {
            if (pendingOperation.equals("=")) {
                pendingOperation = operation;
            }
            switch (pendingOperation) {
                case "=":
                    operand1 = value;
                    break;
                case "/":
                    if (value == 0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= value;
                    }
                    break;
                case "+":
                    operand1 += value;
                    break;
                case "-":
                    operand1 -= value;
                    break;
                case "*":
                    operand1 *= value;
                    break;
            }
        }

        mResult.setText(operand1.toString());
        mNewNumber.setText("");
    }

}
