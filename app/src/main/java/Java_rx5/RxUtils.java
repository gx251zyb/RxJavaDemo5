package Java_rx5;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/4/3.
 */

public class RxUtils {
    private OkHttpClient okHttpClient;
    public RxUtils(){
        okHttpClient=new OkHttpClient();
    }
    public  Observable<byte[]> downloadImage(String path){
        return Observable.create(new Observable.OnSubscribe<byte[]>() {
            @Override
            public void call(Subscriber<? super byte[]> subscriber) {
                if(!subscriber.isUnsubscribed()){
                    Request request=new Request.Builder().url(path).build();
                    okHttpClient.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.i("TAG", "onFailure: "+e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                             if(response.isSuccessful()){
                                 byte[] data=response.body().bytes();
                                 if(data!=null){
                                    subscriber.onNext(data);
                                 }
                             }
                            subscriber.onCompleted();
                        }
                    });

                }

            }
        });
    }
}
