package powertools.mpowerdice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView tvRandName,tvQuestion;

    private ImageButton btnSkip,btnNext,btnRand;
    private List<String> lst = new ArrayList<String>();
    private List<String> qlst= new ArrayList<String>();
    private String name;
    private int count = 0;
    private Firebase mNameRef,mQuestionRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvQuestion = (TextView) findViewById(R.id.QuestionView);
        tvRandName = (TextView) findViewById(R.id.NameView);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        btnRand = (ImageButton) findViewById(R.id.btnRand);
        btnSkip = (ImageButton) findViewById(R.id.btnSkip);
        mQuestionRef = new Firebase("https://m-power-38aa6.firebaseio.com/Questions");

        mQuestionRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
               qlst.clear();
                for(DataSnapshot dsp : dataSnapshot.getChildren()) {
                    qlst.add(String.valueOf(dsp.getValue()));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        mNameRef = new Firebase("https://m-power-38aa6.firebaseio.com/Users");
        mNameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                lst.clear();
                    for(DataSnapshot dsp : dataSnapshot.getChildren()){
                    lst.add(String.valueOf(dsp.getKey()));
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        btnRand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Collections.shuffle(lst);
                try{name = lst.remove(0);}
                catch (Exception e){

                    name = "Iedereen is geweest";
                }
                tvRandName.setText(name);
            }
        });
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tempName = name;
                Collections.shuffle(lst);
                try{name = lst.remove(0);}
                catch (Exception e) {


                    tvRandName.setText(name);
                }
                lst.add(tempName);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                try{tvQuestion.setText(qlst.get(count));}
                catch (Exception i){
                    count =0;

                }
                mNameRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        lst.clear();
                        for(DataSnapshot dsp : dataSnapshot.getChildren()){
                            lst.add(String.valueOf(dsp.getKey()));
                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });
            }
        });
    }
}
