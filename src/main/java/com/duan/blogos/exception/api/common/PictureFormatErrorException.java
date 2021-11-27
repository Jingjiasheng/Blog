package com.duan.blogos.exception.api.common;

import com.duan.blogos.exception.BaseRuntimeException;

/**
 * Created on 2018/3/1.
 * 图片格式错误
 *
 * @author j_jiasheng
 */
public class PictureFormatErrorException extends BaseRuntimeException {

    public static final int code = 22;

    public PictureFormatErrorException() {
        super(code);
    }

    public PictureFormatErrorException(String message) {
        super(message, code);
    }

    public PictureFormatErrorException(String message, Throwable e) {
        super(message, e, code);
    }
}
