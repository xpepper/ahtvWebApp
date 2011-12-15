package it.matrix.alicehometv.test;

import it.matrix.alicehometv.AhtvUser;

public class AhtvUserCreator
{
    @SuppressWarnings("unused")
    public static AhtvUser unknownAhtvUser()
    {
        int ahtvId;
        return new AhtvUserBuilder(ahtvId = 0).build();
    }
    
    public static AhtvUser ahtvUserWithId(int ahtvId)
    {
        return new AhtvUserBuilder(ahtvId).build();
    }

    public static AhtvUser simpleAhtvUser()
    {
        return ahtvUserWithId(123);
    }
}
