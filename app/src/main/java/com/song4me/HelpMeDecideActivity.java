package com.song4me;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpMeDecideActivity extends AppCompatActivity {

    private static final String REDIRECT_URI = "testschema://callback";
    public static String TAG = "HelpMeDecide :";
    private static String CLIENT_ID = "";
    @BindView(R.id.listView)
    ListView listView;
    ArrayAdapter<String> adapter;
    private SpotifyAppRemote mSpotifyAppRemote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_me_decide);
        ButterKnife.bind(this);

        CLIENT_ID = getResources().getString(R.string.spotify_clientID);

        String[] moodStringArray = getResources().getStringArray(R.array.moodsArrayList);

        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, moodStringArray);

        listView.setAdapter(adapter);

        SpotifyAppRemote.connect(this, connectionParams, new Connector.ConnectionListener() {
            @Override
            public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                mSpotifyAppRemote = spotifyAppRemote;
                Toast.makeText(HelpMeDecideActivity.this, "Connected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Throwable throwable) {

                Log.d(TAG, "onFailure: " + throwable.getLocalizedMessage());
                Toast.makeText(HelpMeDecideActivity.this, "Failed : " + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String moodString = adapterView.getItemAtPosition(i).toString();

                Toast.makeText(HelpMeDecideActivity.this, "" + adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setComponent(new ComponentName("com.spotify.music", "com.spotify.music.MainActivity"));

                switch (moodString) {
                    case "Happy":

                        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX2sUQwD7tbmL");
                        startActivity(intent);


                        break;

                    case "Mellow":
                        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX5gQonLbZD9s");
                        startActivity(intent);

                        break;

                    case "Pumped":
                        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DWXTcPFeNCMUP");
                        startActivity(intent);

                        break;

                    case "Energize":
                        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:7hHIr9cHDBO1K8Digl1q8w");
                        startActivity(intent);

                        break;

                    case "Feeling like a snacc":
                        mSpotifyAppRemote.getPlayerApi().play("spotify:playlist:37i9dQZF1DX6VdMW310YC7");
                        startActivity(intent);

                        break;

                    default:
                        break;

                }

            }
        });

    }
}
