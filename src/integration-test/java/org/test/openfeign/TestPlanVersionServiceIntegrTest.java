package org.test.openfeign;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestPlanVersionServiceIntegrTest extends TestAnnotations
{
    @Autowired
    private ArtifactService filter;

    @Test
    public void version_match_1500()
    {
        assertEquals(filter.isValid("intershop7", "15.0.0-SNAPSHOT"), "true");
    }
}