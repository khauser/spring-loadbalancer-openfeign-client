package org.test.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "iste-artifact")
public interface ArtifactClient
{
    String IS_VERSION_ALLOWED = "/isVersionAllowed";
    String ARTIFACT = "artifact";
    String VERSION = "version";

    // @GetMapping(value = "foo/bar")
    // String doRestRequest();

    @GetMapping(value = IS_VERSION_ALLOWED + "/{artifact}:{version}")
    String isValid(@PathVariable(ARTIFACT) String artifact, @PathVariable(VERSION) String version);

    @PatchMapping(value = IS_VERSION_ALLOWED + "/{artifact}:{version}")
    String isValidWithPatch(@PathVariable(ARTIFACT) String artifact, @PathVariable(VERSION) String version);
}
