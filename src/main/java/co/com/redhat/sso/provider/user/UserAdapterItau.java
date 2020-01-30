package co.com.redhat.sso.provider.user;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.keycloak.common.util.MultivaluedHashMap;
import org.keycloak.component.ComponentModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.storage.adapter.AbstractUserAdapter;

public class UserAdapterItau extends AbstractUserAdapter {
	
	protected String usuario = "";
	
	public UserAdapterItau(KeycloakSession session, RealmModel realm, ComponentModel storageProviderModel) {
		super(session, realm, storageProviderModel);
	}

	public String getUsername(  ) {
		return usuario;
	}
	
	public void setUsername( String username ) {
		this.usuario = username;
	}
	
	@Override
    public void setSingleAttribute(String name, String value) {
        if (name.equals("tipoId")) {
            //entity.setPhone(value);
        }
        if (name.equals("numId")) {
            //entity.setPhone(value);
        }
        else {
            super.setSingleAttribute(name, value);
        }
    }

    @Override
    public void removeAttribute(String name) {
    	if (name.equals("tipoId")) {
            //entity.setPhone(null);
        }
        if (name.equals("numId")) {
            //entity.setPhone(null);
        }
        else {
            super.removeAttribute(name);
        }
    }

    @Override
    public void setAttribute(String name, List<String> values) {
    	if (name.equals("tipoId")) {
            //entity.setPhone(values.get(0));
        }
        if (name.equals("numId")) {
            //entity.setPhone(values.get(0));
        }
        else {
            super.setAttribute(name, values);
        }
    }

    @Override
    public String getFirstAttribute(String name) {
    	if (name.equals("tipoId")) {
            //entity.getPhone();
    		return "";
        }
        if (name.equals("numId")) {
            //entity.getPhone();
        	return "";
        }
        else {
            return super.getFirstAttribute(name);
        }
    }

    @Override
    public Map<String, List<String>> getAttributes() {
        Map<String, List<String>> attrs = super.getAttributes();
        MultivaluedHashMap<String, String> all = new MultivaluedHashMap<>();
        all.putAll(attrs);
        all.add("tipoId", "");
        all.add("numId", "");
        return all;
    }

    @Override
    public List<String> getAttribute(String name) {
        if (name.equals("tipoId")) {
            List<String> tipoId = new LinkedList<>();
            tipoId.add("");
            return tipoId;
        }
        else if (name.equals("numId")) {
            List<String> numId = new LinkedList<>();
            numId.add("");
            return numId;
        }
        else {
            return super.getAttribute(name);
        }
    }
}
