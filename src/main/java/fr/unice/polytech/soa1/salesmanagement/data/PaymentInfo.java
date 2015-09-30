package fr.unice.polytech.soa1.salesmanagement.data;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class PaymentInfo {
    private String cardNumber;
    private String cardExpire;
    private String csc;

    @XmlElement(name = "card_number", required = true)
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber (String cardNumber) { this.cardNumber = cardNumber; }

    @XmlElement(name = "card_expire", required = true)
    public String getCardExpire() { return cardExpire; }
    public void setCardExpire (String cardExpire) { this.cardExpire = cardExpire; }

    @XmlElement(name = "csc", required = true)
    public String getCsc() { return csc; }
    public void setCsc  (String csc) { this.csc = csc; }

}
