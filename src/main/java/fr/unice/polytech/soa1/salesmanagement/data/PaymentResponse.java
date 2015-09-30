package fr.unice.polytech.soa1.salesmanagement.data;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class PaymentResponse {
    private boolean success;
    private String message;

    @XmlElement(name = "success", required = true)
    public boolean isSuccess() { return success; }
    public void setSuccess (boolean success) { this.success = success; }

    @XmlElement(name = "message", required = true)
    public String getMessage() { return message; }
    public void setMessage (String message) { this.message = message; }

}
