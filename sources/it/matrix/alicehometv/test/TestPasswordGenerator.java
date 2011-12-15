package it.matrix.alicehometv.test;

import static org.apache.commons.lang.StringUtils.*;
import static org.fest.assertions.Assertions.*;
import it.matrix.alicehometv.PasswordGeneratorImpl;

import org.junit.Test;

public class TestPasswordGenerator
{
    @Test
    public void testGenerateNewPassword() throws Exception
    {
        String password = new PasswordGeneratorImpl().generateNewPassword();
        assertThat(isAlpha(password)).isTrue();
        assertThat(password).hasSize(8);
    }
}
