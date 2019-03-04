package com.steven.base.rx;

import com.steven.base.bean.BaseEntity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author :setven
 * @date :2018/4/17
 * @description : RxJava2工具类，用于线程切换等
 */
public class RxHelper {

    /**
     * 用于订阅转换，返回CompositeDisposable便于取消订阅，同意管理
     *
     * @param observable 被订阅者
     * @param observer   订阅者
     */
    public static <T> Disposable subscribe(Observable<T> observable, final Observer<T> observer) {
        return observable.subscribe(
                new Consumer<T>() {
                    @Override
                    public void accept(T t) throws Exception {
                        observer.onNext(t);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        observer.onError(throwable);
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        observer.onComplete();
                    }
                }, new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        observer.onSubscribe(disposable);
                    }
                }
        );
    }

    /**
     * 线程切换
     *
     * @param <T> 对象
     */
    public static <T> ObservableTransformer<T, T> applySchedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public static <T> ObservableTransformer<T, T> customResult() {
        return new ObservableTransformer<T, T>() {
            @Override
            public Observable<T> apply(Observable<T> upstream) {
                return upstream.flatMap(new Function<T, Observable<T>>() {
                    @Override
                    public Observable<T> apply(T entry) throws Exception {
                        return createData(entry);

                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

            }
        };
    }

    /**
     * 创建成功的数据
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> emitter) throws Exception {
                {
                    try {
                        emitter.onNext(data);
                        emitter.onComplete();
                    } catch (Exception e) {
                        emitter.onError(e);
                    }
                }
            }
        });

    }
    /**
     * Rx优雅处理服务器返回
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<BaseEntity<T>, T> getResult() {
        return new ObservableTransformer<BaseEntity<T>, T>() {
            @Override
            public Observable<T> apply(Observable<BaseEntity<T>> upstream) {
                return upstream.flatMap(new Function<BaseEntity<T>, Observable<T>>() {
                    @Override
                    public Observable<T> apply(BaseEntity<T> entry) throws Exception {
                        if (entry.success()) {
                            return createData(entry.getResult());
                        } else {
                            return Observable.error(new HRException(entry.getReason()));
                        }
                    }
                }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

            }
        };
    }
}
