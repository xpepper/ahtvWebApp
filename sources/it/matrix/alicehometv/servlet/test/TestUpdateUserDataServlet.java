
package it.matrix.alicehometv.servlet.test;

import static org.junit.Assert.assertEquals;
import it.matrix.alicehometv.profile.iptv.IPTVProfile;
import it.matrix.alicehometv.profile.iptv.IPTVUserProfileBroker;
import it.matrix.alicehometv.servlet.UpdateUserDataServlet;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestUpdateUserDataServlet extends ServletTestFixture 
{
    private Mockery context = new JUnit4Mockery();

    private IPTVUserProfileBroker mockIPTVUserProfileBroker;

    @Before
    public void setUp()
    {
        mockIPTVUserProfileBroker = context.mock(IPTVUserProfileBroker.class);
    }

    @Test
    public void testUpdateUserDataSuccessfully() throws Exception
    {
    	ServletTestFixtureUtils.setCookieWithCLI("021234567890", fakeRequest());
    	fakeRequest().setParameter("controlCode", "onePinCode");
    	fakeRequest().setParameter("parentalControlCode", "onePCCode");
    	fakeRequest().setParameter("parentalControlLevel", "1");

    	context.checking(new Expectations()
        {{      
        	one(mockIPTVUserProfileBroker).modifyProfileWith(new IPTVProfile("021234567890", "onePinCode", "onePCCode", 1)); will(returnValue(true));
        }});
        
        new UpdateUserDataServletForTest(mockIPTVUserProfileBroker).doPost(fakeRequest(), fakeResponse());
        
        assertEquals(
                "{" +
                    "\"responseMessage\":{\"itsResult\":\"OK\",\"itsMessage\":\"IPTV user profile saved successfully\"}" + 
                "}", 
                fakeResponse().getContentAsString());   
    }

    @Test
    public void testUpdateUserDataFailure() throws Exception
    {
    	ServletTestFixtureUtils.setCookieWithCLI("unexistingCLI", fakeRequest());
    	fakeRequest().setParameter("controlCode", "onePinCode");
    	fakeRequest().setParameter("parentalControlCode", "onePCCode");
    	fakeRequest().setParameter("parentalControlLevel", "1");

    	context.checking(new Expectations()
        {{      
        	one(mockIPTVUserProfileBroker).modifyProfileWith(new IPTVProfile("unexistingCLI", "onePinCode", "onePCCode", 1)); will(returnValue(false));
        }});
        
        new UpdateUserDataServletForTest(mockIPTVUserProfileBroker).doPost(fakeRequest(), fakeResponse());
        
        assertEquals(
                "{" +
                    "\"responseMessage\":{\"itsResult\":\"ERROR\",\"itsMessage\":\"IPTV user profile not saved\"}" + 
                "}", 
                fakeResponse().getContentAsString());   
    }
    
    
    @SuppressWarnings("serial")
    private class UpdateUserDataServletForTest extends UpdateUserDataServlet
    {
		private final IPTVUserProfileBroker profileFinder;

        public UpdateUserDataServletForTest(IPTVUserProfileBroker aProfileFinder)
        {
            profileFinder = aProfileFinder;
        }
        
       @Override
    protected IPTVUserProfileBroker iptvUserProfileBroker() {
    	return profileFinder;
    }
     }
}
