package demo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import enums.ClientType;
@XmlRootElement
public class LoginObj implements Serializable {
	private static final long serialVersionUID = 1L;
	public String name;
	public String password;
	public ClientType clientType;

	public LoginObj() {
		super();
	}

	public LoginObj(String name, String password, ClientType clientType) {
		super();
		this.name = name;
		this.password = password;
		this.clientType = clientType;
	}

}
