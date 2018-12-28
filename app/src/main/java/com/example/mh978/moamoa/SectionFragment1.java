package com.example.mh978.moamoa;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SectionFragment1 extends Fragment implements LoadJSONTask.Listener, AdapterView.OnItemClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // 추가부분
    private ListView mListView;

    public static final String URL = "https://api.learn2crack.com/android/jsonandroid/";

    private List<HashMap<String, String>> mInfoMapList = new ArrayList<>();

    private static final String KEY_VER = "ver";
    private static final String KEY_NAME = "name";
    private static final String KEY_API = "api";

    private Response response;
    //

    public SectionFragment1() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SectionFragment1 newInstance(String param1, String param2) {
        SectionFragment1 fragment = new SectionFragment1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_section1, container, false);

        // 추가부분
        mListView = (ListView) getActivity().findViewById(R.id.schoolinfo_list_view);
        mListView.setOnItemClickListener(this);
        new LoadJSONTask(this).execute(URL);

        return rootView;
    }

    // 추가부분
    @Override
    public void onLoaded(List<Info> infoList) {
        for(Info info : infoList){
            HashMap<String, String> map = new HashMap<>();

            map.put(KEY_NAME, info.getName());
            map.put(KEY_VER, info.getLog());
            map.put(KEY_API, info.getDate());

            mInfoMapList.add(map);
        }
        loadListView();
    }

    @Override
    public void onError() {
        Toast.makeText(getActivity(), "Error! ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getActivity(), mInfoMapList.get(i).get(KEY_NAME), Toast.LENGTH_SHORT).show();
    }

    private void loadListView(){
        ListAdapter adapter = new SimpleAdapter(getActivity(), mInfoMapList, R.layout.info1_school,
                new String[] { KEY_NAME, KEY_VER, KEY_API },
                new int[] { R.id.name, R.id.log, R.id.date });

        mListView.setAdapter(adapter);
    }
}
