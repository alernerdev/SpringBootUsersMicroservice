package com.pragmaticbitbucket.app.ws.data;

import com.pragmaticbitbucket.app.ws.ui.model.response.AlbumsResponseModel;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// @FeignClient(name="albums-ws", fallback=AlbumsFallback.class)
@FeignClient(name="albums-ws", fallbackFactory=AlbumsFallbackFactory.class)
public interface AlbumsServiceClient {
    @GetMapping("/users/{id}/albums")
    public List<AlbumsResponseModel> getAlbums(@PathVariable String id);
}

/*
@Component
class AlbumsFallback implements AlbumsServiceClient {
    @Override
    public List<AlbumsResponseModel> getAlbums(@PathVariable String id) {
        return new ArrayList<>();
    }
}
 */

@Component
class AlbumsFallbackFactory implements FallbackFactory<AlbumsServiceClient> {
    @Override
    public AlbumsServiceClient create(Throwable cause) {
        return new AlbumsServiceClientFallback(cause);
    }
}

class AlbumsServiceClientFallback implements AlbumsServiceClient {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private final Throwable cause;

    public AlbumsServiceClientFallback(Throwable cause)
    {
        this.cause = cause;
    }

    @Override
    public List<AlbumsResponseModel> getAlbums(String id) {
        if (cause instanceof FeignException && ((FeignException) cause).status() == 404)
            logger.error("404 error took place when getAlbums was called with userId: " + id + ". Error message: " + cause.getLocalizedMessage());
        else
            logger.error("OTHER error took place: " + cause.getLocalizedMessage());

        return new ArrayList<>();
    }
}