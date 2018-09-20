package com.lilei.annotationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lilei.router_annotation.RouterNode;
import com.lilei.router_api.PrintHelloWorld;

@RouterNode(path = "hjhaj")
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PrintHelloWorld.print();
    }
}
