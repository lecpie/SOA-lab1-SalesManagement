package fr.unice.polytech.soa1.salesmanagement.data;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class Client {

    private int id;
    private String name;
    private String email;
    private String address;

    @XmlElement(name = "id", required = false)
    public int getId() { return id; }
    public void setId (int id) { this.id = id; }

    @XmlElement(name = "name", required = true)
    public String getName() { return name; }
    public void setName (String name) { this.name = name; }

    @XmlElement(name = "email", required = true)
    public String getEmail() { return email; }
    public void setEmail (String email) { this.email = email; }

    @XmlElement(name = "address", required = true)
    public String getAddress () { return address; }
    public void setAddress (String address) { this.address = address; }
}
