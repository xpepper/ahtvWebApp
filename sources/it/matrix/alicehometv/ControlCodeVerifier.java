package it.matrix.alicehometv;

public interface ControlCodeVerifier
{
    ServiceResponse forUser(int ahtvUserId, String controlCode, String controlCodeType);
}