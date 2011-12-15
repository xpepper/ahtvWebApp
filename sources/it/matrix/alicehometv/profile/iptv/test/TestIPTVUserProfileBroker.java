package it.matrix.alicehometv.profile.iptv.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.profile.iptv.IPTVProfile;
import it.matrix.alicehometv.profile.iptv.IPTVUserProfileBrokerImpl;
import it.matrix.alicehometv.util.TimeProvider;
import it.telecomitalia.pssc.PSSCOrderEntryServiceSoap;

import java.util.GregorianCalendar;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMock.class)
public class TestIPTVUserProfileBroker
{

    private Mockery context = new JUnit4Mockery();

    @Test
    public void testModifyProfile() throws Exception
    {
        final PSSCOrderEntryServiceSoap mockPsscOrderExecutor = context.mock(PSSCOrderEntryServiceSoap.class);
        final TimeProvider mockTimeProvider = context.mock(TimeProvider.class);
		
		final String payload = 
			"<Payload>" +
				"<ProductOfferingID>SetIPTVPersonalData</ProductOfferingID>" +
				"<CharacterizedBy>" +
					"<ofInterestTo>" +
						"<CLI>0000" + "02859485" + "</CLI>" +
						"<pc_level>1</pc_level>" +
						"<pc_pin>67880</pc_pin>" +
						"<purchase_pin>123456</purchase_pin>" +
						"<pin_remember></pin_remember>" +
					"</ofInterestTo>" +
				"</CharacterizedBy>" +
			"</Payload>";

		context.checking(new Expectations()
        {{
            one(mockTimeProvider).now();
            will(returnValue(new GregorianCalendar(2008, 01, 18, 15, 9, 54)));
            one(mockPsscOrderExecutor).psscOrderEntryRequest("SetIPTVData", payload, "2008-02-18T15:09:54");
            will(returnValue("200"));
        }});

        IPTVProfile profile = new IPTVProfile("02859485", "123456", "67880", 1);
        boolean wasSuccessfull = new IPTVUserProfileBrokerImpl(null, mockPsscOrderExecutor, mockTimeProvider).modifyProfileWith(profile);

        assertTrue(wasSuccessfull);
    }
}
