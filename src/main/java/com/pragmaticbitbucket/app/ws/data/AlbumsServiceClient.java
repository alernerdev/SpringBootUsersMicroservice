package com.pragmaticbitbucket.app.ws.data;

import com.pragmaticbitbucket.app.ws.ui.model.response.AlbumsResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

@FeignClient(name="albums-ws")
public interface AlbumsServiceClient {
    @GetMapping("/users/{id}/albumss")
    public List<AlbumsResponseModel> getAlbums(@PathVariable String id);
}
