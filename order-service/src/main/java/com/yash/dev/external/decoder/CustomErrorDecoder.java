package com.yash.dev.external.decoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yash.dev.exception.CustomException;
import com.yash.dev.external.response.ErroResponse;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import lombok.extern.log4j.Log4j2;

/**
 * @author yashwanthanands
 */

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper= new ObjectMapper();
        log.info("::{}",response.request().url());
        log.info("::{}",response.request().headers());

        try {
            ErroResponse erroResponse =objectMapper.readValue(response.body().asInputStream(),ErroResponse.class);
            return new CustomException(erroResponse.getErrorMessage(), erroResponse.getErrorCode(), response.status());
        } catch (IOException e) {
            throw new CustomException("Internal Server Error","INTERNAL_SERVER_ERROR",
                    500);
        }
    }
}
