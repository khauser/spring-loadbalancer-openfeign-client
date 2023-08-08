package org.test.openfeign;

import java.util.Collections;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;

@Service
public class ArtifactService
{

    @Autowired
    private ArtifactClient isteArtifactClient;

    @PostConstruct
    public void test()
    {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
    }

    public String isValid(String artifact, String version)
    {
        return isteArtifactClient.isValid(artifact, version);
    }

    public String isValidWithPatch(String artifact, String version)
    {
        return isteArtifactClient.isValidWithPatch(artifact, version);
    }

}
