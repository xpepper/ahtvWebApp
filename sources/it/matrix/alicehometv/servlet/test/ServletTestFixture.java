package it.matrix.alicehometv.servlet.test;

import org.junit.Before;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class ServletTestFixture
{
    private MockHttpServletRequest fakeRequest;
    private MockHttpServletResponse fakeResponse;

    @Before
    public void createRequestAndResponse()
    {
        fakeResponse = new MockHttpServletResponse();
        fakeRequest = new MockHttpServletRequest();
    }

    protected MockHttpServletRequest fakeRequest()
    {
        return fakeRequest;
    }

    protected MockHttpServletResponse fakeResponse()
    {
        return fakeResponse;
    }
}
