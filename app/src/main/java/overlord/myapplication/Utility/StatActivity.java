package overlord.myapplication.Utility;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hookedonplay.decoviewlib.DecoView;
import com.hookedonplay.decoviewlib.charts.SeriesItem;
import com.hookedonplay.decoviewlib.events.DecoEvent;

import java.io.PrintWriter;
import java.net.Socket;

import overlord.myapplication.Card.CardListActivity;
import overlord.myapplication.Clue.ClueListActivity;
import overlord.myapplication.QuestionDatabase.Question;
import overlord.myapplication.QuestionDatabase.Singleton;
import overlord.myapplication.R;
import overlord.myapplication.Solved.Answer;

public class StatActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ImageButton mTrojanButton;
    ImageButton mAntiVirusButton;
    ImageButton mVirusButton;
    ImageButton mWormButton;
    TextView    mScoreView;
    TextView    mTeamID;
    int[] percentScore = new int[5];

    public void calculateScore(){
        for(int i = 0; i < 5; ++i){
            Singleton.getInstance().setAnswerArrayList(Singleton.getInstance().getAnswerDB().readDB());
            if(Singleton.getInstance().getMaximumPoints()[i] != 0)
                percentScore[i] = (int)(
                        ((double)Singleton.getInstance().getPoints()[i] /
                        (double)Singleton.getInstance().getMaximumPoints()[i])*
                                100) ;
        }
        for(int i = 0; i < 5; ++i)
            Log.d("TAG",""+percentScore[i]);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.urban_crusade);
        mAntiVirusButton = (ImageButton)findViewById(R.id.button_anti_virus);
        mVirusButton     = (ImageButton)findViewById(R.id.button_virus);
        mTrojanButton    = (ImageButton)findViewById(R.id.button_trojan);
        mWormButton      = (ImageButton)findViewById(R.id.button_worm);
        mScoreView       = (TextView)findViewById(R.id.text_score);
        mTeamID          = (TextView)findViewById(R.id.team_id_deco);

        mAntiVirusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAntiVirus();
            }
        });
        mWormButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWorm();
            }
        });
        mTrojanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTrojan();
            }
        });
        mVirusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVirus();
            }
        });
        mTeamID.setText(Integer.toString(Singleton.getInstance().getSharedPreferences().getInt(Singleton.TEAMID, -1)));

        showScore();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public int backgroundTrack(DecoView arcView, int grayValue, int lineWidth, int inset){
        return arcView.addSeries(new SeriesItem.Builder(Color.argb(255, grayValue, grayValue, grayValue))
                .setInset(new PointF(inset,inset))
                .setRange(0, 100, 100)
                .setInitialVisibility(false)
                .setLineWidth(lineWidth)
                .build());
    }
    public int foregroundTrack(DecoView arcView, String color, int lineWidth, int inset){
        return arcView.addSeries(new SeriesItem.Builder(Color.parseColor(color))
                .setRange(0, 100, 0)
                .setLineWidth(lineWidth)
                .setInset(new PointF(inset, inset))
                .build());
    }

    public void showScore(){
        DecoView arcView = (DecoView)findViewById(R.id.dynamicArcView);
        calculateScore();
        mScoreView.setText(""+Singleton.getInstance().getScore());
        //for(int i =0;i<5;++i)
        //    percentScore[i] = 20*(i+1);
        int lineWidth = 24;

        backgroundTrack(arcView, 60, lineWidth, 0);
        backgroundTrack(arcView,100, lineWidth, lineWidth);
        backgroundTrack(arcView,140, lineWidth, 2 * lineWidth);
        backgroundTrack(arcView,180, lineWidth, 3 * lineWidth);
        backgroundTrack(arcView,220, lineWidth, 4 * lineWidth);

        int wormIndex       = foregroundTrack(arcView,"#D0E8F4",lineWidth,0);
        int virusIndex      = foregroundTrack(arcView,"#00838f",2 * lineWidth,lineWidth);
        int trojanIndex     = foregroundTrack(arcView,"#2F143D",lineWidth,2 * lineWidth);
        int antiVirusIndex  = foregroundTrack(arcView,"#90AFC5",lineWidth,3 * lineWidth);
        int consoleIndex    = foregroundTrack(arcView,"#004445",lineWidth,4 * lineWidth);


        arcView.addEvent(new DecoEvent.Builder(DecoEvent.EventType.EVENT_SHOW, true)
                .setDelay(1000)
                .setDuration(2000)
                .build());

        arcView.addEvent(new DecoEvent.Builder(percentScore[Question.WORM]).setIndex(wormIndex).setDelay(1000).build());
        arcView.addEvent(new DecoEvent.Builder(percentScore[Question.VIRUS]).setIndex(virusIndex).setDelay(1500).build());
        arcView.addEvent(new DecoEvent.Builder(percentScore[Question.TROJAN]).setIndex(trojanIndex).setDelay(2000).build());
        arcView.addEvent(new DecoEvent.Builder(percentScore[Question.ANTIVIRUS]).setIndex(antiVirusIndex).setDelay(2500).build());
        arcView.addEvent(new DecoEvent.Builder(percentScore[Question.CONSOLE]).setIndex(consoleIndex).setDelay(3000).build());

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    String macID(){
        return ((WifiManager)getSystemService(WIFI_SERVICE)).getConnectionInfo().getMacAddress();
    }
    String ipAddress(){
        return Formatter.formatIpAddress(((WifiManager) getSystemService(WIFI_SERVICE)).getConnectionInfo().getIpAddress());
    }
    String laptopIP(){
        return Formatter.formatIpAddress(((WifiManager) getSystemService(WIFI_SERVICE)).getDhcpInfo().gateway);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_antivirus) {
            startAntiVirus();
        } else if (id == R.id.nav_trojan) {
            startTrojan();
            /*
            WifiManager wifiMgr = (WifiManager) getSystemService(WIFI_SERVICE);
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
            int ip = wifiInfo.getIpAddress();
            Log.d("MAC",wifiInfo.getMacAddress());
            String ipAddress = Formatter.formatIpAddress(ip);

            WifiManager manager = (WifiManager) super.getSystemService(WIFI_SERVICE);
            DhcpInfo dhcp = manager.getDhcpInfo();
            String address = Formatter.formatIpAddress(dhcp.gateway);

            Log.d("TAG",ipAddress);
            Log.d("TAG",address);
            */
        } else if (id == R.id.nav_virus) {
            startVirus();
        } else if (id == R.id.nav_console) {
            startConsole();
        } else if (id == R.id.nav_worm) {
            startWorm();
        } else if (id == R.id.nav_card) {
            Intent intent = new Intent(this, CardListActivity.class);
            startActivity(intent);
        }  else if (id == R.id.nav_submit) {
            submit();

        }  else if (id == R.id.nav_map){
            Intent intent = new Intent(StatActivity.this, MapActivity.class);
            startActivity(intent);
        } /*
        else if (id == R.id.nav_contact_dev) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:09437393272"));
            startActivity(intent);
        } else if (id == R.id.nav_contact_man) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:08763306018"));
            startActivity(intent);

        }
        else if(id == R.id.nav_clear){
            Singleton.getInstance().getAnswerDB().onUpgrade(Singleton.getInstance().getAnswerDB().getWritableDatabase(),1,2);
        }
        */
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    void startVirus(){
        Intent intent = new Intent(StatActivity.this,ClueListActivity.class);
        intent.putExtra("CATEGORY", Question.VIRUS);
        startActivity(intent);
    }
    void startAntiVirus(){
        Intent intent = new Intent(StatActivity.this,ClueListActivity.class);
        intent.putExtra(Singleton.CATEGORY,Question.ANTIVIRUS);
        startActivity(intent);
    }
    void startWorm(){
        Intent intent = new Intent(StatActivity.this,ClueListActivity.class);
        intent.putExtra("CATEGORY",Question.WORM);
        startActivity(intent);
    }
    void startTrojan(){
        Intent intent = new Intent(StatActivity.this,ClueListActivity.class);
        intent.putExtra("CATEGORY",Question.TROJAN);
        startActivity(intent);
    }
    void startConsole(){
        Intent intent = new Intent(StatActivity.this,ClueListActivity.class);
        intent.putExtra("CATEGORY",Question.CONSOLE);
        startActivity(intent);
    }
    public class SubmitServer extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            try{
                Socket socket = new Socket(laptopIP(),12345);
                PrintWriter printWriter = new PrintWriter(socket.getOutputStream(),true);
                printWriter.println(Singleton.getInstance().getSharedPreferences().getInt(Singleton.TEAMID, -1));
                for(Answer answer : Singleton.getInstance().getAnswerArrayList())
                    printWriter.println(answer.getAnswer());
                printWriter.flush();
                printWriter.close();
                socket.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    void submit(){
        try{
            new SubmitServer().execute();
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }
}
