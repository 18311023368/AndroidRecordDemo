package demo.record.com.androidrecorddemo;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG="RecordActivity";
    private Button mStartRecord, mStopRecord, mStartPlay, mStopPlay;
    private String pathName = "";//文件储存路径
    private AudioRecord mAudioRecord=null;
    private   boolean  inRecordMode = true; // 控制是否继续进行采样

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewLayout();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        inRecordMode=true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        inRecordMode=false;
        mAudioRecord.stop();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mAudioRecord!=null){
            inRecordMode=false;
            mAudioRecord.stop();
            mAudioRecord.release();
        }
    }

    private void findViewLayout() {
        pathName = Environment.getExternalStorageDirectory().getAbsolutePath() + UUID.randomUUID() + ".raw";
        mStartRecord = (Button) findViewById(R.id.bt_startRecord);
        mStopRecord = (Button) findViewById(R.id.bt_stopRecord);
        mStartPlay = (Button) findViewById(R.id.bt_startPlay);
        mStopPlay = (Button) findViewById(R.id.bt_stopPlay);






    }

    private void procressUI() {
        mStartRecord.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_startRecord:
                break;
            case R.id.bt_stopRecord:
                break;
            case R.id.bt_startPlay:
                break;
            case R.id.bt_stopPlay:
                break;
            default:
                break;
        }
    }

    //开始录音
    private void startRecord() {

    }

    //停止录音
    private void stopRecord() {

    }

    //播放
    private void playMediaRecord() {

    }

    //停止播放
    private void stopMediaRecord() {

    }


    Runnable recordRunnable = new Runnable() {
        @Override
        public void run() {
            record();
        }
    };

    private void record()  {
        File fileRecord = new File(pathName);
        if (fileRecord.exists()) {
            fileRecord.delete();//若文件存在删除文件
        }
        try {
            fileRecord.createNewFile();//创建新文件
            OutputStream os=new FileOutputStream(fileRecord);//创建文件输出流
            BufferedOutputStream bfo=new BufferedOutputStream(os);
            DataOutputStream dop=new DataOutputStream(bfo);//创建输出流完成
            //创建AudioRecord对象,其中需要的最小录音缓存buffer大小可以通过getMinBufferSize方法得到。如果buffer容量过小，将导致对象构造的失败
            int audioSource= MediaRecorder.AudioSource.MIC;
            int sampleRateInHz=11025;//采样率：音频的采样频率，每秒钟能够采样的次数，采样率越高，音质越高。给出的实例是44100、22050、11025但不限于这几个参数。例如要采集低质量的音频就可以使用4000、8000等低采样率。
            int channelConfig= AudioFormat.CHANNEL_OUT_STEREO;// // 设置输出声道为双声道立体声，而CHANNEL_OUT_MONO类型是单声道
            int audioFormat=AudioFormat.ENCODING_PCM_16BIT;//设置音频数据块是8位还是16位，这里设置为16位。好像现在绝大多数的音频都是16位的了
            int bufferSize = AudioRecord.getMinBufferSize(audioSource, channelConfig,  audioFormat);
            mAudioRecord=new AudioRecord(audioSource,sampleRateInHz,channelConfig,audioFormat,bufferSize);//构造函数分别代表：audioSource音频来源,sampleRateInHz采用频率,channelConfig声道,audioFormat编码格式,bufferSizeInBytes内置buffer大小


        } catch (IOException e) {
            e.printStackTrace();
            Log.d("TAG","啊哦 文件创建失败了"+fileRecord.toString());
        }

    }

}