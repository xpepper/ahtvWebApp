package it.matrix.alicehometv.test;

import static org.junit.Assert.*;
import it.matrix.alicehometv.ControlCodeVerifierImpl;
import it.matrix.alicehometv.ServiceResponse;
import it.matrix.alicehometv.service.AhtvUserService;
import it.matrix.alicehometv.service.OTPVerificationResult;
import it.matrix.alicehometv.servlet.test.ServiceResultCreator;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.Before;
import org.junit.Test;


public class TestControlCodeVerifier
{
    private Mockery context = new JUnit4Mockery();

    private AhtvUserService mockAhtvUserService;

    @Before
    public void setUp()
    {
        mockAhtvUserService = context.mock(AhtvUserService.class);
    }

    @Test
    public void shouldReturnAnErrorResponseWhenControlCodeTypeIsNotProvided() throws Exception
    {
        context.checking(new Expectations()
        {{      
            never(mockAhtvUserService);
        }});
        
        ServiceResponse response = new ControlCodeVerifierImpl(mockAhtvUserService).forUser(123, "any control code", null);

        assertFalse(response.isOk());
        assertEquals("control code type is missing", response.message());
    }
    
    @Test
    public void shouldReturnAnErrorResponseWhenControlCodeTypeIsNotValid() throws Exception
    {
        context.checking(new Expectations()
        {{      
            never(mockAhtvUserService);
        }});
        
        ServiceResponse response = new ControlCodeVerifierImpl(mockAhtvUserService).forUser(123, "any control code", "invalid control code type");

        assertFalse(response.isOk());
        assertEquals("control code type is missing", response.message());
    }
    
    
    @Test
    public void shouldReturnAnOkResponseWhenSubmittedControlCodeIsCorrected() throws Exception
    {
        context.checking(new Expectations()
        {{      
            one(mockAhtvUserService).verifyPendingOTPFor(123, "a control code", AhtvUserService.CONTROL_CODE_FOR_EMAIL); will(returnValue(ServiceResultCreator.allOkResult()));
        }});
        
        ServiceResponse response = new ControlCodeVerifierImpl(mockAhtvUserService).forUser(123, "a control code", AhtvUserService.CONTROL_CODE_FOR_EMAIL);

        assertTrue(response.isOk());
        assertEquals("control code a control code is correct", response.message());
    }
    
    @Test
    public void shouldReturnAnErrorResponseWhenSubmittedControlCodeIsNotCorrect() throws Exception
    {
        context.checking(new Expectations()
        {{      
            one(mockAhtvUserService).verifyPendingOTPFor(123, "a control code", AhtvUserService.CONTROL_CODE_FOR_MOBILE); will(returnValue(OTPVerificationResult.NULL_RESULT));
        }});
        
        ServiceResponse response = new ControlCodeVerifierImpl(mockAhtvUserService).forUser(123, "a control code", AhtvUserService.CONTROL_CODE_FOR_MOBILE);

        assertFalse(response.isOk());
        assertEquals("control code a control code is not correct", response.message());
    }   
}
