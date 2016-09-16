package edu.bistu.computer.calculator;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    private EditText resultText;//output result
    private Button[] btn = new Button[10];//ten numbers button
    private Button add;
    private Button substract;
    private Button multiply;
    private Button divide;
    private Button point;
    private Button equals;
    private Button clear;
    private Button delete;
    private Button sin;
    private Button cos;
    private Button tan;
    private Button left_parenthese;//(
    private Button right_parenthese;//)
    private Button log;
    private Button ln;
    private Button x;
    private Button cube;
    private Button drg;

    private boolean vbegin = true;//true重新输入，flase继续输入
    private boolean drg_flag = true;//false为弧度制,true为角度值
    private boolean tip_lock = true;//true继续输入，false错误锁定
    private boolean equals_flag = true;//是否按下=之前输入，true为之前

    public String str_old;
    public String str_new;

    private TextView tip;//技巧提示

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        init();
        registerListenner();
    }

    /**
     * 初始化各组件
     */
    public void init() {
        resultText = (EditText) findViewById(R.id.result_text);
        btn[0] = (Button) findViewById(R.id.number0);
        btn[1] = (Button) findViewById(R.id.number1);
        btn[2] = (Button) findViewById(R.id.number2);
        btn[3] = (Button) findViewById(R.id.number3);
        btn[4] = (Button) findViewById(R.id.number4);
        btn[5] = (Button) findViewById(R.id.number5);
        btn[6] = (Button) findViewById(R.id.number6);
        btn[7] = (Button) findViewById(R.id.number7);
        btn[8] = (Button) findViewById(R.id.number8);
        btn[9] = (Button) findViewById(R.id.number9);
        add = (Button) findViewById(R.id.add1);
        substract = (Button) findViewById(R.id.suntract);
        multiply = (Button) findViewById(R.id.multiply1);
        divide = (Button) findViewById(R.id.divide);
        equals = (Button) findViewById(R.id.equal);
        point = (Button) findViewById(R.id.point);
        clear = (Button) findViewById(R.id.clear);
        delete = (Button) findViewById(R.id.delete);
        sin = (Button) findViewById(R.id.sin);
        cos = (Button) findViewById(R.id.cos);
        tan = (Button) findViewById(R.id.tan);
        left_parenthese = (Button) findViewById(R.id.left_parenthese);
        right_parenthese = (Button) findViewById(R.id.right_parenthese);
        log = (Button) findViewById(R.id.log);
        ln = (Button) findViewById(R.id.ln);
        x = (Button) findViewById(R.id.x);
        cube = (Button) findViewById(R.id.cube);
        drg = (Button) findViewById(R.id.DRG);

        tip = (TextView) findViewById(R.id.tip);
    }

    /**
     * 为每个组件添加监听器
     */
    private void registerListenner() {

        for (Button b : btn) {
            b.setOnClickListener(listener);
        }

        Configuration configuration = getResources().getConfiguration();
        add.setOnClickListener(listener);
        substract.setOnClickListener(listener);
        multiply.setOnClickListener(listener);
        divide.setOnClickListener(listener);
        equals.setOnClickListener(listener);
        point.setOnClickListener(listener);
        delete.setOnClickListener(listener);
        clear.setOnClickListener(listener);
        if (Configuration.ORIENTATION_LANDSCAPE == configuration.orientation) {
            sin.setOnClickListener(listener);
            cos.setOnClickListener(listener);
            tan.setOnClickListener(listener);
            left_parenthese.setOnClickListener(listener);
            right_parenthese.setOnClickListener(listener);
            log.setOnClickListener(listener);
            ln.setOnClickListener(listener);
            x.setOnClickListener(listener);
            cube.setOnClickListener(listener);
            drg.setOnClickListener(listener);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_help:
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_exit:
                finish();
                return true;
            case R.id.action_unit_conversion:
                Intent intent2 = new Intent(MainActivity.this, DetailActivity.class);
                startActivity(intent2);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    String[] TipCommand = new String[500];
    int tip_i = 0;// TipCommand的指针
    private View.OnClickListener listener;

    {
        listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String command = ((Button) view).getText().toString();
                String str = resultText.getText().toString();

                if (!equals_flag && "0123456789.()sincostanlnlogn!+-×÷^".contains(command)) {
                    if (right(str)) {
                        if ("+-×÷^)".contains(command)) {
                            for (int i = 0; i < str.length(); i++) {
                                TipCommand[tip_i] = String.valueOf(str.charAt(i));
                                tip_i++;
                            }
                            vbegin = false;
                        }
                    } else {
                        resultText.setText("0");
                        vbegin = true;
                        tip_i = 0;
                        tip_lock = true;
                    }
                    equals_flag = true;
                }
                if (tip_i > 0)
                    TipChecker(TipCommand[tip_i - 1], command);
                else if (tip_i == 0) {
                    TipChecker("#", command);
                }
                if ("0123456789.()sincostanlnlogn!+-×÷^".contains(command)
                        && tip_lock) {
                    TipCommand[tip_i] = command;
                    tip_i++;
                }
                // 若输入正确，则将输入信息显示到显示器上
                if ("0123456789.()sincostanlnlogn!+-×÷^".contains(command)
                        && tip_lock) {
                    print(command);
                    // 若果点击了“DRG”，则切换当前弧度角度制
                } else if ((command.compareTo("DRG") == 0 || command.compareTo("RAD") == 0)
                        && tip_lock) {
                    if (drg_flag) {
                        drg_flag = false;
                        drg.setText("RAD");
                    } else {
                        drg_flag = true;
                        drg.setText("DRG");
                    }
                    // 如果输入时退格键，并且是在按=之前
                } else if (command.compareTo("backspace") == 0 && equals_flag) {
                    // 一次删除3个字符
                    if (TTO(str) == 3) {
                        if (str.length() > 3)
                            resultText.setText(str.substring(0, str.length() - 3));
                        else if (str.length() == 3) {
                            resultText.setText("0");
                            vbegin = true;
                            tip_i = 0;
                        }
                        // 依次删除2个字符
                    } else if (TTO(str) == 2) {
                        if (str.length() > 2)
                            resultText.setText(str.substring(0, str.length() - 2));
                        else if (str.length() == 2) {
                            resultText.setText("0");
                            vbegin = true;
                            tip_i = 0;
                        }
                        // 依次删除一个字符
                    } else if (TTO(str) == 1) {
                        // 若之前输入的字符串合法则删除一个字符
                        if (right(str)) {
                            if (str.length() > 1)
                                resultText.setText(str.substring(0, str.length() - 1));
                            else if (str.length() == 1) {
                                resultText.setText("0");
                                vbegin = true;
                                tip_i = 0;
                            }
                            // 若之前输入的字符串不合法则删除全部字符
                        } else {
                            resultText.setText("0");
                            vbegin = true;
                            tip_i = 0;
                        }
                    }
                    if (resultText.getText().toString().compareTo("-") == 0
                            || !equals_flag) {
                        resultText.setText("0");
                        vbegin = true;
                        tip_i = 0;
                    }
                    tip_lock = true;
                    if (tip_i > 0)
                        tip_i--;
                    // 如果是在按=之后输入退格键
                } else if (command.compareTo("backspace") == 0 && !equals_flag) {
                    // 将显示器内容设置为0
                    resultText.setText("0");
                    vbegin = true;
                    tip_i = 0;
                    tip_lock = true;
                    // 如果输入的是清除键
                } else if (command.compareTo("C") == 0) {
                    // 将显示器内容设置为0
                    resultText.setText("0");
                    // 重新输入标志置为true
                    vbegin = true;
                    // 缓存命令位数清0
                    tip_i = 0;
                    // 表明可以继续输入
                    tip_lock = true;
                    // 表明输入=之前
                    equals_flag = true;
                } else if (command.compareTo("=") == 0 && tip_lock && right(str)
                        && equals_flag) {
                    tip_i = 0;
                    // 表明不可以继续输入
                    tip_lock = false;
                    // 表明输入=之后
                    equals_flag = false;
                    // 保存原来算式样子
                    str_old = str;
                    // 替换算式中的运算符，便于计算
                    str = str.replaceAll("sin", "s");
                    str = str.replaceAll("cos", "c");
                    str = str.replaceAll("tan", "t");
                    str = str.replaceAll("log", "g");
                    str = str.replaceAll("ln", "l");
                    str = str.replaceAll("n!", "!");
                    // 重新输入标志设置true
                    vbegin = true;
                    // 将-1x转换成-
                    str_new = str.replaceAll("-", "-1×");
                    // 计算算式结果
                    String str1 = new Calculator().process(str_new, str_old, drg_flag);
                    resultText.setText(str1);
                }
                // 表明可以继续输入
                tip_lock = true;
            }
        };
    }


    private void TipChecker(String tipcommand1, String tipcommand2) {

        // Tipcode1表示错误类型，Tipcode2表示名词解释类型
        int Tipcode1 = 0, Tipcode2 = 0;
        // 表示命令类型
        int tiptype1 = 0, tiptype2 = 0;
        // 括号数
        int bracket = 0;
        // “+-x÷^”不能作为第一位
        if (tipcommand1.compareTo("#") == 0
                && (tipcommand2.compareTo("÷") == 0
                || tipcommand2.compareTo("×") == 0
                || tipcommand2.compareTo("+") == 0
                || tipcommand2.compareTo(")") == 0
                || tipcommand2.compareTo("^") == 0)) {
            Tipcode1 = -1;
        }
        // 定义存储字符串中最后一位的类型
        else if (tipcommand1.compareTo("#") != 0) {
            if (tipcommand1.compareTo("(") == 0) {
                tiptype1 = 1;
            } else if (tipcommand1.compareTo(")") == 0) {
                tiptype1 = 2;
            } else if (tipcommand1.compareTo(".") == 0) {
                tiptype1 = 3;
            } else if ("0123456789".contains(tipcommand1)) {
                tiptype1 = 4;
            } else if ("+-×÷".contains(tipcommand1)) {
                tiptype1 = 5;
            } else if ("^".contains(tipcommand1)) {
                tiptype1 = 6;
            } else if ("sincostanloglnn!".contains(tipcommand1)) {
                tiptype1 = 7;
            }
            // 定义欲输入的按键类型
            if (tipcommand2.compareTo("(") == 0) {
                tiptype2 = 1;
            } else if (tipcommand2.compareTo(")") == 0) {
                tiptype2 = 2;
            } else if (tipcommand2.compareTo(".") == 0) {
                tiptype2 = 3;
            } else if ("0123456789".contains(tipcommand2)) {
                tiptype2 = 4;
            } else if ("+-×÷".contains(tipcommand2)) {
                tiptype2 = 5;
            } else if ("^".contains(tipcommand2)) {
                tiptype2 = 6;
            } else if ("sincostanloglnn!".contains(tipcommand2)) {
                tiptype2 = 7;
            }

            switch (tiptype1) {
                case 1:
                    // 左括号后面直接接右括号,“+x÷”（负号“-”不算）,或者"√^"
                    if (tiptype2 == 2
                            || (tiptype2 == 5 && tipcommand2.compareTo("-") != 0)
                            || tiptype2 == 6)
                        Tipcode1 = 1;
                    break;
                case 2:
                    // 右括号后面接左括号，数字，“+-x÷sin^...”
                    if (tiptype2 == 1 || tiptype2 == 3 || tiptype2 == 4
                            || tiptype2 == 7)
                        Tipcode1 = 2;
                    break;
                case 3:
                    // “.”后面接左括号或者“sincos...”
                    if (tiptype2 == 1 || tiptype2 == 7)
                        Tipcode1 = 3;
                    // 连续输入两个“.”
                    if (tiptype2 == 3)
                        Tipcode1 = 8;
                    break;
                case 4:
                    // 数字后面直接接左括号或者“sincos...”
                    if (tiptype2 == 1 || tiptype2 == 7)
                        Tipcode1 = 4;
                    break;
                case 5:
                    // “+-x÷”后面直接接右括号，“+-x÷^”
                    if (tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6)
                        Tipcode1 = 5;
                    break;
                case 6:
                    // “^”后面直接接右括号，“+-x÷^”以及“sincos...”
                    if (tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6
                            || tiptype2 == 7)
                        Tipcode1 = 6;
                    break;
                case 7:
                    // “sincos...”后面直接接右括号“+-x÷^”以及“sincos...”
                    if (tiptype2 == 2 || tiptype2 == 5 || tiptype2 == 6
                            || tiptype2 == 7)
                        Tipcode1 = 7;
                    break;
            }
        }
        // 检测小数点的重复性，Tipconde1=0,表明满足前面的规则
        if (Tipcode1 == 0 && tipcommand2.compareTo(".") == 0) {
            int tip_point = 0;
            for (int i = 0; i < tip_i; i++) {
                // 若之前出现一个小数点点，则小数点计数加1
                if (TipCommand[i].compareTo(".") == 0) {
                    tip_point++;
                }
                // 若出现以下几个运算符之一，小数点计数清零
                if (TipCommand[i].compareTo("sin") == 0
                        || TipCommand[i].compareTo("cos") == 0
                        || TipCommand[i].compareTo("tan") == 0
                        || TipCommand[i].compareTo("log") == 0
                        || TipCommand[i].compareTo("ln") == 0
                        || TipCommand[i].compareTo("n!") == 0
                        || TipCommand[i].compareTo("^") == 0
                        || TipCommand[i].compareTo("÷") == 0
                        || TipCommand[i].compareTo("×") == 0
                        || TipCommand[i].compareTo("-") == 0
                        || TipCommand[i].compareTo("+") == 0
                        || TipCommand[i].compareTo("(") == 0
                        || TipCommand[i].compareTo(")") == 0) {
                    tip_point = 0;
                }
            }
            tip_point++;
            // 若小数点计数大于1，表明小数点重复了
            if (tip_point > 1) {
                Tipcode1 = 8;
            }
        }
        // 检测右括号是否匹配
        if (Tipcode1 == 0 && tipcommand2.compareTo(")") == 0) {
            int tip_right_bracket = 0;
            for (int i = 0; i < tip_i; i++) {
                // 如果出现一个左括号，则计数加1
                if (TipCommand[i].compareTo("(") == 0) {
                    tip_right_bracket++;
                }
                // 如果出现一个右括号，则计数减1
                if (TipCommand[i].compareTo(")") == 0) {
                    tip_right_bracket--;
                }
            }
            // 如果右括号计数=0,表明没有响应的左括号与当前右括号匹配
            if (tip_right_bracket == 0) {
                Tipcode1 = 10;
            }
        }
        // 检查输入=的合法性
        if (Tipcode1 == 0 && tipcommand2.compareTo("=") == 0) {
            // 括号匹配数
            int tip_bracket = 0;
            for (int i = 0; i < tip_i; i++) {
                if (TipCommand[i].compareTo("(") == 0) {
                    tip_bracket++;
                }
                if (TipCommand[i].compareTo(")") == 0) {
                    tip_bracket--;
                }
            }
            // 若大于0，表明左括号还有未匹配的
            if (tip_bracket > 0) {
                Tipcode1 = 9;
                bracket = tip_bracket;
            } else if (tip_bracket == 0) {
                // 若前一个字符是以下之一，表明=号不合法
                if ("^sincostanloglnn!".contains(tipcommand1)) {
                    Tipcode1 = 6;
                }
                // 若前一个字符是以下之一，表明=号不合法
                if ("+-×÷".contains(tipcommand1)) {
                    Tipcode1 = 5;
                }
            }
        }

        // 若命令式以下之一，则显示相应的帮助信息
        if (tipcommand2.compareTo("MC") == 0)
            Tipcode2 = 1;
        if (tipcommand2.compareTo("C") == 0)
            Tipcode2 = 2;
        if (tipcommand2.compareTo("DRG") == 0)
            Tipcode2 = 3;
        if (tipcommand2.compareTo("Bksp") == 0)
            Tipcode2 = 4;
        if (tipcommand2.compareTo("sin") == 0)
            Tipcode2 = 5;
        if (tipcommand2.compareTo("cos") == 0)
            Tipcode2 = 6;
        if (tipcommand2.compareTo("tan") == 0)
            Tipcode2 = 7;
        if (tipcommand2.compareTo("log") == 0)
            Tipcode2 = 8;
        if (tipcommand2.compareTo("ln") == 0)
            Tipcode2 = 9;
        if (tipcommand2.compareTo("n!") == 0)
            Tipcode2 = 10;
        if (tipcommand2.compareTo("√") == 0)
            Tipcode2 = 11;
        if (tipcommand2.compareTo("^") == 0)
            Tipcode2 = 12;
        // 显示帮助和错误信息
        TipShow(bracket, Tipcode1, Tipcode2, tipcommand1, tipcommand2);
    }

    /*
     * 反馈Tip信息，加强人机交互，与TipChecker()配合使用
     */
    private void TipShow(int bracket, int tipcode1, int tipcode2,
                         String tipcommand1, String tipcommand2) {

        String tipmessage = "";
        if (tipcode1 != 0)
            tip_lock = false;// 表明输入有误
        switch (tipcode1) {
            case -1:
                tipmessage = tipcommand2 + "  不能作为第一个算符\n";
                break;
            case 1:
                tipmessage = tipcommand1 + "  后应输入：数字/(/./-/函数 \n";
                break;
            case 2:
                tipmessage = tipcommand1 + "  后应输入：)/算符 \n";
                break;
            case 3:
                tipmessage = tipcommand1 + "  后应输入：)/数字/算符 \n";
                break;
            case 4:
                tipmessage = tipcommand1 + "  后应输入：)/./数字 /算符 \n";
                break;
            case 5:
                tipmessage = tipcommand1 + "  后应输入：(/./数字/函数 \n";
                break;
            case 6:
                tipmessage = tipcommand1 + "  后应输入：(/./数字 \n";
                break;
            case 7:
                tipmessage = tipcommand1 + "  后应输入：(/./数字 \n";
                break;
            case 8:
                tipmessage = "小数点重复\n";
                break;
            case 9:
                tipmessage = "不能计算，缺少 " + bracket + " 个 )";
                break;
            case 10:
                tipmessage = "不需要  )";
                break;
        }
        switch (tipcode2) {
            case 1:
                tipmessage = tipmessage + "[MC 用法: 清除记忆 MEM]";
                break;
            case 2:
                tipmessage = tipmessage + "[C 用法: 归零]";
                break;
            case 3:
                tipmessage = tipmessage + "[DRG 用法: 选择 DEG 或 RAD]";
                break;
            case 4:
                tipmessage = tipmessage + "[backspace 用法: 退格]";
                break;
            case 5:
                tipmessage = tipmessage + "sin 函数用法示例：\n"
                        + "DEG：sin30 = 0.5      RAD：sin1 = 0.84\n"
                        + "注：与其他函数一起使用时要加括号，如：\n" + "sin(cos45)，而不是sincos45";
                break;
            case 6:
                tipmessage = tipmessage + "cos 函数用法示例：\n"
                        + "DEG：cos60 = 0.5      RAD：cos1 = 0.54\n"
                        + "注：与其他函数一起使用时要加括号，如：\n" + "cos(sin45)，而不是cossin45";
                break;
            case 7:
                tipmessage = tipmessage + "tan 函数用法示例：\n"
                        + "DEG：tan45 = 1      RAD：tan1 = 1.55\n"
                        + "注：与其他函数一起使用时要加括号，如：\n" + "tan(cos45)，而不是tancos45";
                break;
            case 8:
                tipmessage = tipmessage + "log 函数用法示例：\n"
                        + "log10 = log(5+5) = 1\n" + "注：与其他函数一起使用时要加括号，如：\n"
                        + "log(tan45)，而不是logtan45";
                break;
            case 9:
                tipmessage = tipmessage + "ln 函数用法示例：\n"
                        + "ln10 = le(5+5) = 2.3   lne = 1\n"
                        + "注：与其他函数一起使用时要加括号，如：\n" + "ln(tan45)，而不是lntan45";
                break;
            case 10:
                tipmessage = tipmessage + "n! 函数用法示例：\n"
                        + "n!3 = n!(1+2) = 3×2×1 = 6\n" + "注：与其他函数一起使用时要加括号，如：\n"
                        + "n!(log1000)，而不是n!log1000";
                break;
            case 11:
                tipmessage = tipmessage + "√ 用法示例：开任意次根号\n"
                        + "如：27开3次根为  27√3 = 3\n" + "注：与其他函数一起使用时要加括号，如：\n"
                        + "(函数)√(函数) ， (n!3)√(log100) = 2.45";
                break;
            case 12:
                tipmessage = tipmessage + "^ 用法示例：开任意次平方\n" + "如：2的3次方为  2^3 = 8\n"
                        + "注：与其他函数一起使用时要加括号，如：\n"
                        + "(函数)√(函数) ， (n!3)^(log100) = 36";
                break;
        }
        // 将提示信息显示到tip
        tip.setText(tipmessage);

    }

    /**
     * 将信息显示在显示屏上
     */
    private void print(String str) {
        // 清屏后输出
        if (vbegin) {
            resultText.setText(str);
        } else {
            resultText.append(str);
        }
        vbegin = false;
    }

    /*
     * 检测函数，返回值为3、2、1 表示应当一次删除几个？ Three+Two+One = TTO 为backsapce按钮的删除方式提供依据
     * 返回3，表示str尾部为sin、cos、tan、log中的一个，应当一次删除3个
     * 返回2，表示str尾部为ln、n!中的一个，应当一次删除2个
     * 返回1，表示为除返回3、2外的所有情况，只需删除一个（包含非法字符时要另外考虑：应清屏）
     */
    private int TTO(String str) {
        if ((str.charAt(str.length() - 1) == 'n'
                && str.charAt(str.length() - 2) == 'i' && str.charAt(str
                .length() - 3) == 's')
                || (str.charAt(str.length() - 1) == 's'
                && str.charAt(str.length() - 2) == 'o' && str
                .charAt(str.length() - 3) == 'c')
                || (str.charAt(str.length() - 1) == 'n'
                && str.charAt(str.length() - 2) == 'a' && str
                .charAt(str.length() - 3) == 't')
                || (str.charAt(str.length() - 1) == 'g'
                && str.charAt(str.length() - 2) == 'o' && str
                .charAt(str.length() - 3) == 'l')) {
            return 3;
        } else if ((str.charAt(str.length() - 1) == 'n' && str.charAt(str
                .length() - 2) == 'l')
                || (str.charAt(str.length() - 1) == '!' && str.charAt(str
                .length() - 2) == 'n')) {
            return 2;
        } else {
            return 1;
        }
    }

    /*
     * 判断一个str是否是合法的，返回值为true、false
     * 只包含0123456789.()sincostanlnlogn!+-×÷^的是合法的str，返回true
     * 包含了除0123456789.()sincostanlnlogn!+-×÷^以外的字符的str为非法的，返回false
     */
    private boolean right(String str) {
        int i;
        for (i = 0; i < str.length(); i++) {
            if (str.charAt(i) != '0' && str.charAt(i) != '1'
                    && str.charAt(i) != '2' && str.charAt(i) != '3'
                    && str.charAt(i) != '4' && str.charAt(i) != '5'
                    && str.charAt(i) != '6' && str.charAt(i) != '7'
                    && str.charAt(i) != '8' && str.charAt(i) != '9'
                    && str.charAt(i) != '.' && str.charAt(i) != '-'
                    && str.charAt(i) != '+' && str.charAt(i) != '×'
                    && str.charAt(i) != '÷' && str.charAt(i) != '^'
                    && str.charAt(i) != 's' && str.charAt(i) != 'i'
                    && str.charAt(i) != 'n' && str.charAt(i) != 'c'
                    && str.charAt(i) != 'o' && str.charAt(i) != 't'
                    && str.charAt(i) != 'a' && str.charAt(i) != 'l'
                    && str.charAt(i) != 'g' && str.charAt(i) != '('
                    && str.charAt(i) != ')' && str.charAt(i) != '!')
                break;
        }
        return str.length() == i;
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://edu.bistu.computer.calculator/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://edu.bistu.computer.calculator/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
