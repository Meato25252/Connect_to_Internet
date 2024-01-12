package com.example.connecttointernet;

import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {

    /// 参考　Cloud Firestore を使ってみる
    /// https://firebase.google.com/docs/firestore/quickstart?hl=ja

    public static final String TAG = "InspirationQuote";
    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("users/03VXPB8CJHUgqWFuIAZf");//現在の位置を取得

    boolean flg = false;

    /// MainActivityが別の画面により背面に移動後に，再度最前面の表示になった場合にも実行される。
    @Override
    protected void onStart() {
        super.onStart();  /// 親クラスの同じメソッドを実行しておく。

        /// Firestoreの保存データに変更がある場合に，onEventメソッドが実行される。
        /// スマホと別のスマホ間における【通知機能】としても利用可能。
        mDocRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                /// データが存在する場合に処理を実行。
                if (flg && documentSnapshot != null && documentSnapshot.exists()) {
                    String parent = documentSnapshot.getString("parent");
                    if(parent.equals("s")){
                        Toast.makeText(MainActivity.this,"第三者がQRコードを読み込みました",Toast.LENGTH_LONG).show();
                    }
                    if(parent.equals(("p"))){
                        Toast.makeText(MainActivity.this,parent,Toast.LENGTH_LONG).show();
                    }

                } else if (e != null) {
                    Log.w(TAG, "Got an exceptiion!", e);
                }
                flg = true;
            }
        });
    }
}