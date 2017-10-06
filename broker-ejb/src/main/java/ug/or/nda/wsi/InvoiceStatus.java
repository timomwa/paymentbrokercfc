
package ug.or.nda.wsi;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for invoiceStatus.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="invoiceStatus">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="PAID"/>
 *     &lt;enumeration value="UNPAID"/>
 *     &lt;enumeration value="EXPIRED"/>
 *     &lt;enumeration value="PENDING_VERIFICATION"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "invoiceStatus")
@XmlEnum
public enum InvoiceStatus {

    PAID,
    UNPAID,
    EXPIRED,
    PENDING_VERIFICATION;

    public String value() {
        return name();
    }

    public static InvoiceStatus fromValue(String v) {
        return valueOf(v);
    }

}
