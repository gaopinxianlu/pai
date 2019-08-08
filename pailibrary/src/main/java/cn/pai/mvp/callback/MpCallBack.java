package cn.pai.mvp.callback;

/**
 * description：mode与presenter的回调接口
 * 两个泛型，成功返回和失败返回的数据类型可能不一样
 * 比如有些时候，列表数据，成功需要返回List对象，而交易失败不需要List对象
 * author：pany
 * on 2018/1/23 10:35
 */
public interface MpCallBack<T, H> {

    /**
     * 成功
     *
     * @param resp 回调值
     */
    void onSuccess(T resp);

    /**
     * 失败
     *
     * @param resp    回调值
     * @param message 失败原因
     */
    void onFailure(H resp, String message);
}
