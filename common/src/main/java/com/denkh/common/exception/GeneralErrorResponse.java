package com.denkh.common.exception;
import com.denkh.common.constant.ApiConstant;
import com.denkh.common.dto.EmptyObject;

import java.util.ArrayList;
import java.util.List;

public class GeneralErrorResponse {

    public static ResponseErrorTemplate generalError(){
        return new ResponseErrorTemplate(
                ApiConstant.GENERAL_ERROR.getDescription(),
                ApiConstant.GENERAL_ERROR.getKey(),
                new EmptyObject(),
                true);
    }

    public static ResponseErrorTemplate generalErrors(){
        List<EmptyObject> emptyObjectList = new ArrayList<>();
        return new ResponseErrorTemplate(
                ApiConstant.GENERAL_ERROR.getDescription(),
                ApiConstant.GENERAL_ERROR.getKey(),
                emptyObjectList,
                true);
    }

}