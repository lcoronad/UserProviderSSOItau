/**
 * 
 */
package co.com.redhat.sso.provider.user;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.keycloak.component.ComponentModel;
import org.keycloak.credential.CredentialInput;
import org.keycloak.credential.CredentialInputUpdater;
import org.keycloak.credential.CredentialInputValidator;
import org.keycloak.credential.CredentialModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.UserCredentialModel;
import org.keycloak.models.UserModel;
import org.keycloak.storage.ReadOnlyException;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.user.UserLookupProvider;

import co.com.itau.security.utils.CryptChannelTools;

/**
 * Clase que permite realizar el login por medio de username y clave conectandose a la base de datos de ITAU 
 * esta validacion se realiza con un Jar provisto por ITAU.
 * 
 * @author Lazaro Miguel Coronado Torres
 * @since 29/01/2020
 * @version 0.1
 *
 */
public class ItauBaseDatosUserStorageProvider implements UserStorageProvider, UserLookupProvider, CredentialInputValidator, CredentialInputUpdater {
	
	private static String SECURITY_CHANNELLOGINPN_IPCRYPTO = "boinfspt1";
	private static String SECURITY_CHANNELLOGINPN_PORTCRYPTO = "23456";
	private static String RESPUESTA_OK_CRYPTOUTILS = "S|Login Correct";
	
	protected KeycloakSession session;
    protected ComponentModel model;
    // map of loaded users in this transaction
    protected Map<String, UserModel> loadedUsers = new HashMap<String, UserModel>();
    

    public ItauBaseDatosUserStorageProvider(KeycloakSession session, ComponentModel model) {
        this.session = session;
        this.model = model;
    }
    
	public UserModel getUserByEmail(String email, RealmModel realm) {
		return null;
	}

	public UserModel getUserById(String id, RealmModel realm) {
		return null;
	}
	
	/**
	 * Metodo para validar el usuario y la clave de un usuario por medio del username
	 * 
	 * @param username Usuario a validar
	 * @param realm Realm que se esta usando
	 */
	public UserModel getUserByUsername(String username, RealmModel realm) {
		System.out.println( "ItauBaseDatosUserStorageProvider.getUserByUsername Usuario: " + username );
		
		UserModel adapter = loadedUsers.get(username);
        if (adapter == null) {
        	//Se crea el modelo de usuario consultando un Store procedure
        	adapter = createAdapter(realm, username);
            loadedUsers.put(username, adapter);
                
            System.out.println( "ItauBaseDatosUserStorageProvider.getUserByUsername Encontro adapter" );
        }
		
		return adapter;
	}
	
	protected UserModel createAdapter(RealmModel realm, String username) {
		UserAdapterItau userAdapterItau = new UserAdapterItau( session, realm, model );
		userAdapterItau = this.getUserItau( userAdapterItau, username );
		
		return userAdapterItau;
    }
	
	protected UserAdapterItau getUserItau( UserAdapterItau userAdapterItau, String username ) {
		// TODO: Llamar a SP de Sybase
		// TODO: Llenar los demás datos del cliente
		userAdapterItau.setUsername( username );
		
		return userAdapterItau;
	}

	public boolean supportsCredentialType(String credentialType) {
		return credentialType.equals(CredentialModel.PASSWORD);
	}

	public boolean isConfiguredFor(RealmModel realm, UserModel user, String credentialType) {
		return credentialType.equals(CredentialModel.PASSWORD);
	}
	
	/**
	 * Metodo que valida el password de un usuario.
	 * 
	 */
	public boolean isValid(RealmModel realm, UserModel user, CredentialInput input) {
		boolean userValid = false;
		
		if ( !supportsCredentialType( input.getType() ) || !( input instanceof UserCredentialModel ) ) {
			return false;
		}
		
        UserCredentialModel cred = (UserCredentialModel) input;
        String usuario = user.getUsername();
        String password = cred.getValue();

    	System.out.println( "ItauBaseDatosUserStorageProvider.isValid Usuario que llego: " + usuario );
    	System.out.println( "ItauBaseDatosUserStorageProvider.isValid Password que llego: " + password );
    	
    	try {
	        String respuesta = CryptChannelTools.validateLogin( SECURITY_CHANNELLOGINPN_IPCRYPTO, SECURITY_CHANNELLOGINPN_PORTCRYPTO, usuario, password );
	        
	    	System.out.println( "ItauBaseDatosUserStorageProvider.isValid Respuesta de CryptoUtils que llego: " + respuesta );
	    	
	    	userValid = respuesta.equals( RESPUESTA_OK_CRYPTOUTILS );
    	}
    	catch( Exception ex ) {
    		System.out.println( "ItauBaseDatosUserStorageProvider.isValid Error llamando a CrytoUtils con error: " + ex.getMessage() );
    		ex.printStackTrace( );
    	}
    	
        return userValid;
	}
	
    public boolean updateCredential(RealmModel realm, UserModel user, CredentialInput input) {
        if ( input.getType().equals( CredentialModel.PASSWORD ) ) {
        	throw new ReadOnlyException( "user is read only for this update" );
        }

        return false;
    }

    public void disableCredentialType(RealmModel realm, UserModel user, String credentialType) {

    }

    public Set<String> getDisableableCredentialTypes(RealmModel realm, UserModel user) {
        return Collections.emptySet();
    }

	public void close() {
		
	}
}
