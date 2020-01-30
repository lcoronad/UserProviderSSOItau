package co.com.redhat.sso.provider.user;

import java.util.List;

import org.keycloak.Config.Scope;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;
import org.keycloak.provider.ProviderConfigProperty;
import org.keycloak.storage.UserStorageProvider;
import org.keycloak.storage.UserStorageProviderFactory;

public class ItauBaseDatosUserStorageProviderFactory implements UserStorageProviderFactory<ItauBaseDatosUserStorageProvider> {
	public static final String PROVIDER_NAME = "readonly-bd-itau";

    public String getId() {
        return PROVIDER_NAME;
    }
    
    public ItauBaseDatosUserStorageProvider create( KeycloakSession session, ComponentModel model ) {
        return new ItauBaseDatosUserStorageProvider( session, model );
    }
    
    public void close() {
    	
    }
    
    @Override
    public void postInit(KeycloakSessionFactory factory) {
    	UserStorageProviderFactory.super.postInit(factory);
    }
    
    @Override
    public void init(Scope config) {
    	UserStorageProviderFactory.super.init(config);
    }
    
    @Override
    public UserStorageProvider create(KeycloakSession session) {
    	return UserStorageProviderFactory.super.create(session);
    }
    
    @Override
    public List<ProviderConfigProperty> getConfigProperties() {
    	return UserStorageProviderFactory.super.getConfigProperties();
    }
    
    @Override
    public String getHelpText() {
    	return UserStorageProviderFactory.super.getHelpText();
    }
}
