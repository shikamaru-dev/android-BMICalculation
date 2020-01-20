package local.hal.st31.calcbmi70321;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * ボタンのイベントリスナを設定する。
         */

        Button btCalc = findViewById(R.id.btCalc);
        btCalc.setOnClickListener(new ButtonClickListener());
        Button btClear = findViewById(R.id.btClear);
        btClear.setOnClickListener(new ButtonClickListener());
    }

    public class ButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            EditText etWeight = findViewById(R.id.etWeight);
            EditText etHeight = findViewById(R.id.etHeight);
            TextView tvAnwser = findViewById(R.id.tvAnswer);
            Context context = getApplicationContext();

            /**
             * 二つのボタンをまとめて管理する。
             */

            int id = view.getId();
            switch (id) {
                case R.id.btCalc:
                    String strWeight = etWeight.getText().toString();
                    String strHeight = etHeight.getText().toString();

                    /**
                     * エラーチェック：何にも入力しないとき。
                     */

                    if (strWeight.equals("") || strHeight.equals("")) {
                        String msg = "えええBMIに自信あるの？入力してや！！！";
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    } else {
                        double height = Double.valueOf(strHeight);

                        /**
                         * エラーチェック：身長が０を入力したとき。
                         */

                        if (height == 0) {
                            String msg = "身長がゼロなんて、出産されてないってこと？";
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                        } else {

                            /**
                             * エラーチェック：身長が300CMを超えたとき。
                             */

                            if (height >= 300) {
                                String msg = "でかすぎへん？ありえへんやな！";
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                            } else {

                                double weight = Double.valueOf(strWeight);

                                /**
                                 * エラーチェック：体重が650KGをこえたとき。
                                 */

                                if (weight >= 650) {
                                    String msg = "デブすぎへん？ありえへんやな！";
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                }
                                else {

                                    /**
                                     * エラーチェック：体重が０を入力したとき。
                                     */

                                    if (weight == 0){
                                        String msg = "体重がゼロなんて、出産されてないってこと？";
                                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                                    }

                                    /**
                                     * BMI計算処理。
                                     */

                                    else {

                                        /**
                                         * 適正体重とBMIを計算する。
                                         */

                                        BigDecimal bigRightWeight = new BigDecimal((height * height) / 10000 * 22);

                                        BigDecimal bigAns = new BigDecimal(weight / (height * height) * 10000);

                                        /**
                                         * 適正体重を整数で表示する。
                                         */

                                        int intRightWeight = bigRightWeight.intValue();

                                        /**
                                         * BMIの基準値を設定する。
                                         */

                                        BigDecimal bigBMImin = new BigDecimal(18.5);
                                        BigDecimal bigBMImax = new BigDecimal(25);

                                        /**
                                         * 小数点後１桁を四捨五入をして、１桁で表示する。
                                         */

                                        bigAns = bigAns.setScale(1, RoundingMode.HALF_UP);

                                        String strRight = Integer.toString(intRightWeight);

                                        String strAns = bigAns.toString();

                                        /**
                                         * 計算したBMIと基準値を比較して、結果に応じてアドバイスを表示する。
                                         */

                                        int result1 = bigAns.compareTo(bigBMImax);
                                        int result2 = bigAns.compareTo(bigBMImin);

                                        if (result1 >= 0) {
                                            tvAnwser.setText("あなたのBMIは" + strAns + "です。" + "肥満です。体重" + strRight + "kgを目指しましょう。");
                                        } else if (result1 < 1 & result2 >= 0) {
                                            tvAnwser.setText("あなたのBMIは" + strAns + "です。" + "ちょうどいいです。現状を維持しましょう。");
                                        } else if (result2 == -1) {
                                            tvAnwser.setText("あなたのBMIは" + strAns + "です。" + "痩せてます。体重" + strRight + "kgを目指しましょう。");

                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;

                /**
                 * クリアボタンを動かせる。
                 */

                case R.id.btClear:
                    etHeight.setText("");
                    etWeight.setText("");
                    tvAnwser.setText("");
                    break;
            }
        }
    }
}
