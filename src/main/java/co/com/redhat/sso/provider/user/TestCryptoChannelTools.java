package co.com.redhat.sso.provider.user;

import co.com.itau.security.utils.CryptChannelTools;

public class TestCryptoChannelTools {
	
	private static String SECURITY_CHANNELLOGINPN_IPCRYPTO = "boinfspt1";
	private static String SECURITY_CHANNELLOGINPN_PORTCRYPTO = "23456";
	private static String RESPUESTA_OK_CRYPTOUTILS = "S|Login Correct";
	
	public static void main( String args[] ) {
		String usuario = args[0];
		String password = args[1];
		
		TestCryptoChannelTools testCryptoChannelTools = new TestCryptoChannelTools( );
		testCryptoChannelTools.validarUsuario( usuario, password );
		testCryptoChannelTools.consultarDatosUsuarioItau( );
	}
	
	private void validarUsuario( String usuario, String password ) {
		String respuesta = CryptChannelTools.validateLogin( SECURITY_CHANNELLOGINPN_IPCRYPTO, SECURITY_CHANNELLOGINPN_PORTCRYPTO, usuario, password );

    	System.out.println( "TestCryptoChannelTools.main Respuesta de CryptoUtils que llego: " + respuesta );
    	
    	boolean userValid = respuesta.equals( RESPUESTA_OK_CRYPTOUTILS );
    	
    	System.out.println( "TestCryptoChannelTools.main Usuario valido: " + userValid );
	}
	
	private void consultarDatosUsuarioItau( ) {
		System.out.println( "TestCryptoChannelTools.main Inicio a consultar datos" );
		
		System.out.println( "TestCryptoChannelTools.main Fin a consultar datos" );
	}
}
