package utils.helpers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Exception")
@XmlAccessorType (XmlAccessType.FIELD)
public class CustomerException {
	public String message;

	public CustomerException() {
	}

	public CustomerException(String message) {
		this.message = message;
	}
}
