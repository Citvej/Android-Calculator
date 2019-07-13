package com.example.kalkulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView racun;
    private TextView rezultatText;
    private Double rezultat;
    private Button nic;
    private Button ena;
    private Button dve;
    private Button tri;
    private Button stiri;
    private Button pet;
    private Button sest;
    private Button sedem;
    private Button osem;
    private Button devet;
    private Button c;
    private Button oklepaj;
    private Button procent;
    private Button deljeno;
    private Button krat;
    private Button minus;
    private Button plus;
    private Button enacaj;
    private Button pika;
    private Button plusminus;
    private String izraz = "";
    private String urejenIzraz;
    private Boolean jeOklepaj = true;
    private Boolean jeDecimalno = true;
    private Boolean evaluate = false;
    private int stevec = 0;
    private Button undo;
    private Button redo;
    private List<String> seznamIzrazov = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pripraviGumbe();
        enacaj.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view){
                Intent i = new Intent(getBaseContext(), activityinfo.class);
                startActivity(i);
                return true;
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn0:
                seznamIzrazov.add(stevec, "0");
                jeOklepaj = false;
                break;
            case R.id.btn1:
                seznamIzrazov.add(stevec, "1");
                jeOklepaj = false;
                break;
            case R.id.btn2:
                seznamIzrazov.add(stevec, "2");
                jeOklepaj = false;
                break;
            case R.id.btn3:
                seznamIzrazov.add(stevec, "3");
                jeOklepaj = false;
                break;
            case R.id.btn4:
                seznamIzrazov.add(stevec, "4");
                jeOklepaj = false;
                break;
            case R.id.btn5:
                seznamIzrazov.add(stevec, "5");
                jeOklepaj = false;
                break;
            case R.id.btn6:
                seznamIzrazov.add(stevec, "6");
                jeOklepaj = false;
                break;
            case R.id.btn7:
                seznamIzrazov.add(stevec, "7");
                jeOklepaj = false;
                break;
            case R.id.btn8:
                seznamIzrazov.add(stevec, "8");
                jeOklepaj = false;
                break;
            case R.id.btn9:
                seznamIzrazov.add(stevec, "9");
                jeOklepaj = false;
                break;
            case R.id.btnc:
                seznamIzrazov = new ArrayList<String>();
                izraz = "";
                stevec = -1;
                break;
            case R.id.btnoklepaj:
                if(jeOklepaj){
                    seznamIzrazov.add(stevec, "(");
                }else{
                    seznamIzrazov.add(stevec, ")");
                }
                jeDecimalno = true;
                break;
            case R.id.btntimes:
                seznamIzrazov.add(stevec, "*");
                jeOklepaj = true;
                jeDecimalno = true;
                break;
            case R.id.btndivided:
                seznamIzrazov.add(stevec, "/");
                jeOklepaj = true;
                jeDecimalno = true;
                break;
            case R.id.btnminus:
                seznamIzrazov.add(stevec, "-");
                jeOklepaj = true;
                jeDecimalno = true;
                break;
            case R.id.btnplus:
                seznamIzrazov.add(stevec, "+");
                jeOklepaj = true;
                jeDecimalno = true;
                break;
            case R.id.btndot:
                if (jeDecimalno) seznamIzrazov.add(stevec, ".");
                jeDecimalno = false;
                break;
            case R.id.btnplusminus:
                seznamIzrazov.add(stevec, "");
                break;
            case R.id.btnpercent:
                seznamIzrazov.add("/100");
                break;
            case R.id.btnequals:
                //izraz = rezultat.toString();
                evaluate = true;
                jeDecimalno = true;
                stevec--;
                break;
            case R.id.undo:
                if(stevec > 0) stevec-=2;
                break;
            case R.id.redo:
                if(stevec < seznamIzrazov.size()) stevec++;
                break;
            default:
                izraz = "Napaka!";
        }

        stevec++;
        izraz = seznamToString(seznamIzrazov, stevec);
        System.out.println(stevec);
        urejenIzraz = urediIzraz(izraz);
        racun.setText(urejenIzraz);
        if(evaluate){
            try {
                rezultat = eval(izraz);
                izraz = rezultat.toString();
                rezultatText.setText(urediRezultat(izraz));
            }catch (Exception e){
                rezultatText.setText("Napačen izraz");
            }
            evaluate = false;
        }

    }



    private String seznamToString(List<String> seznam, int it){
        String expr = "";
        for(int i = 0; i<it; i++){
            expr += seznam.get(i);
        }
        return expr;
    }

    private String urediIzraz(String Izraz) {
        String vrni = "";
        Character ch;
        int len = izraz.length();
        for (int i = 0; i<len; i++) {
            ch = Izraz.charAt(i);
            if(i+2 < len && evaluate && ch == '.' && izraz.charAt(i+1) == '0' && izraz.charAt(i+2) == '0' ) {
                vrni += "";
                i += 2;
            }else if(ch == '/' && izraz.charAt(i+1) == '1' && izraz.charAt(i+2) == '0' && izraz.charAt(i+3) == '0' ) {
                vrni += "";
                i += 3;
            }else if(ch == '.'){
                vrni += ',';
            }else if (ch == '*') {
                vrni += "x";
            }else{
                vrni += Izraz.charAt(i) + "";
            }

        }
        return vrni;
    }

    private String urediRezultat(String Izraz) {
        String vrni = "";
        Character ch;
        int len = izraz.length();
        for (int i = 0; i < len; i++) {
            ch = Izraz.charAt(i);
            if (i + 2 == len && ch == '.' && izraz.charAt(i + 1) == '0') {
                vrni += "";
                i += 1;
            }else if (ch == '.') {
                vrni += ',';
            }else{
                vrni += ch;
            }
        }
        return vrni;
    }

    private void pripraviGumbe(){
        nic = findViewById(R.id.btn0);
        ena = findViewById(R.id.btn1);
        dve = findViewById(R.id.btn2);
        tri = findViewById(R.id.btn3);
        stiri = findViewById(R.id.btn4);
        pet = findViewById(R.id.btn5);
        sest = findViewById(R.id.btn6);
        sedem = findViewById(R.id.btn7);
        osem = findViewById(R.id.btn8);
        devet = findViewById(R.id.btn9);
        c = findViewById(R.id.btnc);
        oklepaj = findViewById(R.id.btnoklepaj);
        procent = findViewById(R.id.btnpercent);
        deljeno = findViewById(R.id.btndivided);
        krat = findViewById(R.id.btntimes);
        minus = findViewById(R.id.btnminus);
        plus = findViewById(R.id.btnplus);
        enacaj = findViewById(R.id.btnequals);
        pika = findViewById(R.id.btndot);
        plusminus = findViewById(R.id.btnplusminus);
        racun = findViewById(R.id.textView2);
        rezultatText = findViewById(R.id.textView);
        undo = findViewById(R.id.undo);
        redo = findViewById(R.id.redo);

        nic.setOnClickListener(this);
        ena.setOnClickListener(this);
        dve.setOnClickListener(this);
        tri.setOnClickListener(this);
        stiri.setOnClickListener(this);
        pet.setOnClickListener(this);
        sest.setOnClickListener(this);
        sedem.setOnClickListener(this);
        osem.setOnClickListener(this);
        devet.setOnClickListener(this);
        c.setOnClickListener(this);
        oklepaj.setOnClickListener(this);
        procent.setOnClickListener(this);
        deljeno.setOnClickListener(this);
        krat.setOnClickListener(this);
        minus.setOnClickListener(this);
        plus.setOnClickListener(this);
        enacaj.setOnClickListener(this);
        pika.setOnClickListener(this);
        plusminus.setOnClickListener(this);
        undo.setOnClickListener(this);
        redo.setOnClickListener(this);
    }


    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
}
