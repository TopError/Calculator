package gr303.LuhsheiderGerman.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    DecimalFormat format = new DecimalFormat("#.#######");

    TextView result;
    EditText A, B;

    String[] OneNumOperations = new String[]{"sqrt", "sin", "cos", "tan", "ln"};
    //String[] TwoNumOperations = new String[]{"plus", "minus", "divide", "multiply", "pow"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        format.setDecimalSeparatorAlwaysShown(false);

        A = findViewById(R.id.editText_A);
        B = findViewById(R.id.editText_B);
        result = findViewById(R.id.textView_result);
    }

    public void button_Pi_onClick(View view) {
        A.setText(String.valueOf(Math.PI));
    }

    public void OperationButton_onClick(View v) {
        double num_A, num_B;
        String str_A, str_B, str_result = "";

        str_A = commaReplace(A.getText().toString());
        str_B = commaReplace(B.getText().toString());

        String operation = (String)v.getTag();
        if (isNum(str_A)) {
            num_A = Float.parseFloat(str_A);

            if (Arrays.asList(OneNumOperations).contains(operation)) {
                str_result = commaReplace(format.format(calculate(num_A, operation)));
                if (!isNum(str_result)) str_result = "";
            } else if (isNum(str_B)) {
                num_B = Float.parseFloat(str_B);
                str_result = commaReplace(format.format(calculate(num_A, num_B, operation)));
                if (!isNum(str_result)) str_result = "";
            }
            result.setText("Результат: ".concat(str_result));
        }
    }

    private double calculate(double A, double B, String operation) {
        switch(operation) {
            case "plus": return A + B;
            case "minus": return A - B;
            case "divide": return A / B;
            case "multiply": return A * B;
            case "pow": return Math.pow(A, B);
        }
        return 0;
    }

    private double calculate(double A, String operation) {
        switch(operation) {
            case "sqrt": return Math.sqrt(A);
            case "sin": return Math.sin(A);
            case "cos": return Math.cos(A);
            case "tan": return Math.tan(A);
            case "ln": return Math.log(A);
        }
        return 0;
    }

    private String commaReplace(String str) {
        if(str.contains(",")) return str.replace(",", ".");
        return str;
    }

    private boolean isNum(String strNum) {
        try {
            Float.parseFloat(strNum);
            if(strNum.equals("NaN")) throw new Exception();
        }catch(Exception ex) {
            createDialog("Перепроверьте введенные данные");
            return false;
        }
        return true;
    }

    private void createDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Что-то пошло не так...").setMessage(message)
                .setPositiveButton("Ок", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) { }
                });
        builder.create().show();
    }

}