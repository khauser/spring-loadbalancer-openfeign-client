package org.test.openfeign;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class TestPlanVersionServiceIntegrTest
{
    @Autowired
    private ArtifactService filter;

    @Test
    public void version_match_1500()
    {
        assertEquals(filter.isValid("intershop7", "15.0.0-SNAPSHOT"), "true");
    }

    @Test
    public void version_match_1500WithPatch()
    {
        assertEquals(filter.isValidWithPatch("intershop7", "15.0.0-SNAPSHOT"), "true");
    }
}